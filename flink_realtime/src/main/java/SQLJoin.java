import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import java.time.Duration;

/**
 * Author: shaco
 * Date: 2022/7/18
 * Desc: 使用Flink SQL 进行多表连接
 */
public class SQLJoin {
    public static void main(String[] args) throws Exception {
        // TODO 1、创建流执行环境以及表环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        env.setParallelism(1);

        // TODO 设置状态的生命周期
        /**
         * 在多表join的过程中，即使过很长时间，两张表也能够在满足连接条件的数据到达时，进行join，
         * 这是因为Flink状态后端将两张表的数据作为状态保存了起来
         * 在实时项目中，为了数据准确性，那么就需要一直将数据保存，长时间保存会导致内存压力过大
         * 因此，在实际情况中，会适当牺牲一定的准确性，来保证集群不崩溃
         * 使用的方法就是设置状态的生命周期
         */

        /**
         * 状态生命周期的更新，有两种：
         * 一种是：当状态发生变更时，即写入状态时，更新状态的生命周期
         * 另一种是：当状态被读取后，状态的生命周期更新
         */

        /**
         *                  左                   右
         * 内连接       OnCreateAndWrite    OnCreateAndWrite
         * 左外连接     OnReadAndWrite      OnCreateAndWrite
         * 右外连接     OnCreateAndWrite    OnReadAndWrite
         * 全外连接     OnReadAndWrite      OnReadAndWrite
         */
        // TODO 使用表环境进行设置
        // 10秒的生存时间
        tableEnv.getConfig().setIdleStateRetention(Duration.ofSeconds(10));

        // TODO 2、获取数据流，Flink SQL不需要分配水位线和时间戳
        SingleOutputStreamOperator<Emp> empDS = env.socketTextStream("hadoop132", 8888).map(
                new MapFunction<String, Emp>() {
                    @Override
                    public Emp map(String value) throws Exception {
                        // 将数据封装成一个Emp对象，并设置水位线
                        String[] split = value.split(",");
                        return new Emp(split[0], split[1], split[2], Long.parseLong(split[3]));
                    }
                }
        );

        SingleOutputStreamOperator<Dept> deptDS = env.socketTextStream("hadoop132", 9999).map(
                new MapFunction<String, Dept>() {
                    @Override
                    public Dept map(String value) throws Exception {
                        // 将数据封装成一个Emp对象，并设置水位线
                        String[] split = value.split(",");
                        return new Dept(split[0], split[1], Long.parseLong(split[2]));
                    }
                }
        );

        // TODO 3、在表环境中注册表
        tableEnv.createTemporaryView("emp", empDS);
        tableEnv.createTemporaryView("dept", deptDS);

        // TODO 4、编写SQL，进行多表join
        // TODO 4.1 内连接：sql语句编写及结果的执行
        // String innerJoin = "select e.id, e.name, d.name, e.deptNum from emp as e inner join dept as d on e.deptNum = d.id";
        // tableEnv.executeSql(innerJoin).print();

        // TODO 4.2 外连接：sql语句编写及结果的执行
        // String innerJoin = "select e.id, e.name, d.name, e.deptNum from emp as e left join dept as d on e.deptNum = d.id";
        // tableEnv.executeSql(innerJoin).print();

        // TODO 5、将多表连接结果写入到Kafka中
        // 5.1 创建一张动态表，用于输出表，并创建对应的要写入的Kafka主题
        // 建表语句
        String kafkaEmpTable = "CREATE TABLE kafka_emp (" +
                "  id string," +
                "  emp_name string," +
                "  dept_name string," +
                "  dept_num string," +
                "  PRIMARY KEY (id) NOT ENFORCED" +
                ") WITH (" +
                "  'connector' = 'upsert-kafka'," +
                "  'topic' = 'flinksql'," +
                "  'properties.bootstrap.servers' = 'hadoop132:9092'," +
                "  'key.format' = 'json'," +
                "  'value.format' = 'json'" +
                ")";

        // 在表环境中注册表，通过表环境执行SQL语句
        tableEnv.executeSql(kafkaEmpTable);

        // 左外连接测试
        // sql语句
        String leftJoin = "insert into kafka_emp select e.id, e.name, d.name, e.deptNum from emp as e left join dept as d on e.deptNum = d.id";
        // 执行sql
        tableEnv.executeSql(leftJoin).print();


        // TODO 最后一步，流数据处理执行
        env.execute();

    }
}
