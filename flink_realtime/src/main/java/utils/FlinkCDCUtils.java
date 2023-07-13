package utils;

import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;

/**
 * Author: shaco
 * Date: 2022/6/28
 * Desc: Flink CDC工具类，用于获得数据源Source
 */
public class FlinkCDCUtils {

    public static MySqlSource<String> getMySqlSource() {
        return MySqlSource.<String>builder()
                .hostname("hadoop132")
                .port(3306)
                // 不知道为什么不能用
                // .scanNewlyAddedTableEnabled(true) // eanbel scan the newly added tables fature
                .databaseList("gmall_realtime_config") // set captured database
                .tableList("gmall_realtime_config.table_process") // set captured tables [product, user, address]
                .username("root")
                .password("1234")
                .startupOptions(StartupOptions.initial())
                .deserializer(new JsonDebeziumDeserializationSchema()) // converts SourceRecord to JSON String
                .build();


        // TODO 6. FlinkCDC 读取配置流并广播流
        // 6.1 FlinkCDC 读取配置表信息
//        MySqlSource<String> mySqlSource = MySqlSource.<String>builder()
//                .hostname("hadoop202")
//                .port(3306)
//                .databaseList("gmall2022_config") // set captured database
//                .tableList("gmall2022_config.table_process") // set captured table
//                .username("root")
//                .password("123456")
//                .deserializer(new JsonDebeziumDeserializationSchema())
//                .startupOptions(StartupOptions.initial())
//                .build();

    }
}
