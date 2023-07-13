package utils;

import com.alibaba.druid.pool.DruidDataSource;
import common.PhoenixConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author: shaco
 * Date: 2022/6/29
 * Desc: Phoenix工具类，用于操作HBase中的表
 */
public class PhoenixUtils {
    public static void createHBaseTable(String sourceTable, String sinkTable, String sinkColumns, String sinkPK,
                                        String sinkExtend, DruidDataSource druidDataSource) {

        // Bean对象的处理
        if (sinkPK == null) {
            // 如果主键为null，默认使用id为主键
            sinkPK = "id";
        }
        if (sinkExtend == null) {
            sinkExtend = "";
        }
        // 拼接SQL语句
        StringBuilder sqlStatement = new StringBuilder("create table if not exists ");

        // 命名空间和表名
        sqlStatement.append(PhoenixConstants.NAMESPACE + "." + sinkTable + " ( ");

        // 字段及数据类型
        String[] columns = sinkColumns.split(",");
        String[] pks = sinkPK.split(",");
        System.out.println("该方法被调用");
        AbstractList<String> columnsArr = new ArrayList<String>(Arrays.asList(columns));
        // 下面语句有错误
        //List<String> columnsArr = Arrays.asList(columns);

        System.out.println("^^^^^^^^^^^^^^^^pks：" + Arrays.toString(pks));
        System.out.println("%%%%%%%%%%%%%columns：" + columnsArr);
        // 在columns中删除主键字段，相当于去重
        for (String pk : pks) {
            System.out.println("该循环被调用");
            columnsArr.remove(pk);
        }

        System.out.println("循环调用结束，进行下一步");

        // 如果是单列主键，那么有单列主键的建表语句
        if (pks.length == 1) {
            sqlStatement.append(sinkPK + " varchar " + " primary key, ");

            for (int i = 0; i < columnsArr.size(); i++) {
                if (i == columnsArr.size() - 1) {
                    sqlStatement.append(columnsArr.get(i) + " varchar ");
                } else {
                    sqlStatement.append(columnsArr.get(i) + " varchar, ");
                }
            }
        }
        // 如果是组合主键，那么有组合主键的建表语句
        else {
            for (String pk : pks) {
                sqlStatement.append(pk + " varchar, ");
            }
            for (int i = 0; i < columnsArr.size(); i++) {
                if (i == columnsArr.size() - 1) {
                    sqlStatement.append(columnsArr.get(i) + " varchar ");
                } else {
                    sqlStatement.append(columnsArr.get(i) + " varchar, ");
                }
            }
            sqlStatement.append(" constraint my_pk primary key (" + sinkPK + ")");
        }

        sqlStatement.append(" ) " + sinkExtend);
        System.out.println("^^^^^^Phoenix维度表建表SQL语句：" + sqlStatement);

        // 获取Phoenix连接，并执行SQL语句
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            // 获取Phoenix连接
            connection = druidDataSource.getConnection();
            // 准备SQL语句和PreparedStatement对象
            ps = connection.prepareStatement(sqlStatement.toString());
            // 执行SQL语句
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 关闭连接
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
