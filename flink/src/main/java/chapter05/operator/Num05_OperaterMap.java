package chapter05.operator;

import Utils.Event;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Num05_OperaterMap {
    // Map算子的使用
    public static void main(String[] args) throws Exception {
        // 获取流处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 获取数据源
        DataStreamSource<Event> eventDataStreamSource = env.fromElements(
                new Event("lisi", "/home", -4832377117453491519L),
                new Event("zhangsan", "/payment", -8356692831163685318L),
                new Event("lisi", "/sku_info", -2046974886125370955L),
                new Event("zhangsan", "/sku_info", -5562017592536961239L),
                new Event("wangwu", "/sku_info", -145223812554166460L),
                new Event("zhaoliu", "/cart", -7017030832878705529L),
                new Event("wangwu", "/sku_info", -8571205379612016868L),
                new Event("lisi", "/cart", -7033650789022819438L)
        );

        // 获取每条数据（对象）的name属性
        SingleOutputStreamOperator<String> map = eventDataStreamSource.map(new MapFunction<Event, String>() {
            @Override
            public String map(Event event) {
                return event.name;
            }
        });

        map.print();

        env.execute();
    }
}
