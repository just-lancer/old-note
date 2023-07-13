package app.dim;

import com.alibaba.fastjson.JSONObject;
import common.KafkaConstants;
import function.DIMBroadcastProcessFunction;
import function.DIMHBaseRichSinkFunction;
import function.DIMKafkaDSFilter;
import function.DIMKafkaDSMap;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.BroadcastConnectedStream;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import utils.FlinkCDCUtils;
import utils.KafkaUtils;
import utils.StateDescriptorUtils;

/**
 * Author: shaco
 * Date: 2022/6/28
 * Desc: 实时数仓，dim层数据处理程序
 */
public class DIMDataProcess {
    public static void main(String[] args) {
        // TODO 获取流执行环境，并设置并行度
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);

        // TODO 设置检查点
        // env.enableCheckpointing(3000L, CheckpointingMode.EXACTLY_ONCE);
        // env.getCheckpointConfig().setCheckpointTimeout(60 * 1000L);
        // env.getCheckpointConfig().setMinPauseBetweenCheckpoints(3000L);
        // env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        // env.setRestartStrategy(RestartStrategies.failureRateRestart(10, Time.of(1L, TimeUnit.DAYS), Time.of(3L, TimeUnit.MINUTES)));
        // env.setStateBackend(new HashMapStateBackend());
        // env.getCheckpointConfig().setCheckpointStorage("hdfs://hadoop132:9820/gmall/ck");
        // System.setProperty("HADOOP_USER_NAME", "atguigu");

        // TODO 获取数据
        // TODO 1、从Kafka中获取业务数据库的数据
        DataStreamSource<String> kafkaDS = env.addSource(KafkaUtils.getKafkaConsumer(KafkaConstants.DIM_TOPICS, KafkaConstants.DIM_CONSUMER_GROUPID));

        // 对Kafka中获取的数据进行简单的处理：映射成一个对象，过滤掉不需要的数据
        SingleOutputStreamOperator<JSONObject> processedKafkaDS = kafkaDS
                .map(new DIMKafkaDSMap())
                .filter(new DIMKafkaDSFilter());

        // TODO 2、利用Flink CDC获取配置表数据
        DataStreamSource<String> configDS = env.fromSource(FlinkCDCUtils.getMySqlSource(), WatermarkStrategy.noWatermarks(), "configSource");

        // 将配置流广播
        BroadcastStream<String> broadcast = configDS.broadcast(StateDescriptorUtils.getDIMBroadcastStateDescriptor());

        // TODO 3、将主业务数据流和广播流连接，并处理
        BroadcastConnectedStream<JSONObject, String> connectDS = processedKafkaDS.connect(broadcast);
        SingleOutputStreamOperator<JSONObject> processedConnectDS = connectDS.process(new DIMBroadcastProcessFunction());

        // TODO 4、将处理后的业务数据写入到HBase中
        processedConnectDS.addSink(new DIMHBaseRichSinkFunction());

        // 测试数据流
        // 广播流
        // configDS.print(">>>>");
        // 业务流
        // processedKafkaDS.print("^^^^");

        // 执行流数据处理
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
