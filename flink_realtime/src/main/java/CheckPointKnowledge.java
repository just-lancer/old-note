import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.runtime.state.storage.FileSystemCheckpointStorage;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.concurrent.TimeUnit;

/**
 * Author: shaco
 * Date: 2022/7/7
 * Desc: flink检查点设置，各个方法的作用
 */
public class CheckPointKnowledge {
    public static void main(String[] args) {
        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 检查点配置
        // 1、开启检查点，每隔指定毫秒数进行一次检查点保存
        // 实际的操作是，每隔10 * 60 * 1000毫秒，flink向数据中插入一条barrier
        env.enableCheckpointing(10 * 60 * 1000L);

        // 获取检查点配置对象
        CheckpointConfig ckConf = env.getCheckpointConfig();

        // 2、设置检查点存储路径，默认的存储路径不是磁盘，而是JVM的堆内存空间
        ckConf.setCheckpointStorage(new FileSystemCheckpointStorage("hdfs://hadoop132:9820/flink/ch"));

        // 3、设置检查点的超时时间，单位：毫秒。超时时间表示：当超出指定时间，检查点保存操作还未完成，那么丢弃这次的检查点保存
        ckConf.setCheckpointTimeout(60 * 1000L);

        // 4、设置检查点一致性级别，默认级别是EXACTLY_ONCE
        ckConf.setCheckpointingMode(CheckpointingMode.AT_LEAST_ONCE);

        // 5、设置两个检查点之间最小的时间间隔，单位：毫秒。
        // 这个参数表示，在上一个检查点完全结束后，再间隔指定时间后，开始进行下一个检查点保存
        ckConf.setMinPauseBetweenCheckpoints(50 * 1000L);

        // 6、设置检查点并发个数，该参数表示同一时间最多只能有指定个数的检查点同时进行保存
        ckConf.setMaxConcurrentCheckpoints(2);

        // 7、设置开启不对齐barrier的检查点操作
        // 不对齐barrier的检查点保存操作有要求
        //  1、检查点模式必须是EXACTLY_ONCE
        //  2、检查点并发数量必须是1
        ckConf.enableUnalignedCheckpoints();

        // 8、设置是否开启检查点的外部持久化，所需要传入的参数类型是：ExternalizedCheckpointCleanup，表示检查点文件的清除策略
        //  ExternalizedCheckpointCleanup是一个枚举类，有两个属性，DELETE_ON_CANCELLATION：表示在job失败时，直接删除外部检查点
        //  RETAIN_ON_CANCELLATION：表示在job失败时，不删除外部检查点。默认值是DELETE_ON_CANCELLATION
        // 默认情况下，在job失败或成功或挂起时，flink是不会自动清除检查点文件及其元数据信息，如果需要删除检查点信息，需要手动进行清除
        // 通过该参数配置，可以进行自动的数据清除
        ckConf.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

        // 9、设置是否允许检查点操作失败，以及其失败个数。默认情况下，不允许失败，即0
        ckConf.setTolerableCheckpointFailureNumber(0);

        // 10、设置重启策略配置。配置指定在重新启动的情况下，执行图将使用哪种重新启动策略。
        env.setRestartStrategy(RestartStrategies.failureRateRestart(10, Time.of(1L, TimeUnit.DAYS), Time.of(3L, TimeUnit.MINUTES)));

        
    }
}
