package chapter05.source;

import Utils.Clicks;
import Utils.Event;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Num03_CustomSource {
    // 自定义数据源
    public static void main(String[] args) throws Exception {
        // 获取流式处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        // 在Flink中，为了方便获取数据的相关信息，主要是数据类型，一般将数据都封装到一个POJO类中
        // 将数据作为类的属性定义
        /*
            POJO类的要求：
                -- 类是public的
                -- 属性都是public的
                -- 提供一个空参构造器
         */

        // 获取自定义数据源
        DataStreamSource<Event> customStream = env.addSource(new Clicks());

        customStream.print();

        // 执行数据处理
        env.execute();
    }

}
