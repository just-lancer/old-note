package chapter08;

import Utils.Clicks;
import Utils.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.time.Duration;

public class Flows01_SplitFlow {
    // 多流操作——分流
    public static void main(String[] args) throws Exception {
        // function1();
        function2();

    }

    // 分流操作方式一：使用过滤的方式
    public static void function1() throws Exception {
        // 获取流式数据处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置并行度
        env.setParallelism(1);

        // 获取数据源
        DataStreamSource<Event> dataSource = env.addSource(new Clicks());

        // 为数据设置水位线配置策略以及指定时间戳获取策略
        SingleOutputStreamOperator<Event> timeSetSource = dataSource.assignTimestampsAndWatermarks(WatermarkStrategy
                .<Event>forBoundedOutOfOrderness(Duration.ZERO).
                        withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.time;
                            }
                        }));

        // 进行分流操作
        // 分流要求：没什么要求，将姓名为zhangsan和lisi的Event对象分别放入其他的两个流中，其他的数据不要
        SingleOutputStreamOperator<Event> output1 = timeSetSource.flatMap(new FlatMapFunction<Event, Event>() {
            @Override
            public void flatMap(Event value, Collector<Event> out) throws Exception {
                if ("zhangsan".equals(value.name)) {
                    out.collect(value);
                }
            }
        });

        SingleOutputStreamOperator<Event> output2 = timeSetSource.flatMap(new FlatMapFunction<Event, Event>() {
            @Override
            public void flatMap(Event value, Collector<Event> out) throws Exception {
                if ("lisi".equals(value.name)) {
                    out.collect(value);
                }
            }
        });

        output1.print("output:zhangsan");
        output2.print("output:lisi");

        // 执行流处理
        env.execute();
    }

    // 分流操作方式二：使用测输出流，将数据进行分流
    public static void function2() throws Exception {
        // 获取流式数据处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置并行度
        env.setParallelism(1);

        // 获取数据源
        DataStreamSource<Event> dataSource = env.addSource(new Clicks());

        // 为数据设置水位线配置策略以及指定时间戳获取策略
        SingleOutputStreamOperator<Event> timeSetSource = dataSource.assignTimestampsAndWatermarks(WatermarkStrategy
                .<Event>forBoundedOutOfOrderness(Duration.ZERO).
                        withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.time;
                            }
                        }));

        // 进行分流操作
        // 将姓名为zhangsan的Event数据输出到侧输出流中
        SingleOutputStreamOperator<Event> outputSide = timeSetSource.process(new ProcessFunction<Event, Event>() {
            @Override
            public void processElement(Event value, Context ctx, Collector<Event> out) throws Exception {
                if ("zhangsan".equals(value.name)) {
                    ctx.output(new OutputTag<Event>("zhangsan") {
                    }, value);
                } else {
                    out.collect(value);
                }
            }
        });

        // 获取侧输出流
        // 利用处理结果得到的流对象调用getSideOutput()方法，并传入相应的侧输出流标签对象outputTag，即可得到侧输出流数据
        DataStream<Event> zhangsan = outputSide.getSideOutput(new OutputTag<Event>("zhangsan") {
        });

        //输出数据
        dataSource.print("input");
        zhangsan.print("sideOutput");

        // 执行流处理
        env.execute();
    }
}
