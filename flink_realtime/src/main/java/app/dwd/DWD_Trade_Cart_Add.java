package app.dwd;

import common.KafkaConstants;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import utils.KafkaUtils;
import utils.MySQLUtils;

/**
 * Author: shaco
 * Date: 2022/7/19
 * Desc: DWD层，交易域加购事务事实表
 */
public class DWD_Trade_Cart_Add {
    public static void main(String[] args) {
        // TODO 1、获取流数据执行环境，并创建表环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        env.setParallelism(4);

        // TODO 2、设置检查点
//        // 1、开启检查点，并设置检查点模式为EXACTLY_ONCE
        env.enableCheckpointing(3000L, CheckpointingMode.EXACTLY_ONCE);
//        // 2、设置检查点超时时间
        env.getCheckpointConfig().setCheckpointTimeout(60 * 1000L);
//        // 3、设置检查点的最小时间间隔
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(3000L);
//        // 4、开启检查点外部持久化策略：两种策略：任务失败，检查点直接删除；或者，任务失败保留检查点
//        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
//        // 5、设置检查点失败重启策略
//        env.setRestartStrategy(RestartStrategies.failureRateRestart(10, Time.of(1L, TimeUnit.DAYS), Time.of(3L, TimeUnit.MINUTES)));
//        // 6、设置状态后端
//        env.setStateBackend(new HashMapStateBackend());
//        // 7、设置检查点存储路径
//        env.getCheckpointConfig().setCheckpointStorage("hdfs://hadoop132:9820/gmall/ck");
//        // 8、设置系统属性，此处设置的是Hadoop的用户名
//        System.setProperty("HADOOP_USER_NAME", "atguigu");

        // TODO 3、获取Kafka topic_db主题的数据，并过滤出加购数据表的数据
        // 3.1 编写建表SQL语句
        // 我这里消费的主题就是加购事务事实表的表名，实时用的采集架构是离线的
        String kafkaSql = " CREATE TABLE cartAdd (\n" +
                "  `database` string,\n" +
                "  `table` string,\n" +
                "  `type` string,\n" +
                "  `data` map<string, string>,\n" +
                "  `old` map<string, string>,\n" +
                "  `ts` string,\n" +
                "  `proctime` as proctime()" +
                ") " + KafkaUtils.getKafkaConnector("cart_info", "dwd_trade_cart_info");
        // 3.2 在表环境中注册表
        tableEnv.executeSql(kafkaSql);

        // TODO 4、获取MySQL中的字典表数据，并转换成动态表
        // 4.1 编写建表SQL语句
        String mysqlTable = " CREATE TABLE mysqlConfTable (\n" +
                "  dic_code string,\n" +
                "  dic_name string,\n" +
                "  parent_code string,\n" +
                "  PRIMARY KEY (dic_code) NOT ENFORCED\n" +
                ")" + MySQLUtils.getMySQLConnector("gmall", "base_dic");
        // 4.2 表环境中注册表
        tableEnv.executeSql(mysqlTable);

        // TODO 5、从加购表中过滤出需要的字段
        // 5.1 编写sql
        String filterCartSQL = " select `data`['id']          as `id`,\n" +
                "       `data`['user_id']     as `user_id`,\n" +
                "       `data`['sku_id']      as `sku_id`,\n" +
                "       if(`type` = 'insert', `data`['sku_num'], cast((cast(`data`['sku_num'] as int) - cast(\n" +
                "               `old`['sku_num'] as int\n" +
                "           )) as string))    as `sku_num`,\n" +
                "       `data`['source_type'] as `source_type`,\n" +
                "       `ts`,\n" +
                "       `proctime`\n" +
                "from cartAdd\n" +
                "where `type` = 'insert'\n" +
                "   or (`type` = 'update' and `data`['sku_num'] is not null and\n" +
                "       cast(`data`['sku_num'] as int) > cast(`data`['sku_num'] as int))";

        // 5.2 执行sql
        Table filterCart = tableEnv.sqlQuery(filterCartSQL);

        // 5.3 在表环境中注册表
        tableEnv.createTemporaryView("filterCart", filterCart);

        // TODO 6、执行lookup join，进行维度退化
        // 6.1 编写SQL
        String lookupJoin = " select id, user_id, sku_id, sku_num, source_type, dic_name, ts\n" +
                "from filterCart as c join mysqlConfTable for system_time as of c.proctime as m on c.source_type = m.dic_code";

        // 6.2 执行SQL
        Table kafkaTable = tableEnv.sqlQuery(lookupJoin);

        // 6.3 表环境中注册表
        tableEnv.createTemporaryView("cartToKafka", kafkaTable);

        // TODO 7、创建动态表，用于向Kafka 主题中写入数据
        // 7.1 编写SQL
        String toKafkaSQL = " CREATE TABLE CartTokafka (\n" +
                "  `id` string,\n" +
                "  `user_id` string,\n" +
                "  `sku_id` string,\n" +
                "  `sku_num` string,\n" +
                "  `source_type` string,\n" +
                "  `dic_name` string,\n" +
                "  `ts` string,\n" +
                "   primary key (`id`) not enforced" +
                ")" + KafkaUtils.getUpsertKafkaConnector(KafkaConstants.DWD_TRADE_CART_ADD);

        // 7.2 执行SQL
        tableEnv.executeSql(toKafkaSQL);

        // 7.3 向Kafka中写入数据
        tableEnv.executeSql("insert into CartTokafka select * from cartToKafka");


    }
}
