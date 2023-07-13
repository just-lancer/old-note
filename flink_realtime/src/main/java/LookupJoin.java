import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * Author: shaco
 * Date: 2022/7/18
 * Desc: lookup join测试
 */
public class LookupJoin {
    public static void main(String[] args) {
        // TODO 1、获取流执行环境，并创建表环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        env.setParallelism(1);

        // TODO 2、连接Kafka，获取数据表
        /**
         *  Flink SQL读取Kafka数据的方式：
         *  编写建表的sql语句，连接器使用kafka连接器，create table ... with connector = 'kafka'，
         *
         *  Flink DataStream读取Kafka数据的方式：
         *  使用FlinkKafkaConsumer连接器
         */
        // 编写SQL
        String kafkaSql = "CREATE TABLE KafkaTable (\n" +
                "  `id` string,\n" +
                "  `name` string,\n" +
                "  `deptNum` int,\n" +
                "  proc_time as proctime()\n" +
                ") WITH (\n" +
                "  'connector' = 'kafka',\n" +
                "  'topic' = 'flinksql',\n" +
                "  'properties.bootstrap.servers' = '192.168.200.132:9092',\n" +
                "  'properties.group.id' = 'testGroup',\n" +
                "  'scan.startup.mode' = 'earliest-offset',\n" +
                "  'format' = 'json'\n" +
                ")";
        // 表环境中注册表
        tableEnv.executeSql(kafkaSql);
        // tableEnv.executeSql("select * from  KafkaTable").print();

        // TODO 3、连接MySQL数据库，获取维度数据
        // 编写SQL
        String mysql = "CREATE TABLE MySQLTable (\n" +
                "  id int,\n" +
                "  dept STRING,\n" +
                "  ts int\n" +
                // "  PRIMARY KEY (id) NOT ENFORCED\n" +
                ") WITH (\n" +
                "   'connector' = 'jdbc',\n" +
                "   'url' = 'jdbc:mysql://192.168.200.132:3306/test',\n" +
                "   'username' = 'root', " +
                "   'password' = '1234', " +
                "   'table-name' = 'flinksql'\n" +
                ")";

        // 表环境中注册表
        tableEnv.executeSql(mysql);


        // TODO 4、lookup join连接
        // 编写sql
        String lookupJoin = "select k.id, k.name, m.dept, k.deptNum " +
                " from KafkaTable k join MySQLTable " +
                " FOR SYSTEM_TIME AS OF k.proc_time AS m" +
                " on k.deptNum = m.id ";

        // TODO 5、执行sql，并打印结果
        tableEnv.executeSql(lookupJoin).print();

    }
}
