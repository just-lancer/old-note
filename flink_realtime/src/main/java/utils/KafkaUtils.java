package utils;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.flink.streaming.connectors.kafka.KafkaSerializationSchema;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Author: shaco
 * Date: 2022/6/28
 * Desc: Kafka工具类
 */
public class KafkaUtils {
    // 获取Flink Kafka消费者对象
    public static FlinkKafkaConsumer<String> getKafkaConsumer(String topics, String consumer_groupID) {
        // 参数1：主题
        List<String> topic = Arrays.asList(topics.split(","));
        // 参数2：反序列化器
        // 由于序列化器，SimpleStringSchema对于Null数据无法处理，所以无法处理Kafka中数据为null的情况，会直接报错
        KafkaDeserializationSchema<String> kafkaDeserializationSchema = new KafkaDeserializationSchema<String>() {
            @Override
            public TypeInformation<String> getProducedType() {
                return TypeInformation.of(String.class);
            }

            @Override
            public boolean isEndOfStream(String s) {
                return false;
            }

            @Override
            public String deserialize(ConsumerRecord<byte[], byte[]> consumerRecord) throws Exception {
                // 如果consumerRecord本身不为null，并且consumerRecord中的值不为null，那么就反序列化
                // 否则，直接返回为null，这也算是对null的处理
                if (consumerRecord != null && consumerRecord.value() != null) {
                    return new String(consumerRecord.value());
                }
                return null;
            }
        };
        // 参数3：Kafka相关配置，至少需要连接地址和消费者组两项
        Properties kafkaProp = new Properties();
        kafkaProp.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop132:9092,hadoop133:9092");
        kafkaProp.setProperty(ConsumerConfig.GROUP_ID_CONFIG, consumer_groupID);

        return new FlinkKafkaConsumer<String>(topic, kafkaDeserializationSchema, kafkaProp);

    }

    // 获取Kafka生产者对象，将数据写入到Kafka中
    public static FlinkKafkaProducer<String> getKafkaProducer(String topicID) {
        Properties prop = new Properties();
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop132:9092,hadoop133:9092");
        prop.setProperty(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 15 * 60 * 1000 + "");

        return new FlinkKafkaProducer<String>(
                topicID,
                new KafkaSerializationSchema<String>() {
                    @Override
                    public ProducerRecord<byte[], byte[]> serialize(String s, Long aLong) {
                        return new ProducerRecord<byte[], byte[]>(topicID, s.getBytes());
                    }
                },
                prop,
                FlinkKafkaProducer.Semantic.EXACTLY_ONCE
        );
    }

    // Kafka连接器：用于从Kafka中读取数据
    public static String getKafkaConnector(String topic, String groupid) {
        return "WITH (\n" +
                "  'connector' = 'kafka',\n" +
                "  'topic' = '" + topic + "',\n" +
                "  'properties.bootstrap.servers' = 'hadoop132:9092',\n" +
                "  'properties.group.id' = '" + groupid + "',\n" +
                "  'scan.startup.mode' = 'earliest-offset',\n" + // earliest-offset，group-offsets
                "  'format' = 'json'\n" +
                ")\n";
    }

    // upsertKafka连接器：用于向Kafka中写入数据
    public static String getUpsertKafkaConnector(String topic) {
        return "WITH (\n" +
                "  'connector' = 'upsert-kafka',\n" +
                "  'topic' = '" + topic + "',\n" +
                "  'properties.bootstrap.servers' = 'hadoop132:9092',\n" +
                "  'key.format' = 'json',\n" +
                "  'value.format' = 'json'" +
                ")";
    }
}
