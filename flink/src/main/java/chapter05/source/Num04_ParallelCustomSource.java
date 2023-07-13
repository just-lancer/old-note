package chapter05.source;

import Utils.Event;
import Utils.ParallelClicks;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Num04_ParallelCustomSource {
    // 自定义并行数据源
    // 自定义并行数据源，除了实现的接口是parallelSourceFunction以外，其内部的重写函数依然是run()和cancel()方法
    // 需要定义的逻辑也是相同的
    public static void main(String[] args) throws Exception {
        // 获取流式处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 获取数据源并设置并行度为2
        DataStreamSource<Event> parallelSource = env.addSource(new ParallelClicks()).setParallelism(2);

        DataStreamSink<Event> print = parallelSource.print();

        // 执行流处理
        env.execute();
    }
}
