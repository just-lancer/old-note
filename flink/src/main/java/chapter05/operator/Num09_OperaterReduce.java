package chapter05.operator;

import Utils.Event;
import Utils.Result01;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


public class Num09_OperaterReduce {
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

        // 简单统计，输出每个用户数据的条数，以及访问时间的最大值和最小值，结果用一个POJO类表示
        // 数据处理，将数据处理成我们想要的形式
        SingleOutputStreamOperator<Result01> map = eventDataStreamSource.map(new MapFunction<Event, Result01>() {
            @Override
            public Result01 map(Event event) throws Exception {
                return new Result01(event.name, event.time, event.time, event.time);
            }
        });
        // 分组
        KeyedStream<Result01, String> result01StringKeyedStream = map.keyBy(new KeySelector<Result01, String>() {
            @Override
            public String getKey(Result01 result01) throws Exception {
                return result01.name;
            }
        });

        // 聚合
        SingleOutputStreamOperator<Result01> reduce = result01StringKeyedStream.reduce(new ReduceFunction<Result01>() {
            @Override
            public Result01 reduce(Result01 result01, Result01 t1) throws Exception {
                return new Result01(result01.name, result01.sum + t1.sum, Math.max(result01.max, t1.max), Math.min(result01.min, t1.min));

            }
        });

        reduce.print();

        env.execute();
    }
}
