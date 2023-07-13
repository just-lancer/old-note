package processfunction;

import datasource.ClickEvents;
import datasource.Event;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class Keyed_ProcessFunction_Requirement05 {
    public static void main(String[] args) throws Exception {
        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 获取数据
        DataStreamSource<Event> streamSource = env.addSource(new ClickEvents());

        // 执行逻辑：每隔5秒，统计一次各用户的PV
        streamSource
                .keyBy(ele -> ele.url)
                .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
                .aggregate(new MyAggregate(), new MyProcessWindowFunction())
                .print();


        // 执行流处理
        env.execute();
    }

    static class MyAggregate implements AggregateFunction<Event, Integer, String> {
        @Override
        public Integer createAccumulator() {
            return 0;
        }

        @Override
        public Integer add(Event value, Integer accumulator) {
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
    }

    static class MyProcessWindowFunction extends ProcessWindowFunction<String, String, String, TimeWindow> {
        @Override
        public void process(String s, Context context, Iterable<String> elements, Collector<String> out) throws Exception {
            System.out.println("页面：" + s + " 访问次数：" + elements.iterator().next());
        }
    }
}
