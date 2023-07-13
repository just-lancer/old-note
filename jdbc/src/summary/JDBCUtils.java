package summary;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class JDBCUtils {

    // JDBC工具类，封装了数据库连接方法和相关资源释放的方法

    // 数据库连接方法
    public static Connection getConnection(String iniName) {
        InputStream is = null;
        try {
            // 1、读取配置文件
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(iniName);

            // 2、利用Properties获取配置文件的键值
            Properties info = new Properties();

            info.load(is);

            String url = info.getProperty("url");
            String driver = info.getProperty("driver");
            String user = info.getProperty("user");
            String password = info.getProperty("password");

            // 3、注册驱动
            Class.forName(driver);

            // 4、利用DriverManager获取数据库连接
            Connection connect = DriverManager.getConnection(url, user, password);

            // 5、返回数据库连接
            return connect;
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            // 6、异常处理，关闭流资源
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


    // 关闭资源的方法
    // 需要两个重载的方法，原因在于增删改没有结果集资源，而查询操作需要关闭结果集资源
    public static void closeResource(Connection connect, PreparedStatement ps) {
        if (connect != null) {
            try {
                connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResource(Connection connect, PreparedStatement ps, ResultSet rs) {
        if (connect != null) {
            try {
                connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
