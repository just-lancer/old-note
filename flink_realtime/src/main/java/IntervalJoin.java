import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Duration;

/**
 * Author: shaco
 * Date: 2022/7/18
 * Desc: Flink间隔连接interval join的练习
 */
public class IntervalJoin {
    public static void main(String[] args) throws Exception {
        // TODO 1、获取流数据执行环境以及表环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // TODO 2、监控socket端口，获取数据流，并封装成对象，并设置水位线
        SingleOutputStreamOperator<Emp> empDS = env.socketTextStream("hadoop132", 8888).map(
                new MapFunction<String, Emp>() {
                    @Override
                    public Emp map(String value) throws Exception {
                        // 将数据封装成一个Emp对象，并设置水位线
                        String[] split = value.split(",");
                        return new Emp(split[0], split[1], split[2], Long.parseLong(split[3]));
                    }
                }
        ).assignTimestampsAndWatermarks(WatermarkStrategy.<Emp>forBoundedOutOfOrderness(Duration.ZERO).withTimestampAssigner(
                new SerializableTimestampAssigner<Emp>() {
                    @Override
                    public long extractTimestamp(Emp element, long recordTimestamp) {
                        return element.ts;
                    }
                }
        ));

        SingleOutputStreamOperator<Dept> deptDS = env.socketTextStream("hadoop132", 9999).map(
                new MapFunction<String, Dept>() {
                    @Override
                    public Dept map(String value) throws Exception {
                        // 将数据封装成一个Emp对象，并设置水位线
                        String[] split = value.split(",");
                        return new Dept(split[0], split[1], Long.parseLong(split[2]));
                    }
                }
        ).assignTimestampsAndWatermarks(WatermarkStrategy.<Dept>forBoundedOutOfOrderness(Duration.ZERO).withTimestampAssigner(
                new SerializableTimestampAssigner<Dept>() {
                    @Override
                    public long extractTimestamp(Dept element, long recordTimestamp) {
                        return element.ts;
                    }
                }
        ));

        // TODO 3、对两表数据进行分组
        KeyedStream<Emp, String> empKeyedDS = empDS.keyBy(ele -> ele.deptNum);
        KeyedStream<Dept, String> deptKeyedDS = deptDS.keyBy(ele -> ele.id);


        // TODO 4、进行多表连接
        // TODO 4.1、间隔连接：interval join
//        empKeyedDS
//                .intervalJoin(deptKeyedDS)
//                .between(Time.milliseconds(-5), Time.milliseconds(5))
//                .process(
//                        new ProcessJoinFunction<Emp, Dept, String>() {
//                            @Override
//                            public void processElement(Emp left, Dept right, Context ctx, Collector<String> out) throws Exception {
//                                out.collect(left.toString() + right.toString());
//                            }
//                        }
//                ).print(">>>>");


        // empDS.print(">>>>");

        // TODO 5、执行流数据处理
        env.execute();
    }
}


