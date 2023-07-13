package mysql;

public class chap02_basic_query_statement {
    /**
     *  二、SQL语言学习
     *  1、SQL概述：
     *      > SQL（Structured Query Language，结构化查询语言）是使用关系模型的数据库应用语言， 与数据直
     *              接打交道 ，由 IBM 上世纪70年代开发出来。后由美国国家标准局（ANSI）开始着手制定SQL标准，
     *              先后有 SQL-86 ， SQL-89 ， SQL-92 ， SQL-99 等标准。
     *      > SQL 有两个重要的标准，分别是 SQL92 和 SQL99，它们分别代表了 92 年和 99 年颁布的 SQL 标准，我们今天使用的 SQL 语言依然遵循这些标准
     *      > 不同的数据库生产厂商都支持SQL语句，但都有特有内容
     *
     *  2、SQL语言分类：
     *      > DDL（Data Definition Languages、数据定义语言）：主要的语句关键字包括 CREATE 、 DROP 、 ALTER 、 RENAME 、 TRUNCATE等
     *      > DML（Data Manipulation Language、数据操作语言）：主要的语句关键字包括 INSERT 、 DELETE 、 UPDATE 、 SELECT 等
     *      > DCL（Data Control Language、数据控制语言）：主要的语句关键字包括 GRANT 、 REVOKE 、 COMMIT 、 ROLLBACK 、 SAVEPOINT 等
     *
     *  3、SQL语言编写规则与规范：
     *      规则：
     *          > SQL 可以写在一行或者多行。为了提高可读性，各子句分行写，必要时使用缩进
     *          > 每条命令以 ; 或 \g 或 \G 结束
     *          > 关键字不能被缩写也不能分行
     *          > 关于标点符号
     *              > 必须保证所有的()、单引号、双引号是成对结束的
     *              > 必须使用英文状态下的半角输入方式
     *              > 字符串型和日期时间类型的数据可以使用单引号（' '）表示
     *              > 列的别名，尽量使用双引号（" "），而且不建议省略as
     *
     *      规范：
     *          > MySQL 在 Windows 环境下是大小写不敏感的
     *          > MySQL 在 Linux 环境下是大小写敏感的
     *              > 数据库名、表名、表的别名、变量名是严格区分大小写的
     *              > 关键字、函数名、列名(或字段名)、列的别名(字段的别名) 是忽略大小写的。
     *          推荐统一的书写规范：
     *              > 数据库名、表名、表别名、字段名、字段别名等都小写
     *              > SQL 关键字、函数名、绑定变量等都大写
     *
     *      注释：
     *          > 单行注释：#注释文字(MySQL特有的方式)
     *          > 单行注释：-- 注释文字(--后面必须包含一个空格。)
     *          > 多行注释：/* 注释文字 *\/
     *
     *  4、数据导入命令：通过导入.sql脚本文件，直接建库建表
     *      > 方式一：命令行：source  文件的全路径名
     *      > 方式二：图形化界面管理工具导入数据
     *
     *  5、最基本的SELECT语句：
     *      SELECT 字段1,字段2,...
     *      FROM 表名;
     *
     *      > * 查询所有字段
     *      SELECT *
     *      FROM 表名;
     *
     *  6、列的别名：AS关键字
     *      as:全称：alias(别名),可以省略
     *      列的别名可以使用一对""引起来，也可以使用''，但是不推荐。
     *
     *  7、取出重复行：DISTINCT 关键字
     *      DISTINCT关键字会对后面所有的列名的组合进行去重
     *
     *  8、空值参与运算：NULL 关键字
     *      NULL值参与的任何运算，其结果都是NULL
     *
     *  9、着重号：``
     *      当SQL语句中的标识符与SQL关键字重复的时候，添加着重号能够区分
     *
     *  10、常数查询：
     *      SELECT 常数;
     *
     *  11、过滤数据：WHERE关键字
     *      SELECT 字段1, 字段2, ...
     *      FROM 表名
     *      WHERE 过滤条件;
     *
     *  12、排序：ORDER BY
     *      > 升序：ASC(ascend,v,攀登,登上;上升,升高;追溯.溯源)
     *      > 降序：DESC(descend,v,下降)
     *      > 多级排序：当前级字段出现重复时，会按照后级字段进行排序
     *      说明：可以使用不在SELECT语句中的字段进行排序
     *
     *  13、分页：
     *      > MySQL 8       LIMIT  rows  [OFFSET offset]
     *      说明：MySQL行索引从0开始，列索引从1开始
     *      公式：（当前页数 - 1）*每页条数，煤每页条数
     *
     *  14、分组聚合： GROUP BY   HAVING
     *      > GROUP BY 按组分类并聚合
     *      > HAVING 对聚合结果进行筛选
     *
     *  总结SQL基本查询语句：
     *  语句书写顺序：
     *      > ① SELECT
     *      > ② FROM
     *      > ③ WHERE
     *      > ④ GROUP BY
     *      > ⑤ HAVING
     *      > ⑥ ORDER BY
     *      > ⑦ LIMIT
     *  执行顺序：② --> ③ --> ④ --> ⑤ --> ⑥/⑦
     *  ORDER BY 和 LIMIT两个关键字是冲突的
     */
}
