package app.dwd;

import com.alibaba.fastjson.JSONObject;
import common.DWDTrafficOutputTagConstants;
import common.KafkaConstants;
import function.DWDTrafficDirtyDataProcessFunction;
import function.DWDTrafficDistributeProcessFunction;
import function.DWDTrafficUserRepairKeyedProcessFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import utils.KafkaUtils;

/**
 * Author: shaco
 * Date: 2022/7/1
 * Desc: dwd层，流量域，用户行为日志解析
 * 将用户行为日志，根据不同的操作进行解析，并写入到Kafka相应的主题中
 */
public class DWD_Traffic__LogParse {
    public static void main(String[] args) throws Exception {
        // TODO 1、获取流执行环境，并设置并行度
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);

        // TODO 2、检查点设置
        // env.enableCheckpointing(3000L, CheckpointingMode.EXACTLY_ONCE);
        // env.getCheckpointConfig().setCheckpointTimeout(60 * 1000L);
        // env.getCheckpointConfig().setMinPauseBetweenCheckpoints(3000L);
        // env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        // env.setRestartStrategy(RestartStrategies.failureRateRestart(10, Time.of(1L, TimeUnit.DAYS), Time.of(3L, TimeUnit.MINUTES)));
        // env.setStateBackend(new HashMapStateBackend());
        // env.getCheckpointConfig().setCheckpointStorage("hdfs://hadoop132:9820/gmall/ck");
        // System.setProperty("HADOOP_USER_NAME", "atguigu");

        // TODO 3、从Kafka中读取数据
        DataStreamSource<String> kafkaDS = env
                .addSource(KafkaUtils.getKafkaConsumer(KafkaConstants.DWD_TRAFFIC_TOPICS, KafkaConstants.DWD_CONSUMER_GROUPID));

        // TODO 4.1、业务逻辑处理1：对用户数据中的脏数据进行过滤，并将脏数据再发送到Kafka相应的主题中
        //  离线项目做了这个操作，第一级Flume的拦截器
        // 脏数据：无法解析成JSON的数据
        SingleOutputStreamOperator<JSONObject> filteredKafkaDS = kafkaDS
                .process(new DWDTrafficDirtyDataProcessFunction(DWDTrafficOutputTagConstants.DIRTYDATATAG));

        // TODO 4.2、业务逻辑处理2：对数据中的新老用户的修正（面试的时候不说，因为离线项目没做这个操作）
        SingleOutputStreamOperator<JSONObject> repairedKafkaDS = filteredKafkaDS
                .keyBy(jsonObj -> jsonObj.getJSONObject("common").getString("mid"))
                .process(new DWDTrafficUserRepairKeyedProcessFunction());


        // TODO 5、业务处理逻辑3；对用户行为日志进行分流：
        //  启动日志、页面日志、错误日志、动作日志、曝光日志都发送到对应的Kafka主题中
        SingleOutputStreamOperator<String> distributedProcessDS
                = repairedKafkaDS.process(new DWDTrafficDistributeProcessFunction());

        // 测试打印输出的结果
        // filteredKafkaDS.print("....");
        distributedProcessDS.print(">>>>");
        distributedProcessDS.getSideOutput(DWDTrafficOutputTagConstants.STRATLOGTAG).print("^^^^");
        distributedProcessDS.getSideOutput(DWDTrafficOutputTagConstants.DISPLAYLOGTAG).print("####");
        distributedProcessDS.getSideOutput(DWDTrafficOutputTagConstants.ACTIONLOGTAG).print("%%%%");
        distributedProcessDS.getSideOutput(DWDTrafficOutputTagConstants.ERRORLOGTAG).print("####");

        // TODO 6、将不同类型的日志输出到Kafka不同的主题中
        // 主流 -> 页面数据
        distributedProcessDS.addSink(KafkaUtils.getKafkaProducer(KafkaConstants.DWD_TRAFFIC_PAGE_LOG));
        // 侧输出流 -> 错误日志
        distributedProcessDS.getSideOutput(DWDTrafficOutputTagConstants.ERRORLOGTAG)
                .addSink(KafkaUtils.getKafkaProducer(KafkaConstants.DWD_TRAFFIC_ERROR_LOG));
        // 侧输出流 -> 启动日志
        distributedProcessDS.getSideOutput(DWDTrafficOutputTagConstants.STRATLOGTAG)
                .addSink(KafkaUtils.getKafkaProducer(KafkaConstants.DWD_TRAFFIC_START_LOG));
        // 侧输出流 -> 曝光日志
        distributedProcessDS.getSideOutput(DWDTrafficOutputTagConstants.DISPLAYLOGTAG)
                .addSink(KafkaUtils.getKafkaProducer(KafkaConstants.DWD_TRAFFIC_DISPLAY_LOG));
        // 侧输出流 -> 动作日志
        distributedProcessDS.getSideOutput(DWDTrafficOutputTagConstants.ACTIONLOGTAG)
                .addSink(KafkaUtils.getKafkaProducer(KafkaConstants.DWD_TRAFFIC_DISPLAY_LOG));

        // TODO 7、执行流数据处理
        env.execute();
    }
}
