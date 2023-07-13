package operator;

import datasource.ClickEvents;
import datasource.Event;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Operator01_Map {
    // 无状态算子01：Map
    // 需求：获取用户的姓名
    public static void main(String[] args) throws Exception {
        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 获取数据源
        DataStreamSource<Event> dataSource = env.addSource(new ClickEvents());

        // 无状态算子Map获取用户的姓名
        SingleOutputStreamOperator<String> outData = dataSource.map(
                new MapFunction<Event, String>() {
                    @Override
                    public String map(Event value) throws Exception {
                        return value.name;
                    }
                }
        );

        // 输出结果
        outData.print("out");

        // 流环境执行
        env.execute();
    }
}
