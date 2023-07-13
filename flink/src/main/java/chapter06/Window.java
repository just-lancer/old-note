package chapter06;

import Utils.Clicks;
import Utils.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.HashSet;


public class Window {
    public static void main(String[] args) throws Exception {
        // Flink窗口
        /**
         *  Flink窗口的本质：
         *      在其他框架中的窗口，是一个“框”，框住几个数据就是几个数据。
         *      Flink中的窗口是一个“桶”，数据流到来后，如果满足一个桶的要求，那么将这个数据丢到这个桶中
         *      因此，在Flink中会同时有多个桶的状态是打开的，允许向其中丢入数据的。
         *
         *      其他框架的窗口：窗口是静态的，数据是动态的，窗口关闭条件达到，框住几个数据就是几个数据
         *      Flink中的窗口：窗口是动态的，数据也是动态，数据到来后，如果满足一个窗口条件，将这个数据就丢到
         *          这个窗口中，如果满足多个窗口，就将这个数据丢到这多个桶中
         *
         *  窗口的分类：
         *      1、基于截取数据的方式，Flink窗口分为：时间窗口和计数窗口
         *      2、基于窗口分配数据的规则，Flink的窗口可以分为：
         *          -- 滚动窗口：主要参数为窗口大小。可基于时间或计数方式
         *          -- 滑动窗口：主要参数为窗口大小和滑动步长。可基于时间或计数方式
         *          -- 会话窗口：主要参数为会话gap。只能基于时间
         *          -- 全局窗口：窗口的触发需要一个触发器
         */

        /**
         *  Flink窗口AIP
         *      一个完整的Flink窗口算子包含两部分：一部分是窗口的定义，另一部分是窗口的行为，也就是窗口中的数据怎样操作
         *      在进行窗口操作之前还需要明确的一点是数据的分组状态。是对分组数据进行开窗还是对未分组数据进行开窗？
         *
         *      对于未分组数据进行开窗操作：未分组的开窗操作都是非并行的，及并行度为1，并不推荐
         *          在DataStream类中，有方法：
         *              -- public AllWindowedStream<T, GlobalWindow> countWindowAll(long size)
         *                  功能：用于创建滚动计数窗口
         *                  参数：size，表示计数窗口的大小，即窗口中数据条数
         *              -- public AllWindowedStream<T, GlobalWindow> countWindowAll(long size, long slide)
         *                  功能：用于创建滑动计数窗口
         *                  参数：size，表示计数窗口大小；slide，表示窗口滑动步长
         *              -- public <W extends Window> AllWindowedStream<T, W> windowAll(WindowAssigner<? super T, W> assigner)
         *                  功能：用于创建时间窗口
         *                  参数：assigner，是一个抽象类WindowAssigner的实例化对象，有四个抽象方法，用于创建滑动窗口还是滚动窗口
         *
         *      对于分组数据进行开窗操作：
         *          在KeyStream类中，有方法：
         *              -- public <W extends Window> WindowedStream<T, KEY, W> window(WindowAssigner<? super T, W> assigner)
         *                  功能：用于创建窗口类型
         *                  参数：同样需要传入一个窗口构建器WindowAssigner
         *
         */

        /**
         *  窗口分配器器WindowAssigner，一个抽象类，有常见的9个实现类：
         *      -- public class DynamicEventTimeSessionWindows<T> extends MergingWindowAssigner<T, TimeWindow>
         *          功能：用于创建动态事件时间会话窗口
         *      -- public class DynamicProcessingTimeSessionWindows<T> extends MergingWindowAssigner<T, TimeWindow>
         *          功能：用于创建动态处理时间会话窗口
         *      -- public class EventTimeSessionWindows extends MergingWindowAssigner<Object, TimeWindow>
         *          功能：用于创建事件时间会话窗口
         *      -- public class ProcessingTimeSessionWindows extends MergingWindowAssigner<Object, TimeWindow>
         *          功能：用于创建处理时间会话窗口
         *     ===========================================================================================================
         *     说明：抽象类MergingWindowAssigner继承自WindowAssigner
         *     ===========================================================================================================
         *      -- public class GlobalWindows extends WindowAssigner<Object, GlobalWindow>
         *          功能：用于创建全局窗口
         *      -- public class SlidingEventTimeWindows extends WindowAssigner<Object, TimeWindow>
         *          功能：用于创建滑动事件时间窗口
         *      -- public class SlidingProcessingTimeWindows extends WindowAssigner<Object, TimeWindow>
         *          功能：用于创建滑动处理时间窗口
         *      -- public class TumblingEventTimeWindows extends WindowAssigner<Object, TimeWindow>
         *          功能：用于创建滚动事件时间窗口
         *      -- public class TumblingProcessingTimeWindows extends WindowAssigner<Object, TimeWindow>
         *          功能；用于创建滚动处理时间窗口
         *
         */

        /**
         *  无论是按键分组窗口还是非按键分组窗口，在需要窗口分配器时，都可以直接调用这些实现类的静态方法of()传入相应的参数，
         *  创建窗口分配器。of()方法的返回值是对应的构建器
         */

        /**
         *  窗口函数：窗口中已经收集的数据的处理，Flink提供了相应的处理函数，即窗口函数
         *  窗口函数中较为轻量级的有：sum()、max()、min()等，较为重量级的有：归约 - reduce()，聚合 - aggregate()，windowFunction()
         *  和处理窗口函数processWindowFunction()。这些窗口函数中，最为底层和最常用的两个窗口函数是：aggregate()和processWindowFunction()
         *
         *  aggregate()窗口函数函数按照流的思想处理数据，数据来一条处理一条，用一个累加器来保存数据处理的中间状态，当数据需要输出时，将累加器的数据
         *  直接输出即可得到结果。
         *
         *  processWindowFunction()窗口函数按照批的思想处理数据，当窗口的所有数据都来到，需要对数据进行处理时，才会处理数据。其攒批的过程中，数据
         *  都被存放到一个迭代器中
         *
         *  两种处理方式各有利弊：对于流式数据处理，能够快速响应，快速输出结果。对于批式处理，虽然数据处理的效率较低，但是该方式能够获取窗口的上下文信息，
         *  用于丰富数据结果。
         *
         *  实际的使用都是两种方式的结合使用：
         *  即WindowedStream类型的数据在调用增量（流式处理）窗口函数reduce()和aggregate()函数时，除了传入第一个参数ReduceFunction、AggregateFunction的
         *  实现类对象，还能够传入第二个参数，一个全量（批式处理）窗口函数WindowFunction、ProcessWindowFunction的实现类对象。
         *
         *  数据在流式处理函数中通过流式方式处理，将中间状态保存到一个累加器中，然后在需要输出结果时，将累加器的值发送到批处理函数的迭代器中，这样迭代器中
         *  就只有一条数据，即最终结果，这样批处理窗口函数又能够快速输出结果，又能够获取上下文对象。
         *
         *
         */

        // 获取流处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 获取数据源
        DataStreamSource<Event> sourceData = env.addSource(new Clicks());

        // 为数据设置水位线和时间戳
        SingleOutputStreamOperator<Event> eventSingleOutputStreamOperator = sourceData.assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ZERO).withTimestampAssigner(
                new SerializableTimestampAssigner<Event>() {
                    @Override
                    public long extractTimestamp(Event element, long recordTimestamp) {
                        return element.time;
                    }
                }
        ));

        // 按键分组
        KeyedStream<Event, String> keyedSource = eventSingleOutputStreamOperator.keyBy(r -> r.page);

        // 开窗：滚动事件时间窗口
        WindowedStream<Event, String, TimeWindow> windowedSource = keyedSource.window(TumblingEventTimeWindows.of(Time.seconds(10)));

        // 设置聚合函数：aggregate()方法
        // 统计每个页面的pv数和uv数
        SingleOutputStreamOperator<String> aggregate = windowedSource.aggregate(new AggregateFunction<Event, Tuple2<Long, HashSet<String>>, Result02>() {
            @Override
            public Tuple2<Long, HashSet<String>> createAccumulator() {
                return Tuple2.of(0L, new HashSet<String>());
            }

            @Override
            public Tuple2<Long, HashSet<String>> add(Event value, Tuple2<Long, HashSet<String>> accumulator) {
                accumulator.f0 = accumulator.f0 + 1;
                accumulator.f1.add(value.page);
                return accumulator;
            }

            @Override
            public Result02 getResult(Tuple2<Long, HashSet<String>> accumulator) {
                return new Result02(accumulator.f0, new Long(accumulator.f1.size()));
            }

            @Override
            public Tuple2<Long, HashSet<String>> merge(Tuple2<Long, HashSet<String>> a, Tuple2<Long, HashSet<String>> b) {
                return null;
            }
        }, new ProcessWindowFunction<Result02, String, String, TimeWindow>() {
            @Override
            public void process(String s, Context context, Iterable<Result02> elements, Collector<String> out) throws Exception {
                Long pv = elements.iterator().next().pv;
                Long uv = elements.iterator().next().uv;
                long start = context.window().getStart();
                long end = context.window().getEnd();
                out.collect("url = " + s + ", pv = " + pv + ", uv = " + uv + ", startTime = " + start + ", endTime = " + end);
            }
        });

        sourceData.print("input");
        aggregate.print("output");

        env.execute();

    }

    // 定义一个POJO类，用于数据的输出
    public static class Result02 {
        public Long pv;
        public Long uv;

        public Result02() {
        }

        public Result02(Long pv, Long uv) {

            this.pv = pv;
            this.uv = uv;
        }

        @Override
        public String toString() {
            return "Result02{" + '\'' +
                    ", pv=" + pv +
                    ", uv=" + uv +
                    '}';
        }
    }
}
