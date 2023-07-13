package mysql;

public class chap04_multi_table_select {
    /**
     *  四、多表查询
     *      > 1、笛卡尔积（交叉连接）：CROSS JOIN
     *      > 2、内连接：INNER JOIN
     *      > 3、外连接：OUTER JOIN
     *          外连接默认是左外连接
     *          右外连接：RIGHT OUTER JOIN
     *      > 4、全连接：MySQL不支持全连接语句，但可以使用UNION实现全连接
     *          SQL标准支持全连接：FULL JOIN 或者 FULL OUTER JOIN
     *      > 5、自连接
     *
     *      > 6、表的联结操作：UNION 或者 UNION ALL
     *          > UNION ：联结两个表的结果，并去重
     *          > UNION ALL ：联结两个表的结果，不去重
     *
     *      > 对于数据库中表记录的查询和变更，只要涉及多个表，都需要在列名前加表的别名（或表名）进行限定。
     *      > 连接条件：用关键字ON或者WHERE语句
     */
}
