package utils;

import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.jdbc.JdbcStatementBuilder;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Author: shaco
 * Date: 2022/7/23
 * Desc: ClickHouse工具类
 */
public class ClickHouseUtils {
    // 该方法用户获取一个向ClickHouse写入数据的SinkFunction
    // 第一个泛型标识该方法是一个泛型方法
    // 第二个方法标识该方法的返回值类型的泛型
    public static <T> SinkFunction<T> getClickHouseSinkFunction(String sql) {
        return JdbcSink.sink(
                sql,
                new JdbcStatementBuilder<T>() {
                    @Override
                    public void accept(PreparedStatement ps, T obj) throws SQLException {
                        // 通过反射获取所有属性
                        Field[] fields = obj.getClass().getFields();

                        for (int i = 0; i < fields.length; i++) {
                            // 设置属性的访问权限
                            fields[i].setAccessible(true);
                            // 获取属性值，并给占位符赋值
                            try {
                                Object o = fields[i].get(obj);
                                ps.setObject(i + 1, o);
                            } catch (IllegalAccessException e) {
                                System.out.println("ClickHouse 占位符赋值异常");
                                e.printStackTrace();
                            }
                        }

                    }
                },
                JdbcExecutionOptions.builder()
                        .withBatchSize(10)
                        .withBatchIntervalMs(2000).build(),
                new JdbcConnectionOptions
                        .JdbcConnectionOptionsBuilder()
                        .withDriverName("ru.yandex.clickhouse.ClickHouseDriver")
                        .withUrl("jdbc:clickhouse://hadoop132:8123")
                        .build()
        );
    }
}
