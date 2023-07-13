package chapter05.sink;

import Utils.Event;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;

import java.util.concurrent.TimeUnit;

public class Num11_SinkToFileSystem {
    // Flink数据输出
    // 数据输出到文件系统（文本文件或者Hadoop等文件系统）
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

        /**
         *  传统的writeXxx()方法将Flink数据输出到文本文件中，存在的问题：
         *      -- 并行度低，无法同时对一个文件进行读写
         *      -- 数据写入过程中如果出现故障，难以保障数据的一致性问题
         */

        /**
         *  Flink提供了一个流式文件系统的连接器：StreamingFileSink
         *      StreamingFileSink继承自抽象类RichSinkFunction，并实现了CheckpointedFunction, CheckpointListener接口，保证了数据的一致性问题
         *          数据写入时的基本过程，是将数据写入桶中，每个桶中的数据会按照Sink算子的并行度进行切分，每个并行度或者并行子任务都会有一个输出文件
         *          对于每个桶，默认按时间进行滚动，每隔一个小时创建一个新的桶
         *
         */

        /*
        StreamingFileSink没有公共构造器，创建对象需要调用forRowFormat()方法或者forBulkFormat()方法获取相应的构建器对象，并调用build()方法创建方法
         -- public static <IN> StreamingFileSink.DefaultRowFormatBuilder<IN> forRowFormat(final Path basePath, final Encoder<IN> encoder)
            以行编码的方式为StramingFileSink类创建一个构建器
            参数：basePath，指定bucket（桶）的创建路径
            参数：encoder，数据写入bucket中的编码方式，Flink中为行编码方式提供了三种编码，即实现类，其中，最简单的是SimpleStringEncoder
            泛型：<IN>，表示需要被写入文件的数据的数据类型
            说明：SimpleStringEncoder有一个泛型，表示输出数据的类型

         -- public static <IN> StreamingFileSink.DefaultBulkFormatBuilder<IN> forBulkFormat(final Path basePath, final BulkWriter.Factory<IN> writerFactory)
            以批编码的方式为StramingFileSink类创建一个构建器
            参数：basePath，指定bucket（桶）的创建路径
            参数：writerFactor，数据写入bucket中的编码方式
            泛型：<IN>，表示需要被写入文件的数据的数据类型
         */

        /*
        当StreamingStringSink的构建器创建成功后，可以直接调用build()方法直接创建StreamStringSink的对象，进而进行数据输出。
        也可以在创建对象之前，调用with系列方法对输出进行配置。
        例如，调用withRollingPolicy()方法，设置文件滚动的策略
        详细使用见下
         */

        // 创建SinkFunction的实现类对象
        StreamingFileSink<Event> build = StreamingFileSink.forRowFormat(new Path("flink\\data"), new SimpleStringEncoder<Event>())
                // 设置桶以及桶中文件滚动条件，主要是设置桶文件的滚动方式
                // withRollingPolicy()方法用于设置文件的滚动策略，在其中需要传入接口RollingPolicy的实现类对象，
                // RollingPolicy有四个实现类，最常使用的实现类对象是DefaultRollingPolicy，它的对象也需要通过构建器创建，
                // 在调用build()方法之前，还可以调用with系列方法配置文件滚动参数
                .withRollingPolicy(DefaultRollingPolicy.builder()
                        // 设置文件滚动时间间隔，单位为毫秒
                        .withRolloverInterval(TimeUnit.MINUTES.toMillis(15))
                        // 设置不活跃文件滚动时间间隔，单位为毫秒
                        .withInactivityInterval(TimeUnit.MINUTES.toMillis(5))
                        // 设置文件滚动大小间隔，单位为字节
                        .withMaxPartSize(1024 * 1024 * 1024)
                        .build())
                .build();

        // 将数据写出
        eventDataStreamSource.addSink(build).setParallelism(2);

        // 执行
        env.execute();

    }
}
