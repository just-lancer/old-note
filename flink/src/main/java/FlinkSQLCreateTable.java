import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class FlinkSQLCreateTable {
    public static void main(String[] args) {
        // 获取表执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        // 编写sql语句
        String createSourceTable = "create table source_table ( " +
                " `user_name` string, " +
                " `url` string, " +
                " `ts` bigint " +
                ") with ( " +
                " 'connector' = 'filesystem', " +
                " 'path' = 'E:\\IntelliJ_IDEA_workplace\\flink\\data\\input02.txt', " +
                " 'format' = 'csv' " +
                " )";

        // 执行sql，在表环境中注册表
        tableEnv.executeSql(createSourceTable);

        // 编写sql语句
        String createSinkTable = "create table sink_table ( " +
                " `url` string, " +
                " `count_pv` bigint " +
                ") with ( " +
                " 'connector' = 'print' " +
                " )";

        // 执行sql，在表环境中注册表
        tableEnv.executeSql(createSinkTable);

        // 执行sql，统计每个页面的pv数
        String pvCount = "select `url`, count(`user_name`) as `count_pv` from source_table group by `url`";
        Table table = tableEnv.sqlQuery(pvCount);

        // 基于已有的Table类型对象，在表环境中注册表
        tableEnv.createTemporaryView("TemporaryView", table);

        // 将当前统计结果写入到输出表中，有两种方法
        // 1、使用表环境的executeSql()方法，传入一个insert sql语句
        String insertIntoTable = "insert into sink_table select `url`, count(`user_name`)  from source_table group by `url`";
        tableEnv.executeSql(insertIntoTable);
        // 2、使用Table对象的executeInsert()方法，将输出表的表名传入
        //table.executeInsert("sink_table");

        // -----------------------------------------------------------

        // 将Table转换成DataStream，调用表环境的toDataStream()方法
        // 下面语句会报错，原因是无法将动态变化的表转换成流
        // DataStream<Row> rowDataStream = tableEnv.toDataStream(table);
        // System.out.println(rowDataStream);

        // 将DataStream转换成Table，调用表环境的fromDataStream()方法
        // Table table1 = tableEnv.fromDataStream(DataSource DS);

        // ------------------------------------------------------------

        // 将Table转换成表环境中的注册表，调用表环境的createTemporaryView()方法，创建零时视图，就相当于在表环境中注册了表
        //tableEnv.createTemporaryView("tempView", table);

        // 将表环境中的零时视图转换成Table对象，调用表环境中的from()方法
        //Table temView = tableEnv.from("temView");

        // ------------------------------------------------------------

        // 同理，也能够直接将DataStream转换成表环境中的注册表，调用表环境的from()方法
        // Table table2 = tableEnv.fromDataStream(DataStream DS);

        // ！！！！将表环境中的注册表转换成DataStream，无法实现！！！！
    }
}
