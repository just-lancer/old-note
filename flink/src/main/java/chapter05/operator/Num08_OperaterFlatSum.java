package chapter05.operator;

import Utils.Event;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


public class Num08_OperaterFlatSum {
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

        // 求用户访问的时间戳之和，调用sum()方法
        /*
            需要注意的是：对于求最大值和最小值的max()、maxBy()和min()、minBy()的这两组方法
            max()方法只会更新指定的字段的值，其他的字段保留第一条数据的对应字段
            maxBy()方法会同步更新其他的字段
         */
        KeyedStream<Event, String> eventStringKeyedStream = eventDataStreamSource.keyBy(new KeySelector<Event, String>() {
            @Override
            public String getKey(Event event) throws Exception {
                return event.name;
            }
        });


        SingleOutputStreamOperator<Event> time = eventStringKeyedStream.sum("time");

        time.print().setParallelism(1);

        env.execute();
    }
}
