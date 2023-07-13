package mysql;

public class chap08_DML {
    /**
     *  DML语句：数据操作语句
     *  常用的有：INSERT INTO、DELETE、UPDATE、SELECT
     *
     *  1、向表中新增数据：INSERT INTO 表名 ([字段列表]) VALUES (值列表);
     *  说明；字段列表可以不写，此时 值列表需要写完整才能向表中添加完整的数据；
     *        可以想表中的指定字段添加数据，做到字段列表与值列表相对应就可以了
     *
     *  2、删除表中的记录：DELETE FROM 表名 [WHERE 条件];
     *  3、更新表的数据：UPDATE 表名 SET 字段1 = 值1, 字段2 = 值2, ... WHERE 条件;
     *      可以同时更新多条记录
     *  4、查询表中的数据：
     *      SELECT 需要展示的字段
     *      FROM 表名（可能是多表查询）
     *      WHERE 条件
     *      GROUP BY 分组条件
     *      LIMIT  OFFSET
     *      ORDER BY 字段名
     *      说明：ORDER BY 与 LIMIT rows [OFFSET offset] 是互斥的
     *
     *  5、计算列：在创建表时可以定义计算列，计算列定义如下：
     *      字段名  数据类型  ALWAYS 计算表达式 VIRTURAL;
     *
     */
}
