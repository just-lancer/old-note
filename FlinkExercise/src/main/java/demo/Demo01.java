package demo;

import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.streaming.api.datastream.AllWindowedStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class Demo01 {
    public static void main(String[] args) throws Exception {
        // 获取流数据处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 获取数据源
        DataStreamSource<String> stringDataStreamSource = env.fromElements("a,1000", "b,2000", "c,3000", "d,4000");
        SingleOutputStreamOperator<String> stringSingleOutputStreamOperator = stringDataStreamSource.assignTimestampsAndWatermarks(WatermarkStrategy.<String>forMonotonousTimestamps()
                .withTimestampAssigner(
                        new SerializableTimestampAssigner<String>() {
                            @Override
                            public long extractTimestamp(String element, long recordTimestamp) {
                                return Long.parseLong(element.split(",")[1]);
                            }
                        }
                ));
        
        // 数据处理逻辑
        AllWindowedStream<String, TimeWindow> stringTimeWindowAllWindowedStream =
                stringSingleOutputStreamOperator.windowAll(SlidingEventTimeWindows.of(Time.seconds(10), Time.seconds(10)));

        stringTimeWindowAllWindowedStream.aggregate(
                new AggregateFunction<String, Integer, String>() {
                    @Override
                    public Integer createAccumulator() {
                        return 0;
                    }

                    @Override
                    public Integer add(String value, Integer accumulator) {
                        return accumulator + 1;
                    }

                    @Override
                    public String getResult(Integer accumulator) {
                        return accumulator.toString();
                    }

                    @Override
                    public Integer merge(Integer a, Integer b) {
                        return null;
                    }
                },

                new AllWindowFunction<String, String, TimeWindow>() {
                    @Override
                    public void apply(TimeWindow window, Iterable<String> values, Collector<String> out) throws Exception {
                        for (String s : values) {
                            System.out.println("主线程：" + Thread.currentThread());
                            System.out.println(s);
                        }
                    }
                }
        );

        // 执行当前流数据处理
        env.execute();

    }
}
