package introduction;

public class Scala01_Variable {
    /**
     *  Scala基本语法：变量与数据类型
     *  1、变量
     *      1.1 注释
     *      1.2 变量与常量
     *      1.3 标识符的命名规范
     *  2、Scala输入和输出
     *      2.1 字符串输出
     *      2.2 键盘输入
     *      2.3 文件输入输出
     *  3、Scala数据类型
     *      3.1 Scala数据类型体系
     *      3.2 数据类型转换
     *  4、运算符
     *      4.1 算数运算符
     *      4.2 比较运算符
     *      4.3 逻辑运算符
     *      4.4 赋值运算符
     *      4.5 位运算符
     *      4.6 运算符的本质
     *
     */

    /**
     *  1、变量
     *  1.1 注释：Scala注释与Java注释方式完全一样
     *
     *  1.2 变量与常量
     *      常量：在程序执行过程中，值不会发生变化的变量
     *      Java中常量和变量的定义方式
     *          变量：int a = 10;
     *          常量：final int a = 10;
     *          需要说明的是：java中的final如果加static才会被存放在常量池中，否则作为不可修改的变量存在堆栈中。
     *      Scala中常量和变量的定义方式
     *          变量：var 变量名 [: 数据类型] = 初始值
     *                var a: Int = 10
     *          常量：val 常量名 [: 数据类型] = 初始值
     *                val b: Int = 20
     *          说明：Scala具有自动类型推断，因此可以省略变量或常量的数据类型
     *               Scala的每条语句可以加';'结尾，也可以不加
     *               Scala中能使用常量的地方就不要使用变量，为了兼顾函数式编程
     *
     *      Scala中常量和变量的进一步说明：
     *          > 变量的类型确定后，就不能再修改，说明Scala是强数据类型语言
     *              例如：var a = 10，那么a = "abc"语句会报错
     *          > 变量的声明必须有初始值
     *          > 在声明/定义一个变量时，可以使用var或者val来修饰，var修饰的变量可改变，val修饰的变量不可改变
     *          > var修饰的对象引用可以改变，val修饰的对象引用则不可改变，但对象的状态（属性）却是可以改变的。（比如：自定义对象、数组、集合等等）
     *
     *  1.3 标识符的命名规则与规范
     *      标识符：变量、方法、函数等结构命名是所使用的字符序列成为标识符。换句话说，凡是自己能够起名字的地方都是标识符
     *      Scala标识符的命名规则与Java基本一致，以下是Scala表示符命名规则
     *          > 以字母或者下划线开头，后接字母、数字、下划线
     *          > 可以以操作符开头，如果以操作符开头，那么就只能包含操作符（+ - * / # !等）
     *          > 可以用反引号``包括的任意字符串作为标识符，包括Scala的关键字和保留字
     *
     *      Scala表示符的命名规范与Java相同
     *
     */

    /**
     *  2、Scala输入和输出
     *  2.1 字符串输出
     *      说明：程序的控制台输出，不论输出前是什么数据类型，输出时，都要转换成字符串格式进行输出
     *      对于字符串输出，有以下常见操作
     *      > 字符串拼接：利用'+'将变量与字符串拼接，形成一个新的字符串，这种操作，与Java的字符串拼接完全一样
     *      > 重复字符串拼接：利用'*'可以将字符串重复指定次数
     *      > printf占位符：利用%进行变量占位，形成字符串。这种操作与C语言中相似，%s代表字符串，%d代表整数，%f代表浮点数
     *      > 字符串模板：利用${变量名}获取变量值，与xml中的EL表达式相同
     *          根据字符串模板的前缀不同会有不同的效果
     *          > s"hello ${world}" 常规使用，利用$获取变量world的值
     *          > f"hello ${num}%2.3f" 格式化字符串模板，将num进行格式化，整个num保留2个字符的长度，不足的补0，超过的按实际输出，
     *              小数点后面保留三位，不足的补0，超出的部分进行四舍五入
     *          > raw"hello ${num}%2.3f" 只获取num的数据，并将其他字符按常规字符进行输出，忽视其转移含义
     *      > 三引号表示字符串，保持多行字符串的原格式输出
     *          格式为：""" ... """".stripMargin
     *          其中，...表示字符串内容，stripMargin是字符串的一个方法，用于忽略边界
     *
     *  2.2 键盘输入
     *      Scala从键盘读入数据需要用到scala.io.StdIn这个类中的相应的方法，例如StdIn.readLine(),StdIn.readShort(),StdIn.readDouble()等
     *
     *  2.3 文件输入和输出
     *      文件输入：Scala进行文件读取时，需要用到scala.io.Source类中的方法
     *      文件输出：Scala中没有定义相关的文件输出API，因为Scala可以直接调用Java的API，即调用Java的流进行文件的输出
     *
     */

