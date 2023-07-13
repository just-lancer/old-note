package utils;

/**
 * Author: shaco
 * Date: 2022/7/20
 * Desc: MySQL相关的工具类
 */
public class MySQLUtils {
    // MySQL连接器
    public static String getMySQLConnector(String database, String table) {
        return "WITH (\n" +
                "  'connector' = 'jdbc',\n" +
                "  'username' = 'root',\n" +
                "  'password' = '1234',\n" +
                "  'driver' = 'com.mysql.cj.jdbc.Driver',\n" +
                "  'url' = 'jdbc:mysql://hadoop132:3306/" + database + "',\n" +
                "  'table-name' = '" + table + "'\n" +
                ") ";
    }
}
