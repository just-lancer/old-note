package processfunction;

import datasource.NumSource;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class Keyed_ProcessFunction_Requirement01 {
    public static void main(String[] args) throws Exception {
        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 获取数据源
        DataStreamSource<Integer> streamSource = env.addSource(new NumSource());

        // 逻辑处理：每隔10秒，输出一次数据流的统计信息
        streamSource.keyBy(ele -> 0).process(
                new KeyedProcessFunction<Integer, Integer, String>() {
                    // 为定时器时间创建值状态
                    ValueState<Long> ts_state;

                    // 为数据统计创建值累加器
                    ValueState<Result> acc_state;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        // 初始化时间状态
                        ValueStateDescriptor<Long> ts_desc = new ValueStateDescriptor<>("ts_desc", Long.class);
                        ts_state = getRuntimeContext().getState(ts_desc);

                        // 初始化值累加器
                        ValueStateDescriptor<Result> acc_desc = new ValueStateDescriptor<>("acc_desc", Result.class);
                        acc_state = getRuntimeContext().getState(acc_desc);
                    }

                    @Override
                    public void processElement(Integer value, Context ctx, Collector<String> out) throws Exception {
                        // 当定时器的状态的值为null时，表明是第一条数据
                        if (ts_state.value() == null) {
                            // 注册一个定时器，以处理时间为准
                            ctx.timerService().registerProcessingTimeTimer(System.currentTimeMillis() + 10 * 1000);

                            // 更新定时器状态的值
                            ts_state.update(System.currentTimeMillis() + 10 * 1000);
                        }

                        // 当累加器的状态的值为null时，表明是第一条数据
                        if (acc_state.value() == null) {
                            String odd_even;
                            if (ctx.getCurrentKey() == 0) {
                                odd_even = "偶数";
                            } else {
                                odd_even = "奇数";
                            }

                            // 更新状态
                            acc_state.update(new Result(odd_even, 1.0, value + 0.0, value + 0.0, value, value));
                        } else {
                            String odd_even;
                            if (ctx.getCurrentKey() == 0) {
                                odd_even = "偶数";
                            } else {
                                odd_even = "奇数";
                            }

                            Double count = acc_state.value().count + 1;
                            Double sum = acc_state.value().sum + value;
                            Double avg = count / sum;
                            Integer max = Math.max(value, acc_state.value().max);
                            Integer min = Math.min(value, acc_state.value().min);

                            // 更新状态
                            acc_state.update(new Result(odd_even, count, sum, avg, max, min));
                        }
                    }

                    @Override
                    public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
                        System.out.println("" +
                                "定时时间到达：" + timestamp + "\n" + acc_state.value().toString());

                        // 清空定时器状态
                        ts_state.clear();
                    }
                }
        );

        streamSource.print("input");

        // 执行流数据处理
        env.execute();
    }
}

class Result {
    public String odd_even;
    public Double count; // 总数
    public Double sum; // 总和
    public Double avg; // 平均值
    public Integer max; // 最大值
    public Integer min; //最小值

    public Result() {
    }

    public Result(String odd_even, Double count, Double sum, Double avg, Integer max, Integer min) {
        this.odd_even = odd_even;
        this.count = count;
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
    }

    @Override
    public String toString() {
        return "Result{" +
                "odd_even='" + odd_even + '\'' +
                ", count=" + count +
                ", sum=" + sum +
                ", avg=" + avg +
                ", max=" + max +
                ", min=" + min +
                '}';
    }
}