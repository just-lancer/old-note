package chapter05.source;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class Num02_KafkaSource {
    // Flink作为Kafka的消费者，从Kafka中读取数据
    public static void main(String[] args) throws Exception {
        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 调用addSource()方法，addSource()方法中需要传入SourceFunction接口的实现类对象
        // 在Flink与Kafka的连接工具里，FlinkKafkaConsumer类就是该接口的实现类
        // FlinkKafkaConsumera类有6个重载构造器，有至少三个参数需要输入，以及一个泛型需要指定
        // 对于泛型，指定的是从Kafka传输到Flink数据的类型，一般使用String，原因是反序列化器一般使用的就是字符串反序列化器
        // 对于第一个参数String topic。定义了从哪些主题中读取数据。可以是一个 topic，也可以是 topic列表，还可以是匹配所有想要读取的 topic 的正则表达式。
        // 当从多个 topic 中读取数据时， Kafka 连接器将会处理所有 topic 的分区，将这些分区的数据放到一条流中去。
        // 对于第二个参数，是指定反序列化器。Kafka 消息被存储为原始的字节数据，所以需要反序列化成 Java 或者 Scala 对象。
        // 上面代码中使用的 SimpleStringSchema，是一个内置的 DeserializationSchema，它只是将字节数组简单地反序列化成字符串。也是最常用的反序列化器
        // 对于第三个参数，是一个properties类型的配置文件，用于连接Kafka集群
        Properties props = new Properties();
        props.put("bootstrap.servers", "hadoop132:9092");
        props.setProperty("group.id", "consumer-group");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("auto.offset.reset", "latest");

        DataStreamSource<String> kafkaSource = env.addSource(new FlinkKafkaConsumer<String>("atguigu", new SimpleStringSchema(), props));

        kafkaSource.print();

        env.execute();
    }
}
