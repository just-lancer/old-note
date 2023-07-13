package processfunction;

import datasource.Temp;
import datasource.TempMonitor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class Keyed_ProcessFunction_Requirement04 {
    public static void main(String[] args) throws Exception {
        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 获取数据源
        DataStreamSource<Temp> streamSource = env.addSource(new TempMonitor());

        // 执行逻辑：以输入的第一个温度为准，± 1℃允许，± 1 到 ± 3℃时间不超过10秒，允许，超过± 3℃，不允许，直接报警
        streamSource.keyBy(ele -> ele.monitor).process(
                new KeyedProcessFunction<String, Temp, String>() {
                    // 创建定时器值状态
                    ValueState<Long> ts_state;

                    // 创建第一个温度的值定时器
                    ValueState<Double> tem_state;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        ValueStateDescriptor<Long> ts_desc = new ValueStateDescriptor<>("ts_desc", Long.class);
                        ts_state = getRuntimeContext().getState(ts_desc);

                        ValueStateDescriptor<Double> tem_desc = new ValueStateDescriptor<>("tem_desc", Double.class);
                        tem_state = getRuntimeContext().getState(tem_desc);
                    }

                    @Override
                    public void processElement(Temp value, Context ctx, Collector<String> out) throws Exception {
                        // 如果是第一个值，直接更新状态
                        if (tem_state.value() == null) {
                            tem_state.update(value.temp);
                        } else {
                            // 如果是后续的值
                            if (Math.abs(value.temp - tem_state.value()) > 3.0) {
                                // 如果温度直接超过3℃，直接报警
                                System.out.println("监视器" + ctx.getCurrentKey() + "：温度波动超过3℃！");
                            } else if (Math.abs(value.temp - tem_state.value()) <= 3.0 && Math.abs(value.temp - tem_state.value()) > 1.0) {
                                // 如果温度没有超过3℃，需要进行时间判断
                                // 如果当前监视器没有定时器，说明是最近第一个波动超过1℃没有超过3℃的温度，此时应该注册定时器
                                // 如果当前监视器已经有定时器了，说明前面已经出现了波动超过1℃没有超过3℃的温度，需要对该温度进行判断
                                if (ts_state.value() == null) {
                                    // 如果没有定时器，注册定时器
                                    ctx.timerService().registerProcessingTimeTimer(ctx.timerService().currentProcessingTime() + 10 * 1000);
                                    ts_state.update(ctx.timerService().currentProcessingTime() + 10 * 1000);
                                } else {
                                    // 已经有定时器了，对定时器未触发之前的数据进行判断，
                                    // 如果温度恢复至可接受的状态，删除定时器
                                    if (Math.abs(value.temp - tem_state.value()) <= 1.0) {
                                        // 删除定时器
                                        // ctx.timerService().deleteProcessingTimeTimer(ts_state.value());
                                        ts_state.clear();
                                    } else {
                                        // 如果温度还大于1℃，小于3℃，还需要注册一个定时器
                                        ctx.timerService().registerProcessingTimeTimer(ctx.timerService().currentProcessingTime() + 10 * 1000);
                                        ts_state.update(ctx.timerService().currentProcessingTime() + 10 * 1000);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
                        // 定时时间到达，那么报警
                        System.out.println("监视器" + ctx.getCurrentKey() + "：温度波动超过1℃，未超过3℃，持续时间超过3秒！");
                        // 删除定时器
                        ts_state.clear();
                    }
                }
        );

        streamSource.print();
        env.execute();

    }
}
