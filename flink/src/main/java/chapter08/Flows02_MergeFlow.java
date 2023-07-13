package chapter08;

import Utils.Clicks;
import Utils.ClicksUserInfo;
import Utils.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.Iterator;

public class Flows02_MergeFlow {
    // 多流操作——合流
    public static void main(String[] args) throws Exception {
        function1();
    }

    // 合流情况一：多条类型相同的数据流合并
    // 调用数据流的union()方法
    public static void function1() throws Exception {
        // 获取流式数据处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置并行度
        env.setParallelism(1);

        // 获取数据源
        DataStreamSource<Event> dataSource1 = env.addSource(new Clicks());
        DataStreamSource<Event> dataSource2 = env.addSource(new Clicks());

        // 为数据流1设置水位线配置策略以及指定时间戳获取策略
        SingleOutputStreamOperator<Event> timeSetSource1 = dataSource1.assignTimestampsAndWatermarks(WatermarkStrategy
                .<Event>forBoundedOutOfOrderness(Duration.ZERO).
                        withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.time;
                            }
                        }));

        // 为数据流1设置水位线配置策略以及指定时间戳获取策略
        SingleOutputStreamOperator<Event> timeSetSource2 = dataSource2.assignTimestampsAndWatermarks(WatermarkStrategy
                .<Event>forBoundedOutOfOrderness(Duration.ZERO).
                        withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.time;
                            }
                        }));

        // 合并两个数据源
        // 可以多个数据源同时合并
        DataStream<Event> unionData = timeSetSource1.union(timeSetSource2);

        // 优化一下输出，每隔10秒输出一次合并结果
        SingleOutputStreamOperator<String> process = unionData.keyBy(r -> true).process(
                new KeyedProcessFunction<Boolean, Event, String>() {
                    // 定义一个值状态，用于存储定时器时间戳
                    ValueState<Long> ts_state;

                    // 还是需要创建按一个list状态，用于收集这些数据
                    ListState<Event> event_state;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        // 创建值状态描述器
                        ValueStateDescriptor<Long> my_descriptor = new ValueStateDescriptor<>("ts_descriptor", Long.class);

                        // 给值状态初始化
                        ts_state = getRuntimeContext().getState(my_descriptor);

                        // 创建List描述器
                        // 使用Types.POJO(Event.class)也可以
                        ListStateDescriptor<Event> event_descriptor = new ListStateDescriptor<>("event_descriptor", Event.class);

                        // 给list状态初始化
                        event_state = getRuntimeContext().getListState(event_descriptor);
                    }

                    @Override
                    public void processElement(Event value, Context ctx, Collector<String> out) throws Exception {
                        // 当Event数据到来时，添加到List状态中
                        event_state.add(value);

                        // 设置一个定时器，每隔10秒才输出一个合并后的数据
                        if (ts_state.value() == null) {
                            ctx.timerService().registerEventTimeTimer(value.time + 10 * 1000L);
                        }

                    }

                    @Override
                    public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
                        Iterable<Event> events = event_state.get();

                        Iterator<Event> iterator = events.iterator();

                        while (iterator.hasNext()) {
                            out.collect(iterator.next().toString());
                        }

                        event_state.clear();
                        ts_state.clear();
                    }
                }
        );

        // 打印输出
        timeSetSource1.print("input1");
        timeSetSource2.print("input2");
//        unionData.print("output");

        process.print("output");
        // 执行输出
        env.execute();
    }

    // 合流情况二：两个数据类型不同的数据流的连接
    // 调用DataStream的connect()方法

    public static void function2() {
        // 获取流式数据处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置并行度
        env.setParallelism(1);

        // 获取数据源
        DataStreamSource<Event> dataSource1 = env.addSource(new Clicks());
        DataStreamSource<Event> dataSource2 = env.addSource(new ClicksUserInfo());

        // 为数据流1设置水位线配置策略以及指定时间戳获取策略
        SingleOutputStreamOperator<Event> timeSetSource1 = dataSource1.assignTimestampsAndWatermarks(WatermarkStrategy
                .<Event>forBoundedOutOfOrderness(Duration.ZERO).
                        withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.time;
                            }
                        }));

        // 为数据流1设置水位线配置策略以及指定时间戳获取策略
        SingleOutputStreamOperator<Event> timeSetSource2 = dataSource2.assignTimestampsAndWatermarks(WatermarkStrategy
                .<Event>forBoundedOutOfOrderness(Duration.ZERO).
                        withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.time;
                            }
                        }));

        // 合并两个数据源，调用数据流的connect()方法
        ConnectedStreams<Event, Event> connect = dataSource1.connect(dataSource2);

        // 调用keyBy()指定两个流中的key
        ConnectedStreams<Event, Event> eventEventConnectedStreams = connect.keyBy(0, 0);

    }

}
