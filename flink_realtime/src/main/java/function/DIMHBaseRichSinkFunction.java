package function;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONObject;
import common.PhoenixConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import utils.JDBCUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Author: shaco
 * Date: 2022/6/29
 * Desc: DIM层sinkFunction，用于将处理后的业务数据写入到HBase中
 */
public class DIMHBaseRichSinkFunction extends RichSinkFunction<JSONObject> {
    // 申明JDBC连接池
    DruidDataSource druidDataSource;

    @Override
    public void open(Configuration parameters) {
        // 获取连接池对象
        druidDataSource = JDBCUtils.druidDataSource;
    }

    @Override
    public void invoke(JSONObject value, Context context) {
        // HBase命名空间
        String namespace = PhoenixConstants.NAMESPACE;
        // HBase表名
        String sink_table = value.getString("sink_table");
        // 字段以及值
        JSONObject data = value.getJSONObject("data");

        // 将data中的表名删掉
        data.remove(sink_table);

        // 拼接SQL语句
        StringBuilder sqlStatement = new StringBuilder(" upsert into " + PhoenixConstants.NAMESPACE + "." + sink_table +
                " ( " + StringUtils.join(data.keySet(), ", ") + " ) " +
                " values " + " ('" + StringUtils.join(data.values(), "','") + "')");

        System.out.println("=====Phoenix表数据插入SQL语句：" + sqlStatement);

        DruidPooledConnection connection = null;
        PreparedStatement ps = null;
        try {
            // 获取连接，执行sql
            connection = druidDataSource.getConnection();

            ps = connection.prepareStatement(sqlStatement.toString());

            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 关闭资源
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
