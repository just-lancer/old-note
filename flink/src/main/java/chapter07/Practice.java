package chapter07;

import Utils.Clicks;
import Utils.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Practice {
    // 处理函数
    // 实操：统计最近10秒钟内最热门的两个url链接，并且每5秒钟更新一次
    // 热度用访问量（pv）表示
    public static void main(String[] args) throws Exception {
        // 获取流处理执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 获取数据源
        DataStreamSource<Event> source = env.addSource(new Clicks());

        // 为数据指定时间戳以及设置水位线生成策略
        SingleOutputStreamOperator<Event> tsSource = source.assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ZERO).withTimestampAssigner(
                new SerializableTimestampAssigner<Event>() {
                    @Override
                    public long extractTimestamp(Event element, long recordTimestamp) {
                        return element.time;
                    }
                }
        ));

        // 按url分组
        KeyedStream<Event, String> keyedSource = tsSource.keyBy(data -> data.page);

        // 设置窗口函数：滑动窗口，大小：10s，步长：5s
        WindowedStream<Event, String, TimeWindow> window = keyedSource.window(SlidingEventTimeWindows.of(Time.seconds(10), Time.seconds(5)));

        // 开始窗口内的数据处理
        /*
        对于数据，需要每隔url的pv数据，还需要将当前的窗口信息获取，所以既需要增量数据处理，也需要全量数据处理
        将处理得到的结果保存到一个POJO类中
         */
        SingleOutputStreamOperator<PVCountResult> incFullAggregate = window.aggregate(new MyAggregateFunction(), new MyProcessWindowFunction());

        // 现在已经将数据进行了第一波的聚合，下面还需要进行一次采集和开窗，聚合
        source.print("input");
        incFullAggregate.print("output");


        env.execute();

    }

    // 创建AggregateFunction的实例化对象
    public static class MyAggregateFunction implements AggregateFunction<Event, PVCountResult, PVCountResult> {
        @Override
        public PVCountResult createAccumulator() {
            return new PVCountResult("", 0L, 0L, 0L);
        }

        @Override
        public PVCountResult add(Event value, PVCountResult accumulator) {
            accumulator.pv = accumulator.pv + 1;
            return accumulator;
        }

        @Override
        public PVCountResult getResult(PVCountResult accumulator) {
            return accumulator;
        }

        @Override
        public PVCountResult merge(PVCountResult a, PVCountResult b) {
            return null;
        }
    }

    // 创建ProcessWindowFunction的实例化对象
    public static class MyProcessWindowFunction extends ProcessWindowFunction<PVCountResult, PVCountResult, String, TimeWindow> {
        @Override
        public void process(String s, Context context, Iterable<PVCountResult> elements, Collector<PVCountResult> out) {
            PVCountResult result = new PVCountResult();

            PVCountResult next = elements.iterator().next();

            result.url = s;
            result.pv = next.pv;
            result.endTime = context.window().getEnd();
            result.startTime = context.window().getStart();

            out.collect(result);

        }
    }

    // 第一次开窗输出结果的POJO类
    public static class PVCountResult {
        public String url;
        public Long pv;
        public Long startTime;
        public Long endTime;

        public PVCountResult() {
        }

        public PVCountResult(String url, Long pv, Long startTime, Long endTime) {
            this.url = url;
            this.pv = pv;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        @Override
        public String toString() {
            return "PVCountResult{" +
                    "url='" + url + '\'' +
                    ", pv=" + pv +
                    ", startTime=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(startTime)) +
                    ", endTime=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(endTime)) +
                    '}';
        }
    }
}
