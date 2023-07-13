package chapter05.operator;

import Utils.Event;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;


public class Num07_OperaterFlatMap {
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

        // 将输入的每条数据输出为一个二元组，如("zhangsan", 1)
        SingleOutputStreamOperator<Tuple2<String, Integer>> result = eventDataStreamSource.flatMap(new FlatMapFunction<Event, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(Event event, Collector<Tuple2<String, Integer>> collector) throws Exception {
                collector.collect(Tuple2.of(event.name, 1));
            }
        });

        result.print();

        env.execute();
    }
}
