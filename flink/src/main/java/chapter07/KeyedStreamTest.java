package chapter07;

import Utils.Clicks;
import Utils.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

import java.time.Duration;

public class KeyedStreamTest {
    // 测试内容很简单，就是看一下为什么只有键控流KeyedStream调用process()方法是，才能使用定时器相关操作
    public static void main(String[] args) throws Exception {
        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置全局并行度，以便测试
        env.setParallelism(1);

        // 获取数据源
        DataStreamSource<Event> sourceData = env.addSource(new Clicks());

        // 设置水位线策略和提取时间戳策略
        SingleOutputStreamOperator<Event> data = sourceData.assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ZERO).
                withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                    @Override
                    public long extractTimestamp(Event element, long recordTimestamp) {
                        return element.time;
                    }
                }));

        // 不进行按key分组，直接输出，并设置定时器，每10秒打印一句话
        // 测试结果是，不进行分组无法使用定时器
        KeyedStream<Event, Boolean> keyedData = data.keyBy(r -> true);
        SingleOutputStreamOperator<String> processResult = keyedData.process(new ProcessFunction<Event, String>() {
            // 注册一个状态，用来保存定时器时间戳
            ValueState<Long> timerts;

            @Override
            public void open(Configuration parameters) throws Exception {
                // 创建一个状态描述器
                ValueStateDescriptor<Long> timerState = new ValueStateDescriptor<>("timerState", Long.class);

                // 给定时器赋值
                timerts = getRuntimeContext().getState(timerState);
            }

            @Override
            public void processElement(Event value, Context ctx, Collector<String> out) throws Exception {
                // 每到一个数据，都判断一下是否已经有定时器了，如果有，那就不注册定时器，如果没有就注册一个定时器
                // 如何保存一个定时器呢，用状态保留
                if (timerts.value() == null) {
                    // 注册定时器
                    ctx.timerService().registerEventTimeTimer(System.currentTimeMillis() + 10 * 1000);
                    // 更新定时器状态
                    timerts.update(value.time);
                }

                // 注册定时器后，将Event转换成字符串输出
//                out.collect(value.toString());
            }

            @Override
            public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
                // 当定时器到来时，输出一句话
                System.out.println("定时器到来，定时器时间为：" + timestamp + "，当前系统时间为：" + System.currentTimeMillis() +
                        "；差值为：" + (System.currentTimeMillis() - timestamp));

                // 清空定时器
                timerts.clear();
            }
        });

        keyedData.print("input");
        processResult.print("output");

        // 流处理执行
        env.execute();
    }
}
