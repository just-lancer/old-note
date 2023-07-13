package summary;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Part1_GetConnection {
    // Java程序连接数据库第一步：获取数据库连接
    // 方式一：
    @Test
    public void connectionTest1() throws Exception {
        // 1、创建Driver接口的实现类对象
        // MySQL的驱动实现类是：com.mysql.jdbc.Driver
        // Oracle的驱动实现类是：oracle.jdbc.driver.OracleDriver
        Driver driver = new com.mysql.cj.jdbc.Driver();

        // 2、调用Driver接口中的connect()方法，获取数据库连接对象
        // Connection connect(String url, java.util.Properties info) throws SQLException;

        // url(Uniform Resource Location)，统一资源定位符，用于标识一个被注册的驱动程序，
        // 通过该URL，驱动管理程序能够正确的选择驱动程序，进而与数据库进行连接
        // URL有两部分构成，每一部分用':'隔开
        // 第一部分：通讯协议。在jdbc中，通讯协议由两部分构成，第一部分主协议，总是'jdbc'；
        //          第二部分是子协议，子协议用于标识一个具体数据库驱动程序，连接MySQL数据库就是'mysql'，连接Oracle数据库就是'oracle'
        // 第二部分：子名称。子名称提供了定位具体数据库资源的足够信息，包含数据库服务器的主机名（服务端的IP地址），端口号，数据库名
        // 常用的URL(MySQL5.7)，如：
        //  jdbc:mysql://localhost:3306/test
        //  jdbc:oracle://localhost:1521/test
        // 现在我们需要连接的MySQL8，所使用的URL还需要写入时区信息serverTimezone
        String url = "jdbc:mysql://localhost:3306/atguigudb?serverTimezone=UTC";

        // 设置登录数据库服务器的账号和密码
        Properties info = new Properties();
//        info.put("user", "root");
//        info.put("password", "1234");
        // 注意，在此处填写登录服务器的账号密码时，Properties对象建议使用setProperty()方法，不直接调用put()方法
        // 虽然setProperty()方法中是通过调用put()方法，但setProperty()能使添加的键值对只能使字符串类型
        info.setProperty("user", "root");
        info.setProperty("password", "1234");
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }


    // 方式二：对方式一的迭代
    @Test
    public void connectionTest2() throws Exception {
        // 获取数据库连接对象
        // 1、获取Driver对象：利用反射来实现
        Driver driver = null;
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Object o = clazz.newInstance();
        driver = (Driver) o;


        // 2、填写URL
        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";

        // 3、填写登录服务器的账号和密码
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "1234");
//        info.setProperty("user", "root");
//        info.setProperty("password", "1234");

        // 4、登录服务器，获取连接
        Connection connect = driver.connect(url, info);
        System.out.println(connect);

    }


    // 方式三：利用DriverManager类来替换Driver接口，创建数据库连接
    @Test
    public void connectionTest3() {
        // 利用DriverManager类来创建数据库连接，需要做两件事

        // 第一件事：注册驱动，即告诉DriverManager类，需要连接的是哪个数据库。调用
        // > public static void registerDriver(Driver driver) throws SQLException
        // 该方法将给定的驱动注册到DriverManager中。
        // 一个新加载的驱动类应该调用registerDriver方法来让DriverManager知道它自己。如果驱动程序当前是已注册的，则不采取任何行动。

        // 第二件事：获取数据库连接对象，调用
        // > public static Connection getConnection(String url) throws SQLException
        // 说明：该方法中需要在url中将账号和密码都写进去
        // > public static Connection getConnection(String url, Properties info) throws SQLException
        // > public static Connection getConnection(String url, String user, String password) throws SQLException

        // 开始创建数据库连接对象
        // 1、创建Driver驱动
        Driver driver = null;
        try {
            Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
            Object o = clazz.newInstance();
            driver = (Driver) o;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2、注册驱动
        try {
            DriverManager.registerDriver(driver);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // 3、填写url，user，password等信息：① 算是失败了
        // ① 调用getConnection(String url)方法，填写URL时，将账号和密码都填写进去
//        String url1 = "jdbc.mysql://localhost:3306/test?user=root&password=1234&serverTimezone=UTC";

        // ② 调用getConnection(String url,Properties info)
        String url2 = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "1234");

        // 4、获取数据库连接
        // ①
//        Connection connect1 = null;
//        try {
//            connect1 = DriverManager.getConnection(url1);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        System.out.println(connect1);

        // ②
        try {
            Connection connect = DriverManager.getConnection(url2, info);
            System.out.println(connect);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    // 方式四：方式三的迭代
    @Test
    public void connectionTest4() throws Exception {
        // 由于com.mysql.cj.jdbc.Driver类中含有一个静态代码块
        /**
         * static {
         *         try {
         *             DriverManager.registerDriver(new Driver());
         *         } catch (SQLException var1) {
         *             throw new RuntimeException("Can't register driver!");
         *         }
         *     }
         */
        // 该静态代码块的作用就是帮我们进行驱动的注册，所以代码可以进行优化

        // 1、利用反射进行驱动注册
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2、填写url、账号和密码
        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
        String user = "root";
        String password = "1234";

        // 3、获取数据库连接对象
        Connection connect = DriverManager.getConnection(url, user, password);
        System.out.println(connect);
    }

    // 方式五：最终版：将一些信息写到配置文件中去，如，url、user、password、驱动名：com.mysql.cj.jdbc.Driver
    // 这样做的好处是：解耦，便于移植或便于登录其他数据库服务器
    // 在创建并编写配置文件的时候有一点需要注意：配置文件应该放在src文件夹下，这样在将代码部署到web项目中时会自动将其部署
    //      如果配置文件放在工程下，那么配置文件在部署的时候会出现缺失
    // 配置文件在编写的时候不要为了美观而出现空格，除非这些信息中自带空格字符
    @Test
    public void connectionTest5() {
        // 获取数据库连接
        // 1、编写.perproties配置文件
        // 略

        // 2、读取配置文件信息
        // 通过类加载器获取流，进而读取配置文件
        Properties info = null;
        try {
            InputStream is = Part1_GetConnection.class.getClassLoader().getResourceAsStream("jdbc.properties");
            info = new Properties();
            info.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = info.getProperty("url");
        String user = info.getProperty("user");
        String password = info.getProperty("password");
        String driver = info.getProperty("driver");

        // 3、注册数据库驱动
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 4、获取数据库连接
        try {
            Connection connect = DriverManager.getConnection(url, user, password);
            System.out.println(connect);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


}
