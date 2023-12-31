package mysql;

public class chap03_operational_character {
    /**
     *  三、运算符
     *  注意：NULL参与含有运算符的运算，其结果都是NULL，<=>运算符除外
     *        运算符两侧数据的数据类型不同时，SQL会自动进行类型转换，当转换失败会按0处理
     *        具体情况具体分析
     *
     *  1、算术运算符
     *      加    >  +        注意：当运算符两侧数据类型不同时，运算开始会试着将非数值数据转换成数值类型数据，如果失败那么非数值类型数据按0处理，
     *      减    >  -
     *      乘    >  *
     *      除    >  / DIV     注意：SQL的除法，不论是INT还是DOUBLE类型，结果都是DOUBLE
     *                              当除数是0时，运算结果是NULL
     *      取模  >  % MOD     注意：被模数 % 模数     当被模数和模数的符号不一样时，其结果的符号与被模数相同
     *
     *  2、比较运算符
     *      注意：
     *          > SQL语言中，当运算结果为真时，返回结果为1；当运算结果为假时，返回结果为0；
     *              当NULL参与运算时，返回结果都是NULL，<=>运算符除外
     *          > 当两侧的数据类型不同时，会进行数据类型进行转换，转换失败则按0处理
     *
     *      等于        >  =
     *      安全等于    >  <=>      为NULL的运算而生
     *      不等于      >  !=  <>
     *      小于        >  <
     *      小于等于    >  <=
     *      大于        >  >
     *      大于等于    >  >=
     *
     *      IS NULL         判断值、字符串或者表达式是否为空
     *      IS NOT NULL     判断值、字符串或者表达式是否不为空
     *      LEAST()         返回多个值中的最小值
     *      GREATEST()      返回多个值中的最大值
     *      BETWEEN LOWER  AND  UPPER   判断一个值是否在LOWER和UPPER中，左闭右闭
     *      ISNULL          判断一个值、字符串或者表达式是否为NULL
     *      IN              判断一个值是否在一个列表中的任何一个值
     *      NOT IN          判断一个值是否不在一个列表中的任何一个值
     *      LIKE            模糊查询
     *          > 通配符1：%    代表0个或者任意字符
     *          > 通配符2：_    代表1个字符
     *          说明：如果就是需要判断字符串中是否包含%或_，那么可以使用转义字符 \ 或者使用ESCAPE 字符 告诉编译器哪个字符是转移字符
     *
     *
     *      3、逻辑运算符
     *          > 逻辑与    AND 或  &&
     *          > 逻辑或    OR  或  ||
     *          > 逻辑非    NOT 或  ！
     *          > 逻辑异或  XOR
     *          说明：逻辑与的优先级高于逻辑或的优先级
     *
     *      4、位运算符：了解
     *          > 按位与        &
     *          > 按位或        |
     *          > 按位取反      ~
     *          > 按位异或      ^
     *          > 按位右移      >>
     *          > 按位左移      <<
     *
     *      5、正则表达式，了解
     *
     */
}
