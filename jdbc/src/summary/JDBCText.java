package summary;

public class JDBCText {
    /**
     * JDBC学习
     * 1、JDBC概述
     * 1.1 数据持久化：将内存中的数据保存到可掉电存储的设备中，以供后续使用。
     * 一般可掉电存储设备是硬盘，数据的存储形式有：文件，XML数据文件，数据库中数据等
     * <p>
     * 针对数据库中存储的数据，在Java中其存取技术有以下几类：
     * > JDBC，直接访问数据库，进行数据存取
     * > JDO(Java Data Objec)技术
     * > 第三方 R / O 工具，如Hibernate、Mybatis等
     * 需要说明的是：JDO、Hibernate、Mybatis等技术只是更好的封装了JDBC，而JDBC是访问数据库的技术基石
     * <p>
     * 1.2 JDBC介绍：
     * 当前市面上有许多关系型数据库和非关系型数据库，针对关系型数据库，其每个数据库底层开发都不相同，那么Java程序员
     * 如果直接面对具体数据库开发，首先的问题是需要了解不同数据库的底层原理，这样费时费力；其次，针对具体数据库开发的
     * 程序无法顺利的移植到其他数据库上。
     * 为此SUN公司提供了一套专门用于数据库访问的接口（规范）。对于数据库厂商而言，各厂商必须针对这一套接口提供各自的实现，
     * 这些接口的实现即是不同数据库的驱动程序；对于Java程序员而言，只需要针对这一套接口进行开发即可。
     * <p>
     * JDBC即是SUN公司提供的一套接口
     * <p>
     * JDBC(Java Database Connectivity)是一个独立于特定数据库管理系统、通用的SQL数据库存取和操作的公共接口（一组API），
     * 定义了用来访问数据库的标准Java类库，（java.sql,javax.sql）使用这些类库可以以一种标准的方法、方便地访问数据库资源。
     * <p>
     * JDBC为访问不同的数据库提供了一种统一的途径，为开发者屏蔽了一些细节问题。
     * <p>
     * JDBC的目标是使Java程序员使用JDBC可以连接任何提供了JDBC驱动程序的数据库系统，这样就使得程序员无需对特定的数据库系统的特点
     * 有过多的了解，从而大大简化和加快了开发过程
     * <p>
     * JDBC体系：面向应用的API：Java API，抽象接口，供应用程序开发人员使用（连接数据库，执行SQL语句，获得结果）。
     * 面向数据库的API：Java Driver API，供开发商开发数据库驱动程序用
     * <p>
     * 1.3 JDBC访问数据库程序编写步骤：
     * 1、获取相应数据库的驱动程序并建立数据库连接:
     * 说明： Driver接口：是SUN公司提供的数据库访问的标准接口之一，所有的数据库厂商都必须提供其实现类，以供程序开发者能够获取数据库驱动
     * > ① 获取Driver接口的实现类对象：
     * 最基本的形式：调用实现类对象
     * MySQL 5.7：Driver driver = new com.mysql.jdbc.Driver();
     * MySQL 8：Dirver driver = new com.mysql.cj.jdbc.Driver();
     * 利用反射来获取Driver对象：
     * MySQL 5.7：Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
     * MySQL 8：Driver driver = (Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
     * <p>
     * > ② 编写URL：
     * MySQL 5.7：String url = "jdbc:mysql://localhost:3306/test";
     * MySQL 8: String url = "jdbc:mysql://localhost:3306/test?serveTimezone=UTC";
     * MySQL 8中还需要填写时区信息
     * <p>
     * > ③ 填写数据库服务器账户和密码：
     * 利用Properties对象来存储账户和密码的话，需使用setProperty()方法或put()方法：
     * Properties properties = new Properties();
     * properties.setProperty("user", "root");
     * properties.setProperty("password", "1234");
     * <p>
     * > ④ 获取连接：
     * Driver对象调用connect()方法：
     * Connect connect = dricer.connect(url, properties);
     * <p>
     * 利用DriverManager类获取连接：
     * 首先，利用Driver进行数据库驱动注册
     * DriverManager.registerDriver("com.mysql.cj.jdbc.Driver");
     * 随后，获取数据库连接
     * DriverManager.getConnection(String url, String user, String password);
     * <p>
     * 获取数据库连接四大要素：1、url、user、password、数据库驱动名
     * <p>
     * 2、利用PreparedStatement接口实现数据库CRUD：
     * 需要说明的是：PreparedStatement是Statement接口的子接口，Statement接口也能实现CRUD操作，但因为存在SQL注入的原因，现在不使用Statement接口了
     * <p>
     * 利用用PreparedStatement进行CRUD操作有着固定的步骤：
     * > 1、获取数据库连接
     * > 2、编写SQL语句
     * > 3、利用数据库连接对象获取PrepareStatement对象，并为SQL语句的占位符赋值
     * > 4、执行SQL语句
     * > 5、对于查询操作，有结果集需要处理；对于增删改操作，没有结果集需要处理
     * > 6、关闭资源
     * <p>
     * 鉴于固定的步骤以及增删改与查询操作的区别，能够分别写出通用的方法：
     * > ① 获取数据库连接的方法
     * > ② 关闭资源的方法
     * > ③ 针对不同表增删改的通用方法
     * > ④ 针对不同表的查询操作的通用方法
     */

