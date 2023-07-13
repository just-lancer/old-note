package processfunction;

import datasource.ClickEvents;
import datasource.Event;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

public class Stateless_ProcessFunction {
    // 无状态处理函数：ProcessFunction
    public static void main(String[] args) throws Exception {
        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 获取数据源
        DataStreamSource<Event> dataSource = env.addSource(new ClickEvents());

        // 无状态处理函数：ProcessFunction，实现无状态算子map，filter，flatMap
        SingleOutputStreamOperator<String> outData = dataSource.process(
                new ProcessFunction<Event, String>() {
                    @Override
                    public void processElement(Event value, Context ctx, Collector<String> out) throws Exception {
                        // 如果用户姓名为zhangsan，那么过滤该数据：filter
                        if (!"zhangsan".equals(value.name)) {
                            // 如果用户姓名为wangwu，获取用户的姓名：map
                            if ("wangwu".equals(value.name)) {
                                out.collect(value.name);
                            } else if ("lisi".equals(value.name)) {
                                // 如果用户姓名为lisi，连续两次数据该用户的数据
                                out.collect(value.name);
                                out.collect(value.name);
                            } else {
                                // 其他用户输出other
                                out.collect("other");
                            }
                        }
                    }
                }
        );

        // 输出数据
        outData.print();

        // 执行流数据处理
        env.execute();
    }
}
