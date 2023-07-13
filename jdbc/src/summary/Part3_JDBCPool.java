package summary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public class Part3_JDBCPool {
    /**
     * Druid数据库连接池
     */

    @Test
    public void DruidTest1() throws SQLException {
        // 硬编码方式创建数据库连接池，获取数据库连接
        // 创建数据库连接池
        DruidDataSource source = new DruidDataSource();

        // 设置数据库连接池的属性
        Driver driver = new com.mysql.cj.jdbc.Driver();
        source.setUsername("root");
//        source.setDriver(driver);
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        source.setPassword("1234");

        source.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC");

        // 获取数据库连接
        DruidPooledConnection connect = source.getConnection();
        System.out.println(connect);
    }

    @Test
    public void druidTest2() throws Exception {
        // 编写配置文件，获取数据库连接池和数据库练级
        // 编写配置文件

        // 读取配置文件
        FileInputStream fis = new FileInputStream("E:\\IntelliJ_IDEA_workplace\\jdbc\\src\\druid.properties");
//        InputStream fis = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");

        Properties info = new Properties();

        info.load(fis);

        // 利用Druid数据工厂生产Druid数据库连接池
        DruidDataSourceFactory druidDataSourceFactory = new DruidDataSourceFactory();

        DataSource dataSource = druidDataSourceFactory.createDataSource(info);

        Connection connection = dataSource.getConnection();

        System.out.println(connection);
    }

    /**
     * Apache-DBUtils实现数据库CRUD操作
     */
    @Test
    public void dbUtilsTest1() throws Exception {
        // 导包

        // 获取数据库连接对象
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
        Properties properties = new Properties();
        properties.load(is);
        DruidDataSourceFactory druidDataSourceFactory = new DruidDataSourceFactory();
        DataSource dataSource = druidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();

        // 创建QueryRunner类的对象
        QueryRunner queryRunner = new QueryRunner(dataSource);

//        DataSource dataSource = queryRunner.getDataSource();

//        System.out.println(dataSource);

        String sql = "select name, email, birth from customers where id = ?";
        ResultSetHandler<Customers> rsh = new BeanHandler<Customers>(Customers.class);

        Object query = queryRunner.query(sql, rsh, 1);
        System.out.println(query);

    }
}
