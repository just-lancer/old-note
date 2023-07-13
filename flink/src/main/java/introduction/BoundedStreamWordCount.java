package introduction;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class BoundedStreamWordCount {
    // flink入门程序，单词计数
    public static void main(String[] args) throws Exception {
        // 1、获取流式执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 2、获取有界数据源，从文本文件中读取
        DataStreamSource<String> lineData = env.readTextFile("flink\\data\\input01.txt");

        // 3、单词计数逻辑处理
        SingleOutputStreamOperator<Tuple2<String, Integer>> wordAndOne = lineData.flatMap(
                new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                        String[] s1 = s.split(" ");
                        for (String word : s1) {
                            collector.collect(Tuple2.of(word, 1));
                        }
                    }
                }
        );

        KeyedStream<Tuple2<String, Integer>, String> wordAndOneGroup = wordAndOne.keyBy(
                new KeySelector<Tuple2<String, Integer>, String>() {
                    @Override
                    public String getKey(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                        return stringIntegerTuple2.f0;
                    }
                }
        );

        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = wordAndOneGroup.sum(1);

        sum.print();

        // 4、执行处理流程
        env.execute();

    }
}

