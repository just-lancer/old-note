package processfunction;

import datasource.ClickEvents;
import datasource.Event;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class Keyed_ProcessFunction_Requirement03 {
    public static void main(String[] args) throws Exception {
        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 获取数据
        DataStreamSource<Event> streamSource = env.addSource(new ClickEvents());

        // 执行逻辑：每隔30秒，统计一次各用户的PV数，每个用户对每个页面的访问次数
        // PV（浏览量）:店铺各页面被查看的次数。用户多次打开或刷新同一个页面,该指标值累加。
        // UV（访客数）：全店各页面的访问人数。所选时间段内,同一访客多次访问会进行去重计算
        // 它们的区别是，对用户去重和不去重
        streamSource.keyBy(ele -> ele.name).process(
                new KeyedProcessFunction<String, Event, String>() {
                    // 创建定时器的值状态
                    ValueState<Long> ts_state;

                    // 创建Map状态累加器，用于存储每个用户对每个页面的访问次数
                    MapState<String, Long> res_state;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        // 初始化定时器状态
                        ValueStateDescriptor<Long> ts_desc = new ValueStateDescriptor<>("ts_desc", Long.class);
                        ts_state = getRuntimeContext().getState(ts_desc);

                        // 初始化累加器状态
                        MapStateDescriptor<String, Long> res_desc = new MapStateDescriptor<>("res_desc", String.class, Long.class);
                        res_state = getRuntimeContext().getMapState(res_desc);
                    }

                    @Override
                    public void processElement(Event value, Context ctx, Collector<String> out) throws Exception {
                        // 定时器逻辑
                        if (ts_state.value() == null) {
                            ctx.timerService().registerProcessingTimeTimer(System.currentTimeMillis() + 10 * 1000);

                            ts_state.update(System.currentTimeMillis());
                        }

                        // 需求处理逻辑
                        if (!res_state.contains(value.url)) {
                            // 如果是第一次访问该页面
                            res_state.put(value.url, 1L);
                        } else {
                            // 如果不是第一次访问该页面
                            res_state.put(value.url, res_state.get(value.url) + 1);
                        }
                    }

                    @Override
                    public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
                        System.out.print("定时时间到达：");
                        Iterable<String> keys = res_state.keys();
                        for (String key : keys) {
                            System.out.print(key + " : ");
                            System.out.print(res_state.get(key) + "\t");
                        }
                        System.out.println();

                        // 清除定时器
                        ts_state.clear();
                    }
                }
        );
        // 执行流数据处理
        env.execute();
    }
}
