package app.dwd;

import common.KafkaConstants;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import utils.KafkaUtils;

import java.time.Duration;

/**
 * Author: shaco
 * Date: 2022/7/22
 * Desc: 交易域取消下单事务事实表
 */
public class DWD_Trade_Order_Cancel_Detail {
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

        // TODO 3、从Kafka订单预处理主题中，读取预处理数据，并创建动态表
        // 3.1 编写SQL
        String order_detail_preprocess_sql =
                "create table order_preprocess_table\n" +
                        "(\n" +
                        "    `id`                    string,\n" +
                        "    `order_id`              string,\n" +
                        "    `sku_id`                string,\n" +
                        "    `sku_num`               string,\n" +
                        "    `order_price`           string,\n" +
                        "    `create_time`           string,\n" +
                        "    `source_type`           string,\n" +
                        "    `source_id`             string,\n" +
                        "    `split_total_amount`    string,\n" +
                        "    `split_activity_amount` string,\n" +
                        "    `split_coupon_amount`   string,\n" +
                        "    `od_type`               string,\n" +
                        "    `od_old`                map<string, string>,\n" +
                        "    `od_ts`                 string,\n" +
                        "    `proctime`              string,\n" +
                        "    `user_id`               string,\n" +
                        "    `province_id`           string,\n" +
                        "    `operate_time`          string,\n" +
                        "    `order_status`          string,\n" +
                        "    `oi_type`               string,\n" +
                        "    `oi_old`                map<string, string>,\n" +
                        "    `oi_ts`                 string,\n" +
                        "    `activity_id`           string,\n" +
                        "    `activity_rule_id`      string,\n" +
                        "    `coupon_id`             string,\n" +
                        "    `dic_name`              string\n" +
                        ")" +
                        KafkaUtils.getKafkaConnector(KafkaConstants.DWD_TRADE_ORDER_PREPROCESS, "order_preprocess_cancel");
        // 3.2 表环境中注册表
        tableEnv.executeSql(order_detail_preprocess_sql);

        // TODO 4、从预处理数据动态表中，过滤出取消下单行为
        /**
         *  什么条件的数据是取消下单行为？
         *      -- type = update
         *      -- old不为null，并且old['order_status']不为null
         *      -- 并且order_status修改后的值为1003
         */
        // 4.1 编写sql
        String order_cancel_detail_sql =
                "select \n" +
                        "    `id`,\n" +
                        "    `order_id`,\n" +
                        "    `user_id`,\n" +
                        "    `sku_id`,\n" +
                        "    `sku_num`,\n" +
                        "    `province_id`,\n" +
                        "    `activity_id`,\n" +
                        "    `activity_rule_id`,\n" +
                        "    `coupon_id`,\n" +
                        "    `operate_time` `cancel_time`,\n" +
                        "    `source_id`,\n" +
                        "    `source_type`,\n" +
                        "    `dic_name` `source_type_name`,\n" +
                        "    `split_activity_amount`,\n" +
                        "    `split_coupon_amount`,\n" +
                        "    `split_total_amount`,\n" +
                        "    `oi_ts` `ts`\n" +
                        "from order_preprocess_table\n" +
                        "where `od_type` = 'update'\n" +
                        "  and `od_old`['order_status'] is not null\n" +
                        "  and `order_status` = '1003'";
        // 4.2 执行查询，并获得Table对象
        Table order_cancel_table = tableEnv.sqlQuery(order_cancel_detail_sql);
        // 4.3 表环境中注册表
        tableEnv.createTemporaryView("order_cancel_table", order_cancel_table);

        // TODO 5、将取消下单行为写入到Kafka DWD_Trade_Order_Cancel_Detail主题中
        // 5.1 编写SQL，创建输出表
        String result_table_sql =
                "create table to_kafka_table(\n" +
                        "    `id` string,\n" +
                        "    `order_id` string,\n" +
                        "    `user_id` string,\n" +
                        "    `sku_id` string,\n" +
                        "    `sku_num` string,\n" +
                        "    `province_id` string,\n" +
                        "    `activity_id` string,\n" +
                        "    `activity_rule_id` string,\n" +
                        "    `coupon_id` string,\n" +
                        "    `operate_time cancel_time` string,\n" +
                        "    `source_id` string,\n" +
                        "    `source_type` string,\n" +
                        "    `source_type_name` string,\n" +
                        "    `split_activity_amount` string,\n" +
                        "    `split_coupon_amount` string,\n" +
                        "    `split_total_amount` string,\n" +
                        "    `oi_ts ts` string,\n" +
                        "    primary key (`id`) not enforced" +
                        ")" + KafkaUtils.getUpsertKafkaConnector(KafkaConstants.DWD_TRADE_ORDER_CANCEL);
        // 5.2 表环境中注册表
        tableEnv.executeSql(result_table_sql);
        // 5.3 向Kafka中写入数据
        tableEnv.executeSql("insert into to_kafka_table select * from order_cancel_table");
    }
}
