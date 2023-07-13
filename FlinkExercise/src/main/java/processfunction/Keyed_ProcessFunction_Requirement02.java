package processfunction;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.Collections;

public class Keyed_ProcessFunction_Requirement02 {
    public static void main(String[] args) throws Exception {
        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 获取数据
        DataStreamSource<Integer> streamSource = env.fromElements(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // 执行逻辑：对数据按奇偶升序排序
        streamSource.keyBy(ele -> ele % 2).process(
                new KeyedProcessFunction<Integer, Integer, String>() {
                    // 申明一个列表状态，用于接收奇数或者偶数
                    ListState<Integer> acc_state;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        ListStateDescriptor<Integer> acc_desc = new ListStateDescriptor<>("acc_desc", Integer.class);
                        acc_state = getRuntimeContext().getListState(acc_desc);
                    }

                    @Override
                    public void processElement(Integer value, Context ctx, Collector<String> out) throws Exception {
                        // 逻辑处理
                        acc_state.add(value);

                        Iterable<Integer> integers = acc_state.get();
                        ArrayList<Integer> res = new ArrayList<>();

                        for (Integer i : integers) {
                            res.add(i);
                        }

                        Collections.sort(res);

                        System.out.println(res);
                    }
                }
        );

        // 执行流数据处理
        env.execute();
    }
}
