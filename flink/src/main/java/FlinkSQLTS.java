import Utils.Clicks;
import Utils.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import java.time.Duration;

import static org.apache.flink.table.api.Expressions.$;

/**
 * Author: shaco
 * Date: 2022/7/9
 * Desc: 时间语义
 */
public class FlinkSQLTS {
    public static void main(String[] args) throws Exception {
        // 使用DDL语言创建动态表时，创建时间戳

        // 1、利用SQL语句建表时，指定时间属性
        // func1();

        // 2、从DataStream转换成表时，指定字段——事件时间
        // 从流中转换成Table，需要在流中指定水位线和时间戳
        // func2();
        // func3();

        // 3、从DataStream转换成Table，指定字段——处理时间
        // 不需要指定水位线和时间戳，但需要额外的拿一个字段作为时间字段
        func4();

    }

    public static void func1() throws Exception {
        // 获取环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        // 编写DDL SQL，创建输入表
        // csv解析格式，不能直接将文件中的数据直接解析成timestamp类型
        String sourceTable = "create table source_table ( " +
                " `user_name` string, " +
                " `url` string," +
                " `ts` bigint, " +
                " `ts1` as to_timestamp(from_unixtime(ts / 1000)), " +
                " watermark for `ts1` as `ts1` - interval '1' second " +
                " ) with ( " +
                " 'connector' = 'filesystem', " +
                " 'path' = 'E:\\IntelliJ_IDEA_workplace\\flink\\data\\input02.txt', " +
                " 'format' = 'csv' " +
                " )";

        // 在表环境中注册输入表
        tableEnv.executeSql(sourceTable);

        // 查询注册表
        Table table = tableEnv.sqlQuery("select * from source_table");

        // 将Table转换成DS，并打印输出
        tableEnv.toDataStream(table).print(">>>>");

        // 执行流数据处理
        env.execute();
    }

    public static void func2() throws Exception {
        // 将流转换成表时，时间的定义
        // 获取执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        // 读取数据流
        DataStreamSource<String> sourceDS = env.readTextFile("E:\\IntelliJ_IDEA_workplace\\flink\\data\\input02.txt");

        // 将流转换成表，并指定时间信息，需要为数据流指定水位线策略和时间戳，因为表环境的方法只能指定数据流中的时间字段，并不能进行水位线的指定
        SingleOutputStreamOperator<String> DS = sourceDS.assignTimestampsAndWatermarks(
                WatermarkStrategy.<String>forBoundedOutOfOrderness(Duration.ZERO)
                        .withTimestampAssigner(
                                new SerializableTimestampAssigner<String>() {
                                    @Override
                                    public long extractTimestamp(String element, long recordTimestamp) {
                                        return Long.valueOf(element.split(",")[2]);
                                    }
                                }
                        )
        );

        // 将数据流转换成表，同时指定时间属性
        Table table = tableEnv.fromDataStream(DS, $("f0"));

        // 将表转换成流进行输出
        tableEnv.toDataStream(table).print();

        DS.print();
        // 执行流数据处理
        env.execute();
    }

    public static void func3() throws Exception {
        // 将流转换成表时，时间属性的定义——事件时间
        // 获取执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        // 获取数据源——流式数据
        SingleOutputStreamOperator<Event> sourceDS = env.addSource(new Clicks())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ZERO)
                        .withTimestampAssigner(
                                new SerializableTimestampAssigner<Event>() {
                                    @Override
                                    public long extractTimestamp(Event element, long recordTimestamp) {
                                        return element.time;
                                    }
                                }
                        ));

        // 将流转换成表
        Table table = tableEnv.fromDataStream(sourceDS, $("name").as("user_name"), $("page").as("url"),
                $("time").rowtime().as("ts"));

        // 将表转换成数据流
        tableEnv.toDataStream(table).print();

        // 执行流数据处理
        env.execute();

    }

    public static void func4() throws Exception {
        // 将流转换成表时，时间的定义——处理时间
        // 获取执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

//        // 获取数据流
//        DataStreamSource<Event> sourceDS = env.addSource(new Clicks());
//
//        // 转换成表
//        Table table = tableEnv.fromDataStream(sourceDS, $("name"), $("page"), $("ts").proctime());
//
//        // 将表转换成流并输出
//        tableEnv.toDataStream(table).print();

        //====================================
        // 利用DDL语句创建表
        String createSQL = "create table source_table (" +
                " `name` string, " +
                " `url` string, " +
                " `time` bigint, " +
                " `ts` as proctime() " +
                " ) with ( " +
                " 'connector' = 'filesystem', " +
                " 'path' = 'E:\\IntelliJ_IDEA_workplace\\flink\\data\\input02.txt', " +
                " 'format' = 'csv' " +
                ")";

        // 在表环境中注册表
        tableEnv.executeSql(createSQL);

        // 执行查询
        Table table1 = tableEnv.sqlQuery("select * from source_table");

        // 创建输出表，将结果输出到控制台
//        String sinkTable = "create table sink_table (" +
//                " `name` string, " +
//                " `url` string, " +
//                " `ts` timestamp " +
//                ") with (" +
//                " 'connector' = 'print' " +
//                ")";
//
//        // 在表环境中注册表
//        tableEnv.executeSql(sinkTable);

        // 写出到表
        // table1.executeInsert("sink_table");

        // 还是将表转换成数据流输出
        tableEnv.toDataStream(table1).print();

        // 执行流数据处理
        env.execute();

    }

}
