package processfunction;

import datasource.ClickEvents;
import datasource.Event;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class Keyed_ProcessFunction_Requirement06 {
    public static void main(String[] args) throws Exception {
        // 利用KeyedProcessFunction实现，带累加器的窗口
        // 需求依然是：每隔5秒统计一次各用户的pv数

        // 获取数据源
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 获取数据源
        DataStreamSource<Event> streamSource = env.addSource(new ClickEvents());
        streamSource.print("input");

        // 执行逻辑
        // 利用处理时间作为窗口函数
        streamSource.keyBy(ele -> ele.name).process(
                new MyKeyedProcessFunction(5000L)
        ).print();


        // 执行流数据处理
        env.execute();
    }


}

class MyKeyedProcessFunction extends KeyedProcessFunction<String, Event, String> {
    private Long size;

    public MyKeyedProcessFunction(Long size) {
        this.size = size;
    }

    // 需要为每一个窗口创建一个Map类型的状态
    // 必须是能够标记状态属于哪个窗口的，不然其他窗口的值可能就访问到了这个状态
    MapState<Long, Integer> acc_state;

    // 不需要定时器状态，窗口会有开始结束时间戳，直接注册定时器就行了

    @Override
    public void open(Configuration parameters) throws Exception {
        MapStateDescriptor<Long, Integer> acc_desc = new MapStateDescriptor<>("acc_desc", Long.class, Integer.class);
        acc_state = getRuntimeContext().getMapState(acc_desc);
    }

    @Override
    public void processElement(Event value, Context ctx, Collector<String> out) throws Exception {
        // 每来一条数据，需要确定这个数据属于哪个窗口
        // 确定窗口的开始时间和结束时间
        Long procTS = ctx.timerService().currentProcessingTime();
        Long startWindow = procTS - procTS % size;
        Long endWindow = startWindow + size;

        // 如果是该窗口的第一条数据，那么开启窗口
        // 以窗口开始时间标记窗口
        // 以窗口结束时间 - 1结束窗口
        if (acc_state.get(startWindow) == null) {
            acc_state.put(startWindow, 1);
        } else {
            acc_state.put(startWindow, acc_state.get(startWindow) + 1);
        }

        // 注册定时器
        ctx.timerService().registerProcessingTimeTimer(endWindow - 1);
    }

    @Override
    public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
        // 说明数据都已经到达，输出结果
        System.out.println("==============================");
        System.out.println("窗口：" + timestamp + "\t" + "用户：" + ctx.getCurrentKey() + "\t" + "PV数：" + acc_state.get(timestamp - size + 1));
        System.out.println("==============================");
    }
}

