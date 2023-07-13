package operator;

import datasource.ClickEvents;
import datasource.Event;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Operator02_Filter {
    // 无状态算子02：filter
    // 需求：过滤姓名为zhangsan的用户
    public static void main(String[] args) throws Exception {
        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 获取数据源
        DataStreamSource<Event> dataSource = env.addSource(new ClickEvents());

        // 无状态算子filter
        SingleOutputStreamOperator<Event> outData = dataSource.filter(
                new FilterFunction<Event>() {
                    @Override
                    public boolean filter(Event value) throws Exception {
                        if ("zhangsan".equals(value.name)) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
        );

        // 输出结果
        outData.print("out");

        // 执行流数据处理
        env.execute();
    }
}
