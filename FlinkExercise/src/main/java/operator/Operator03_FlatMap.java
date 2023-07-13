package operator;

import datasource.ClickEvents;
import datasource.Event;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class Operator03_FlatMap {
    // 无状态算子03：FlatMap
    public static void main(String[] args) throws Exception {
        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 获取数据源
        DataStreamSource<Event> dataSource = env.addSource(new ClickEvents());

        // 无状态算子flatMap：对数据过滤，过滤姓名为zhangsan的用户
        SingleOutputStreamOperator<String> outData = dataSource.flatMap(
                new FlatMapFunction<Event, String>() {
                    @Override
                    public void flatMap(Event value, Collector<String> out) throws Exception {
                        if (!"zhangsan".equals(value.name)) {
                            out.collect(value.name);
                        }
                    }
                }
        );

        // 输出数据
        outData.print("out");

        // 执行流数据处理
        env.execute();
    }
}
