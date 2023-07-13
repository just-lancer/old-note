package mysql;

public class chap07_DDL {
    /**
     *  DDL：数据库定义语言，数据库、数据表结构相关操作的语言
     *  常用的有：CREATE、DROP、ALTER
     *
     *  数据库相关的操作：
     *  1、创建数据库：CREATE DATABASE [IF NOT EXISTS] 数据库名 [CHARACTER SET '字符集'];
     *  2、使用或切换数据库：USE 数据库名;
     *  3、查看当前有哪些数据库：SHOW DATABASES;
     *  4、查看当前数据库中有哪些表：SHOW TABLES FROM 数据库名; 或者 SHOW TABLES # 已经在当前数据库中了
     *  5、查看当前正在使用哪个数据库：SHOW DATABASE();
     *  6、查看数据库的创建信息：SHOW CREATE DATABASE 数据库名;
     *  7、修改数据库的信息：只能修改数据库的字符集：ALTER DATABASE 数据库名 CHARACTER SET '字符集';
     *  8、删除数据库：DROP DATABASE 数据库名;
     *
     *
     *  数据表相关的操作：
     *  1、创建数据表：两种方式：
     *      需要说明的是：在创建表的时候设置的字符集，只在当前有效，重启完数据库服务器之后，就无效了
     *      ① CREATE TABLE [IF NOT EXISTS] 表名(
     *            字段1 数据类型1 字段约束1,
     *            字段2 数据类型2 字段约束2,
     *            字段3 数据类型3 字段约束3,
     *             ...
     *            [表级约束]
     *        );
     *
     *      ② 从其他表中创建新表
     *      CREATE TABLE 表名 [(字段1, 字段2, 字段3, ...)]
     *      AS
     *      子查询;
     *      需要注意的是，若新表指明了字段，那么字段的数量必须与子查询获得的字段数量必须相同
     *
     *  2、查看表的结构：有两种方式
     *      ① DESCRIBE/DESC TABLE 表名;
     *      ② SHOW CREATE TABLE 表名;
     *
     *  3、重命名表：两种方式
     *      ① ALTER TABLE 旧表名 RENAME TO 新表名;
     *      ② RENAME TABLE 旧表名 [TO] 新表名;
     *
     *  4、修改表的字段：有四种情况
     *      ① 增加一个字段：ALTER TABLE 表名 ADD [COLUMN] 字段名 数据类型 [FIRST/AFTER （表中已存在的）字段名];
     *      ② 删除一个字段：ALTER TABLE 表名 DROP [COLUMN] 字段名;
     *      ③ 重命名一个字段：ALTER TABLE 表名 CHANGE [COLUMN] 旧字段名 新字段名 数据类型;
     *      ④ 修改一个字段（可以修改字段的数据类型、默认值、位置）：ALTER TABLE 表名 MODIFY [COLUMN] 字段名 数据类型 [DEFAULT] [FRIST/AFTER 字段名]
     *
     *  5、删除表结构：DROP TABLE 表名;
     *  6、删除表数据：两种方法：
     *      ① DELETE FROM 表名 [WHERE 条件];
     *      ② TRUNCATE 表名;
     *      DELETE语句不含WHERE条件时，其效果与TRUNCATE语句相同
     *      二者区别：
     *          > DELETE删除表的数据，在一定情况下可以回滚，而TRUNCATE语句不能回滚
     *          > DELETE的效率没有TRUNCATE高，原因是TRUNCATE在删除表数据时占用的资源较少
     *
     */


}
