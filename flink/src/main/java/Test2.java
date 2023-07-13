import Utils.Clicks;
import Utils.Event;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;

/**
 * Author: shaco
 * Date: 2022/7/8
 * Desc:
 */
public class Test2 {
    public static void main(String[] args) {
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                .useBlinkPlanner()
                .build();
        TableEnvironment tableEnv = TableEnvironment.create(settings);

        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 获取数据源
        DataStreamSource<Event> eventStream = env.addSource(new Clicks());

        // 将数据流转换成表

        // 将表转换成数据流，并打印输出

        // 执行流数据处理
    }
}
