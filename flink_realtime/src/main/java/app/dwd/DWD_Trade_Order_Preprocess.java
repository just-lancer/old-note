package app.dwd;

import common.KafkaConstants;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import utils.KafkaUtils;
import utils.MySQLUtils;

import java.time.Duration;

/**
 * Author: shaco
 * Date: 2022/7/21
 * Desc: dwd层交易域，订单预处理
 * 将订单明细表、订单表、订单明细活动表、订单明细优惠券表和字典表进行关联
 */
public class DWD_Trade_Order_Preprocess {
    public static void main(String[] args) {
        // TODO 1、获取流执行环境，并创建表环境
        // 1.1 创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        // 1.2 流执行环境设置并行度
        env.setParallelism(4);
        // 1.3 表环境设置状态生命周期
        tableEnv.getConfig().setIdleStateRetention(Duration.ofSeconds(10));

        // TODO 2、设置检查点
//        // 1、开启检查点，并设置检查点模式为EXACTLY_ONCE
//        env.enableCheckpointing(3000L, CheckpointingMode.EXACTLY_ONCE);
//        // 2、设置检查点超时时间
//        env.getCheckpointConfig().setCheckpointTimeout(60 * 1000L);
//        // 3、设置检查点的最小时间间隔
//        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(3000L);
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

        // TODO 3、从Kafka中读取订单明细表的数据
        // 3.1 编写sql
        String order_detail_sql = " create table order_detail (" +
                " `database` string, " +
                " `table` string, " +
                " `type` string, " +
                " `data` map<string, string>, " +
                " `old` map<string, string>, " +
                " `ts` string, " +
                " `proctime` as proctime()" +
                " )" + KafkaUtils.getKafkaConnector("order_detail", "dwd_order_detail_preprocess");
        // 3.2 表环境中注册表
        tableEnv.executeSql(order_detail_sql);
        // 3.3 过滤需要的字段
        String filter_order_detail = " select `data`['id'] as id,\n" +
                "       `data`['order_id'] as order_id,\n" +
                "       `data`['sku_id'] as sku_id,\n" +
                "       `data`['sku_num'] as sku_num,\n" +
                "       `data`['order_price'] as order_price,\n" +
                "       `data`['create_time'] as create_time,\n" +
                "       `data`['source_type'] as source_type,\n" +
                "       `data`['source_id'] as source_id,\n" +
                "       `data`['split_total_amount'] as split_total_amount,\n" +
                "       `data`['split_activity_amount'] as split_activity_amount,\n" +
                "       `data`['split_coupon_amount'] as split_coupon_amount,\n" +
                "       `type`,\n" +
                "       `old`,\n" +
                "       `ts`,\n" +
                "       `proctime`\n" +
                "from order_detail\n" +
                "where `type` = 'insert' or `type` = 'update'";
        // 3.4 执行查询，获得对应的Table对象
        Table filter_order_detail_table = tableEnv.sqlQuery(filter_order_detail);
        // 3.5 表环境中注册Table
        tableEnv.createTemporaryView("filter_order_detail", filter_order_detail_table);

        // TODO 4、从Kafka中读取订单信息表的数据
        // 4.1 编写sql
        String order_info_sql = " create table order_info (" +
                " `database` string, " +
                " `table` string, " +
                " `type` string, " +
                " `data` map<string, string>, " +
                " `old` map<string, string>, " +
                " `ts` string " +
                ")" + KafkaUtils.getKafkaConnector("order_info", "dwd_order_info_preprocess");
        // 4.2 表环境中注册
        tableEnv.executeSql(order_info_sql);
        // 4.3 过滤需要的字段
        String filter_order_info =
                " select `data`['id'] as id,\n" +
                        "       `data`['user_id'] as user_id,\n" +
                        "       `data`['province_id'] as province_id,\n" +
                        "       `data`['operate_time'] as operate_time,\n" +
                        "       `data`['order_status'] as order_status,\n" +
                        "       `type`,\n" +
                        "       `old`,\n" +
                        "       `ts`\n" +
                        "from order_info\n" +
                        "where `type` = 'insert'\n" +
                        "   or `type` = 'update'";
        // 4.4 执行查询，获得对应的Table对象
        Table filter_order_info_table = tableEnv.sqlQuery(filter_order_info);
        // 4.5 表环境中注册表
        tableEnv.createTemporaryView("filter_order_info", filter_order_info_table);

        // TODO 5、从Kafka中读取订单明细活动表
        // 5.1 编写sql
        String order_detail_activity_sql = " create table order_detail_activity (" +
                " `database` string, " +
                " `table` string, " +
                " `type` string, " +
                " `data` map<string, string>, " +
                " `old` map<string, string>, " +
                " `ts` string " +
                ")" + KafkaUtils.getKafkaConnector("order_detail_activity", "dwd_order_detail_activity_preprocess");
        // 5.2 表环境中注册
        tableEnv.executeSql(order_detail_activity_sql);
        // 5.3 过滤需要的字段
        String filter_order_detail_activity =
                " select `data`['order_detail_id'] as order_detail_id,\n" +
                        "       `data`['activity_id'] as activity_id,\n" +
                        "       `data`['activity_rule_id'] as activity_rule_id\n" +
                        "from order_detail_activity\n" +
                        "where `type` = 'insert' or `type` = 'update'";
        // 5.4 执行查询，获得对应的Table对象
        Table filter_order_detail_activity_table = tableEnv.sqlQuery(filter_order_detail_activity);
        // 5.5 表环境中注册表
        tableEnv.createTemporaryView("filter_order_detail_activity", filter_order_detail_activity_table);

        // TODO 6、从Kafka中读取订单明细优惠券表
        // 6.1 编写sql
        String order_detail_coupon_sql = " create table order_detail_coupon (" +
                " `database` string, " +
                " `table` string, " +
                " `type` string, " +
                " `data` map<string, string>, " +
                " `old` map<string, string>, " +
                " `ts` string " +
                ")" + KafkaUtils.getKafkaConnector("order_detail_coupon", "dwd_order_detail_coupon_preprocess");
        // 6.2 表环境中注册
        tableEnv.executeSql(order_detail_coupon_sql);
        // 6.3 过滤需要的字段
        String filter_order_detail_coupon =
                " select `data`['order_detail_id'] as order_detail_id,\n" +
                        "       `data`['coupon_id'] as coupon_id\n" +
                        "from order_detail_coupon\n" +
                        "where `type` = 'insert' or `type` = 'update'";
        // 6.4 执行SQL获得Table对象
        Table filter_order_detail_coupon_table = tableEnv.sqlQuery(filter_order_detail_coupon);
        // 6.5 在表环境中注册表
        tableEnv.createTemporaryView("filter_order_detail_coupon", filter_order_detail_coupon_table);

        // TODO 7、从MySQL中读取字典表
        // 7.1 编写sql
        String base_dic_sql = " CREATE TABLE base_dic (\n" +
                "  dic_code string,\n" +
                "  dic_name string,\n" +
                "  parent_code string,\n" +
                "  PRIMARY KEY (dic_code) NOT ENFORCED\n" +
                ")" + MySQLUtils.getMySQLConnector("gmall", "base_dic");
        // 7.2 表环境中注册
        tableEnv.executeSql(base_dic_sql);

        // TODO 8、将订单明细表、订单信息表、订单明细活动表、订单明细优惠券表、字典表关联
        // 8.1 编写SQL
        String joined_tables_sql =
                " select " +
                        "       od.id,\n" +
                        "       od.order_id,\n" +
                        "       od.sku_id,\n" +
                        "       od.sku_num,\n" +
                        "       od.order_price,\n" +
                        "       od.create_time,\n" +
                        "       od.source_type,\n" +
                        "       od.source_id,\n" +
                        "       od.split_total_amount,\n" +
                        "       od.split_activity_amount,\n" +
                        "       od.split_coupon_amount,\n" +
                        "       od.type as od_type,\n" +
                        "       od.`old`  as od_old,\n" +
                        "       od.ts   as od_ts,\n" +
                        "       cast(od.proctime as string) as proctime,\n" +
                        "       oi.user_id,\n" +
                        "       oi.province_id,\n" +
                        "       oi.operate_time,\n" +
                        "       oi.order_status,\n" +
                        "       oi.type as oi_type,\n" +
                        "       oi.`old`  as oi_old,\n" +
                        "       oi.ts   as oi_ts,\n" +
                        "       oda.activity_id,\n" +
                        "       oda.activity_rule_id,\n" +
                        "       odc.coupon_id,\n" +
                        "       bc.dic_name as source_type_name\n" +
                        "from filter_order_detail as od\n" +
                        "         join filter_order_info as oi on od.order_id = oi.id\n" +
                        "         left join filter_order_detail_activity as oda on od.id = oda.order_detail_id\n" +
                        "         left join filter_order_detail_coupon as odc on od.id = odc.order_detail_id\n" +
                        "         join base_dic for system_time as of od.proctime as bc on od.source_type = bc.dic_code";
        // 8.2 执行sql，获得Table对象
        Table result_table = tableEnv.sqlQuery(joined_tables_sql);
        // 8.3 在表环境中注册
        tableEnv.createTemporaryView("result_table", result_table);

        // TODO 9、创建输出动态表，向Kafka主题中写入数据
        // 9.1 编写sql
        String to_kafka_table =
                " create table to_kafka_table\n" +
                        "(\n" +
                        "    id                    string,\n" +
                        "    order_id              string,\n" +
                        "    sku_id                string,\n" +
                        "    sku_num               string,\n" +
                        "    order_price           string,\n" +
                        "    create_time           string,\n" +
                        "    source_type           string,\n" +
                        "    source_id             string,\n" +
                        "    split_total_amount    string,\n" +
                        "    split_activity_amount string,\n" +
                        "    split_coupon_amount   string,\n" +
                        "    od_type               string,\n" +
                        "    od_old                map<string, string>,\n" +
                        "    od_ts                 string,\n" +
                        "    proctime              string,\n" +
                        "    user_id               string,\n" +
                        "    province_id           string,\n" +
                        "    operate_time          string,\n" +
                        "    order_status          string,\n" +
                        "    oi_type               string,\n" +
                        "    oi_old                map<string, string>,\n" +
                        "    oi_ts                 string,\n" +
                        "    activity_id           string,\n" +
                        "    activity_rule_id      string,\n" +
                        "    coupon_id             string,\n" +
                        "    dic_name              string,\n" +
                        "    primary key (`id`) not enforced" +
                        ")" +
                        KafkaUtils.getUpsertKafkaConnector(KafkaConstants.DWD_TRADE_ORDER_PREPROCESS);
        // 9.2 表环境中注册表
        tableEnv.executeSql(to_kafka_table);

        // TODO 10、向Kafka中写入数据
        tableEnv.executeSql("insert into to_kafka_table select * from result_table");

    }
}