    /**
     *  3、数据类型
     *  3.1 Scala数据类型体系
     *      Scala数据类型体系
     *      Any
     *      |---- AnyVal
     *          |---- Double    8个字节，双精度浮点数，浮点数的默认数据类型
     *          |---- Float     4个字节，单精度浮点数
     *          |---- Long      8个字节，16位有符号补码整数
     *          |---- int       4个字节，32位有符号补码整数，整数的默认数据类型
     *          |---- Short     2个字节，16位有符号补码整数
     *          |---- Byte      1个字节，表示8位有符号补码整数
     *          |---- Char      2个字节，用''表示单个字符
     *          |---- Boolean   1个字节，只有两个值，true和false
     *          |---- StringOps 是Scala中的字符串类型，是对Java的String的增强，在Scala中，StringOps是数值类型
     *          |---- Unit      对应Java中的Void，用于方法返回值的位置，表示该方法没有返回值。Util是数值类型，只有一个实例化对象，()
     *      |---- AnyRef
     *          |---- Scala collections     Scala的集合类
     *          |---- all java classes      所有的Java类
     *          |---- other Scalaclasses    自定义Scala类
     *              ||---- Null     空引用类型，只有一个对象null，是所有引用类型(AnyRef)的子类
     *
     *      关于数据类型的说明：
     *          > Scala中一切数据都是对象，都是Any的子类
     *          > Scala的数据类型分为两个大类，数值类型(AnyVal)和引用类型(AnyRef)，两种类型都是对象
     *          > 对于数据类型，Scala仍旧支持，低精度数值类型向高精度数值类型自动转换
     *          > Scala中将字符串类型重新包装成StringOps，用作数值类型，是Java String类型的增强
     *          > Unit对应Java中的Void类，是一个数值类型，表示空，只有一个对象，()
     *          > Null是一个引用数据类型，是所有引用数据类型的子类，只有一个实例化对象null
     *          > Nothing，是所有数据类型的子类，主要用在一个函数没有明确返回值时使用
     *
     *  3.2 数据类型转换
     *      3.2.1 自动类型转换，针对数值类型的数据类型转换
     *          Byte --> Short --> Int --> Long --> Float --> Double
     *          对于Char类型，在运算时，最少会向Int进行转换
     *          Byte, Short, Char三者之间运算时，会先转换成Int类型
     *
     *      3.2.2 强制类型转换
     *          对于数值类型的数据而言，进行强制数据类型转换可能会损失精度
     *          不同于自动类型转换，在强制类型转换中，字符串也能够参与转换，这是Scala的StringOps数据类型
     *          强制类型转换需要调用相应的toXxx()方法
     *
     */

    /**
     *  4、运算符
     *  4.1 算术运算符
     *      +、-、*、/、%、+、-、+
     *      加减乘除取余，正号，负号，字符串拼接
     *
     *  4.2 比较运算符
     *      ==  等于
     *          区别于Java，Java中的'=='在基本数据类型中，判断两个数值是否相等，在引用数据类型中，判断两个对象的地址值是否相等
     *          用重写的equals()方法判断两个对象的内容是否相等
     *          而在Scala中，没有基本数据类型，全都是对象，所以'=='的作用就是比较两个对象的内容是否是相等的
     *      !=  不等于
     *      <   小于
     *      <=  小于等于
     *      >   大于
     *      >=  大于等于
     *
     *  4.3 逻辑运算符
     *      &&  逻辑与，左边条件判断为false，右边条件不会执行，返回false
     *      ||  逻辑或，左边条件判断为true，右边条件不会执行，返回true
     *      !   逻辑非
     *
     *  4.4 赋值运算符
     *      =、+=、-=、*=、/=、%=
     *      直接赋值，加等，减等，乘等，除等，求余等
     *      <<=、>>=、&=、|=、^=
     *      左移后赋值，右移后赋值，按位与后赋值，按位或之后赋值，按位异或之后赋值
     *
     *      需要说明的是，Scala中没有++、--操作符
     *
     *  4.5 位运算符
     *      &、|、^、~、<<、>>、>>>
     *      按位与，按位或，按位异或，按位取反，左移，右移，无符号右移
     *
     */
}