    /**
     * 注意：Java与数据库交互涉及到的相关Java API中的索引都从1开始。
     * <p>
     * 对于查询操作的结果集ResultSet和结果集的元数据ResultSetMetaData
     * > 查询需要调用PreparedStatement 的 executeQuery() 方法，查询结果是一个ResultSet 对象
     * > ResultSet 对象以逻辑表格的形式封装了执行数据库操作的结果集，ResultSet 接口由数据库厂商提供实现
     * > ResultSet 返回的实际上就是一张数据表。有一个指针指向数据表的第一条记录的前面。
     * > ResultSet 对象维护了一个指向当前数据行的游标，初始的时候，游标在第一行之前，可以通过 ResultSet 对象的 next() 方法移动到下一行。
     * 调用 next()方法检测下一行是否有效。若有效，该方法返回 true，且指针下移。相当于Iterator对象的 hasNext() 和 next() 方法的结合体
     * > 当指针指向一行时, 可以通过调用 getXxx(int index) 或 getXxx(String columnName) 获取每一列的值
     * <p>
     */

    /**
     * JDBC第二部分，数据库事物及数据库连接池
     * 一、事物
     *  1、概念：作为一个单元的一组有序的数据库操作
     *  2、事物四大特性：（ACID）
     *      > 原子性（Atomicity）：原子性是指事务是一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生。
     *      > 一致性（Consistency） 事务必须使数据库从一个一致性状态变换到另外一个一致性状态。
     *      > 隔离性（Isolation） 事务的隔离性是指一个事务的执行不能被其他事务干扰，即一个事务内部的操作及使用的
     *                  数据对并发的其他事务是隔离的，并发执行的各个事务之间不能互相干扰。
     *      > 持久性（Durability） 持久性是指一个事务一旦被提交，它对数据库中数据的改变就是永久性的，接下来的其
     *                  他操作和数据库故障不应该对其有任何影响
     *
     *  3、数据库事物提交机制：
     *      DDL语句的执行是自动提交事物
     *      SQL代码调用COMMIT，Java程序调用commit()方法
     *      创建数据库连接或断开数据库连接
     *
     *  4、JDBC处理事务的方法：
     *      为了让多个SQL语句作为一个事物进行，那么必须是在同一个数据库连接下，并且取消数据库的事物自动提交
     *      > 调用数据库连接的setAutoCommit(boolean bool)方法设置数据库不自动提交事物
     *      > 在所有SQL语句执行完成后，手动调用commit()方法，提交事物
     *      > 在遇到异常时，调用rollback()方法，回滚事物
     *
     *  5、数据库并发问题：多个事务并发执行时出现的问题：
     *      > 1、脏读: 对于两个事务 T1, T2, T1 读取了已经被 T2 更新但还没有被提交的字段。之后, 若 T2 回滚, T1读取的
     *          内容就是临时且无效的。
     *      > 2、不可重复读: 对于两个事务T1, T2, T1 读取了一个字段, 然后 T2 更新了该字段。之后, T1再次读取同一个字
     *          段, 值就不同了。
     *      > 3、幻读: 对于两个事务T1, T2, T1 从一个表中读取了一个字段, 然后 T2 在该表中插入了一些新的行。之后, 如
     *          果 T1 再次读取同一个表, 就会多出几行。
     *      不可重复读和幻读的实际上是一样的：都是在一个事物执行了DDL操作，并提交事物后，另一个事物在提交事务前后读取的结果不同，
     *      它们的区别在于，不可重复读是更新操作，幻读是插入数据操作
     *
     *      数据库事物的隔离级性：数据库系统必须具有隔离并发运行各个事务的能力, 使它们不会相互影响, 避免各种并发问题
     *      数据库事物的隔离级别：
     *          > READ UNCOMMITTED：读未提交事物，允许事物读取其他事物未提交的变更。因此脏读、不可重复读、幻读都未曾解决
     *          > READ COMMITTED：读已提交事物，只允许事物读取其他事物已提交的变更，可以避免脏读，不可避免不可重复读、幻读
     *          > REPEATED READ：可重复读，确保事物能重复读取，在该事务进行的过程中，禁止其他事物进行更新操作，因此可以避免脏读和不可重复读
     *              说明：因为幻读是插入操作，而不可重复读是更新操作，所以READ REPEATED无法解决幻读
     *          > SERIALIZABLE：序列化，确保事物的串行执行，在一个事物执行的过程中，禁止其他事物对数据库的DDL操作，能解决所有问题
     *
     *          数据库的隔离级别越高，数据的一致性就越好，但其效率就越低
     *
     *  6、设置数据库的隔离级别
     *      > 查看当前数据库连接的数据库隔离级别：SELECT @@tx_isolation
     *      > 在MySQL中设置数据库的隔离级别：SET TRANSACTION ISOLATION LEVEL READ REPEATED;
     *      > 在MySQL中设置数据库的全局隔离级别：SET GLOBAL TRANSACTION ISOLATION LEVEL READ REPEATED;
     *      > 在Java程序中设置数据库的隔离级别：connection.setTransactionIsolation(int level);
     *          查看数据库的隔离级别：connection.getTransactionIsolation();
     *
     *
     * 二、DAO
     *  1、什么是DAO:
     *      > 1.Data Access Object（数据存取对象）
     *      > 2.位于业务逻辑和持久化数据之间
     *      > 3.实现对持久化数据的访问
     *  2、DAO模式的作用：
     *      > 1.隔离业务逻辑代码和数据访问代码
     *      > 2.隔离不同数据库的实现
     *      > 3.业务逻辑层，数据访问层（Oracle，SQLServer，MySQL）
     *  3、DAO模式的组成部分：
     *      > DAO接口
     *      > DAO实现类
     *      > 实体类
     *      > 数据库连接和关闭工具类
     *
     *
     *
     *
     * 三、数据库连接池：
     *   1、常见的数据库连接池：
     *   JDBC的数据库连接池使用 javax.sql.DataSource来表示，DataSource只是一个接口
     *   该接口通常由服务器(Weblogic, WebSphere, Tomcat)提供实现，也有一些开源组织提供实现
     *   实现后的DataSource的一个实现类对象则代表一个数据库连接池
     *
     *   常见数据库连接池
     *   > C3P0 是一个开源组织提供的一个数据库连接池，速度相对较慢，稳定性还可以。
     *   > DBCP 是Apache提供的数据库连接池。tomcat 服务器自带dbcp数据库连接池。速度相对c3p0较快，但因自身存在BUG，Hibernate3已不再提供支持
     *   > Druid 是阿里提供的数据库连接池，据说是集DBCP 、C3P0 、Proxool 优点于一身的数据库连接池
     *
     *  2、DataSource 通常被称为数据源，它包含连接池和连接池管理两个部分，习惯上也经常把 DataSource 称为连接池
     *     DataSource用来取代DriverManager来获取Connection，获取速度快，同时可以大幅度提高数据库访问速度
     *     当数据库访问结束后，程序还是像以前一样关闭数据库连接：conn.close(); 但conn.close()并没有关闭数据库的物理连接，
     *     它仅仅把数据库连接释放，归还给了数据库连接池
     *
     *
     *  3、Druid数据库连接池
     *      · 简介：Druid是阿里巴巴开源平台上一个数据库连接池实现，它结合了C3P0、DBCP、Proxool等DB池的优点，同时加入了日志监控，
     *          可以很好的监控DB池连接和SQL的执行情况，可以说是针对监控而生的DB连接池，可以说是目前最好的连接池之一。
     *
     *      · 创建数据库连接池：
     *          > 导包：import com.alibaba.druid.pool.DruidDataSourceFactory;
     *          > 编写配置文件：url、driver、user、password这四个是不可缺少的，还可以添加一些数据库连接池的相关配置信息
     *          > 创建流，读取配置文件
     *          > 创建数据库连接池
     *          > 获取数据库连接
     *
     *      · 数据库连接池的配置参数，也就是数据库连接池的相关信息
     *
     * 四、Apache-DBUtils实现CRUD操作
     *  现在从数据库连接池获取了数据库连接，接下来要进行相关的数据库操作
     *  1、简介：commons-dbutils 是 Apache 组织提供的一个开源 JDBC工具类库，它是对JDBC的简单封装，学习成本极低，
     *      并且使用dbutils能极大简化jdbc编码的工作量，同时也不会影响程序的性能。
     *
     *  2、Apache-DBUtils API：
     *      > org.apache.commons.dbutils.QueryRunner：提供数据库操作的一些列重载的update()和query()操作
     *      > org.apache.commons.dbutils.ResultSetHandler：此接口用于处理查询操作得到的结果集。不同的结果集由其不同的子类来实现
     *      > 工具类：org.apache.commons.dbutils.DbUtils
     *
     *  3、DBUtils类：提供如关闭连接x`、装载JDBC驱动程序等常规工作的工具类，里面的所有方法都是静态的。
     *     主要方法如下：
     *     > public static void close(…) throws java.sql.SQLException：将数据库连接放回到数据库连接池
     *       DbUtils类提供了三个重载的关闭方法。这些方法检查所提供的参数是不是NULL，如果不是的话，它们就关闭Connection、Statement和ResultSet。
     *
     *     > public static void closeQuietly(…):这一类方法不仅能在Connection、Statement和ResultSet为NULL情况下避免关闭，
     *       还能隐藏一些在程序中抛出的SQLEeception。
     *
     *     > public static void commitAndClose(Connection conn)throws SQLException：用来提交连接的事务，然后关闭连接
     *
     *     > public static void commitAndCloseQuietly(Connection conn)： 用来提交连接，然后关闭连接，并且在关闭连接时不抛出SQL异常。
     *
     *     > public static void rollback(Connection conn)throws SQLException：允许conn为null，因为方法内部做了判断
     *
     *     > public static void rollbackAndClose(Connection conn)throws SQLException
     *
     *     > public static boolean loadDriver(java.lang.String driverClassName)：这一方装载并注册JDBC驱动程序，如果成功就返回true。
     *       使用该方法，你不需要捕捉这个异常ClassNotFoundException。
     *
     *   4、QueryRunner类：该类简单化了SQL查询，它与ResultSetHandler组合在一起使用可以完成大部分的数据库操作，能够大大减少编码量。
     *      · 构造器：
     *          > 默认的无参构造器
     *          > 需要一个 javax.sql.DataSource 来作参数的构造器
     *      · 主要方法：
     *          更新：
     *          > public int update(Connection conn, String sql, Object... params) throws SQLException:用来执行一个更新（插入、更新或删除）操作。
     *
     *          插入：
     *          > public T insert(Connection conn,String sql,ResultSetHandler rsh, Object... params) throws SQLException：只支持INSERT语句，
     *           其中 rsh - The handler used to create the result object from the ResultSet of auto-generated keys.
     *           返回值: An object generated by the handler.即自动生成的键值
     *
     *          批处理：
     *          > public int[] batch(Connection conn,String sql,Object[][] params)throws SQLException：INSERT, UPDATE, or DELETE语句
     *          > public T insertBatch(Connection conn,String sql,ResultSetHandler rsh,Object[][] params)throws SQLException：只支持INSERT语句
     *
     *          查询：
     *          > public Object query(Connection conn, String sql, ResultSetHandler rsh,Object... params) throws SQLException：
     *            执行一个查询操作，在这个查询中，对象数组中的每个元素值被用来作为查询语句的置换参数。
     *            该方法会自行处理 PreparedStatement 和 ResultSet 的创建和关闭
     *
     *   5、ResultSetHandler接口及实现类
     *      > 该接口用于处理 java.sql.ResultSet，将数据按要求转换为另一种形式。
     *      > ResultSetHandler 接口提供了一个单独的方法：Object handle (java.sql.ResultSet .rs)。
     *      > 主要实现类：
     *          > ArrayHandler：把结果集中的第一行数据转成对象数组。
     *          > ArrayListHandler：把结果集中的每一行数据都转成一个数组，再存放到List中。
     *          > BeanHandler：将结果集中的第一行数据封装到一个对应的JavaBean实例中。
     *          > BeanListHandler：将结果集中的每一行数据都封装到一个对应的JavaBean实例中，存放到List里。
     *          > ColumnListHandler：将结果集中某一列的数据存放到List中。
     *          > KeyedHandler(name)：将结果集中的每一行数据都封装到一个Map里，再把这些map再存到一个map里，其key为指定的key。
     *          > MapHandler：将结果集中的第一行数据封装到一个Map里，key是列名，value就是对应的值。
     *          > MapListHandler：将结果集中的每一行数据都封装到一个Map里，然后再存放到List
     *          > ScalarHandler：查询单个值对象
     *
     */


}
