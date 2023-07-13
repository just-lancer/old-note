package summary;

import org.junit.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class Part2_ExecuteSQL {
    // 数据库连接被用于向数据库服务器发送命令和 SQL语句，并接受数据库服务器返回的结果。其实一个数据库连接就是一个Socket连接
    // 现在已经获得数据库连接了，那么可以开始数据的增删改查

    /**
     * 在 java.sql包中声明了3个接口，分别定义了数据库调用的三种方式：
     * > Statement：用于执行静态SQL语句并返回它所生成结果的对象。
     * > PrepatedStatement：是Statement接口的子接口。SQL语句被预编译并存储在此对象中，可以使用此对象多次高效地执行该语句。
     * > CallableStatement：用于执行SQL存储过程
     * <p>
     * 需要说明的是，Statement接口在进行数据库操作时会出现SQL注入问题，因此，现在基本不使用Statement接口进行数据库操作
     * <p>
     * 对于增删改查四种数据库操作，根据其有无结果集返回将其分为两类
     * 增删改为一类，查询为一类
     */

    // Statement接口进行数据库操作，存在SQL注入问题，不使用，只需要了解即可

    /**
     * PreparedStatement与Statement的对比：
     * > PreparedStatement相关代码比Statement相关代码的可读性和可维护性要好很多，原因在于Statement的SQL语句要拼写SQL语句字符串
     * > PreparedStatement能够最大限度提高代码性能
     * > PreparedStatement能够防止SQL注入问题
     */

    /**
     * PreparedStatement接口：
     * > 能解决Statement接口出现的SQL注入问题
     * > 能够处理Blob数据
     * > 能够进行高效的批量数据插入操作，尽管Statement接口也能实现批量数据插入，但是效率很低
     * <p>
     * 一句话总结PreparedStatement和Statement接口的区别，那就是：
     * PreparedStatement接口的实现类对象表示一个预编译SQL语句对象
     */


    // 针对不同表的增删改的通用方法
    public static int executeUpdate(String sql, Object... obj) {
        Connection connect = null;
        PreparedStatement ps = null;
        try {
            // 1、获取数据库连接
            connect = JDBCUtils.getConnection("jdbc.properties");

            // 2、获取PreparedStatement实现类对象
            ps = connect.prepareStatement(sql);

            // 3、为SQL语句中的占位符赋值
            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1, obj[i]);
            }

            // 4、执行SQL语句
            int i = ps.executeUpdate();

            return i;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 5、关闭资源，处理异常
            JDBCUtils.closeResource(connect, ps);
        }

        return 0;
    }

    // 针对不同表的查询操作，查询操作会有结果集，需要创建对应的Java类对象
    public static <T> ArrayList<T> executeQuery(Class<T> clazz, String sql, Object... obj) {
        Connection connect = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1、获取数据库连接对象
            connect = JDBCUtils.getConnection("jdbc.properties");

            // 2、获取PreparedStatement对象
            ps = connect.prepareStatement(sql);

            // 3、为占位符赋值
            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1, obj[i]);
            }

            // 4、执行SQL语句，获取结果集
            rs = ps.executeQuery();

            // 5、获取结果集的元数据
            ResultSetMetaData metaData = rs.getMetaData();

            // 6、获取结果集的列数
            int columnCount = metaData.getColumnCount();

            // 7、创建相应的javaBean类

            // 8、开始动态为Javabean类的属性赋值
            // 因为结果可能是多行，所以需要使用while循环
            // 先创建承接JavaBean类对象的容器
            ArrayList<T> arrT = new ArrayList<>();
            while (true) {
                if (rs.next()) {// 当结果集下一行还有记录，那么需要创建相应的javabea对象
                    T t = clazz.newInstance();

                    for (int i = 0; i < columnCount; i++) {
                        // 根据列的序号获取列的名称（或别名）
                        String columnLabel = metaData.getColumnLabel(i + 1);
                        // 根据列的名称或别名获取列的值
                        Object object = rs.getObject(columnLabel);

                        // 现在有了列的别名，利用反射为JavaBean类对象赋值
                        Field declaredField = clazz.getDeclaredField(columnLabel);

                        // 设置该属性是可访问的
                        declaredField.setAccessible(true);

                        // 为该属性赋值
                        declaredField.set(t, object);

                    }
                    // for循环执行完一次，那么就会将一行记录的值赋给一个JavaBean类的对象
                    // 将结果添加到arrT中
                    arrT.add(t);
                } else { // 当结果集下一行没有记录了，那么就跳出循环
                    break;
                }

            }
            // 返回结果
            return arrT;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 9、处理异常，关闭资源
            JDBCUtils.closeResource(connect, ps, rs);
        }

        return null;
    }

    // 针对聚合查询结果
    public static <E> E executeAggregateQuery(String sql, Object... objects) {
        Connection connect = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object obj = null;
        try {
            // 1、获取数据库连接
            connect = JDBCUtils.getConnection("jdbc.properties");

            // 2、获取PreparedStatement对象
            ps = connect.prepareStatement(sql);

            // 3、为占位符赋值
            for (int i = 0; i < objects.length; i++) {
                ps.setObject(i + 1, objects[i]);
            }

            // 4、执行SQL，获得结果集
            rs = ps.executeQuery();

            // 5、将聚合记过返回
            if (rs.next()) {
                obj = rs.getObject(1);
            }

            return (E) obj;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 6、关闭资源，处理结果集
            JDBCUtils.closeResource(connect, ps, rs);
        }
        return (E) obj;
    }


    // PreparedStatement接口实现Blob类型数据的CRUD
    // 说明：数据的增删改都是一样的操作步骤，查询是另一种情况
    // 有些问题需要解决：为什么将setBlob()换成setObject()就不行了？
    @Test
    public void blobTest() {
        Connection connect = null;
        PreparedStatement ps = null;
        FileInputStream fis = null;
        try {
            // 1、获取数据库连接
            connect = JDBCUtils.getConnection("jdbc.properties");

            // 2、编写SQL
            String sql = "insert into customers (name, email, birth, photo) values(?, ?, ?, ?)";

            // 3、获取PreparedStatement对象
            ps = connect.prepareStatement(sql);

            // 4、为占位符赋值
            ps.setString(1, "guohaoqi");
            ps.setString(2, "guohaoxuan@163.com");
            ps.setString(3, "2016-07-06");

            fis = new FileInputStream("E:\\IntelliJ_IDEA_workplace\\jdbc\\src\\P80628-154918.jpg.JPG");
            ps.setObject(4, fis);

            // 5、执行SQL
            int i = ps.executeUpdate();
            System.out.println(i);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            // 6、关闭资源
            JDBCUtils.closeResource(connect, ps);
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // 从数据库读取Blob类型数据到本地
    // 存在问题，Object 类型对象不能向下转型为java.sql.Blob类对象
    @Test
    public void blobTest2() {
        Connection connect = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1、获取数据库连接
            connect = JDBCUtils.getConnection("jdbc.properties");

            // 2、编写SQL
            String sql = "select name, id, email, birth, photo from customers where id = ?";

            // 3、获取PreparedStatement实现类对象
            ps = connect.prepareStatement(sql);

            // 4、为占位符赋值
            ps.setInt(1, 22);

            // 5、执行SQL语句，获取结果集
            rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Date birth = rs.getDate("birth");
                Customers customers = new Customers();
                customers.setId(id);
                customers.setEmail(email);
                customers.setName(name);
                customers.setBirth(birth);
                System.out.println(customers);

                Blob photo = rs.getBlob("photo");
                System.out.println(photo.getClass());
                Blob blob = (Blob) photo;

                InputStream binaryStream = blob.getBinaryStream();
                // 现在已经获得了流，那么就将其写入到本地
                byte[] b = new byte[1024];
                int len = 0;

                FileOutputStream fos = new FileOutputStream("guohaoxuan.jpg");
                while ((len = binaryStream.read(b)) != -1) {
                    fos.write(b, 0, len);
                }


                // 处理异常，关闭流
                if (binaryStream != null) {
                    try {
                        binaryStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (fos != null) {
                    try {
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connect, ps, rs);
        }

    }

    // PreparedStatement接口进行批量数据插入
    @Test
    public void insertTest() {
        long begin = System.currentTimeMillis();
        Connection connect = null;
        PreparedStatement ps = null;
        try {
            // 1、获取数据库连接
            connect = JDBCUtils.getConnection("jdbc.properties");
            // 2、将数据库设置为非自动提交
            connect.setAutoCommit(false);
            // 3、编写SQL语句
            String sql = "insert into testtable (id) values(?)";
            ps = connect.prepareStatement(sql);
            for (int i = 1; i <= 200000; i++) {
                ps.setInt(1, i);

                // 攒SQL语句
                ps.addBatch();
                if (i == 200000) {
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }
            connect.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 关闭资源
            JDBCUtils.closeResource(connect, ps);
            long end = System.currentTimeMillis();
            System.out.println(end - begin);
        }
    }


}
