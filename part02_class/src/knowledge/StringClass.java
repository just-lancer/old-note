package knowledge;

import org.junit.Test;

public class StringClass {
    /*
    String类的使用
    String:字符串，使用一对""引起来表示。
    1.String声明为final的，不可被继承
    2.String实现了Serializable接口：表示字符串是支持序列化的。
            实现了Comparable接口：表示String可以比较大小
    3.String内部定义了final char[] value用于存储字符串数据
    4.String:代表不可变的字符序列。简称：不可变性。
        体现：1.当对字符串重新赋值时，需要重写指定内存区域赋值，不能使用原有的value进行赋值。
             2. 当对现有的字符串进行连接操作时，也需要重新指定内存区域赋值，不能使用原有的value进行赋值。
             3. 当调用String的replace()方法修改指定字符或字符串时，也需要重新指定内存区域赋值，不能使用原有的value进行赋值。
    5.通过字面量的方式（区别于new）给一个字符串赋值，此时的字符串值声明在字符串常量池中。
    6.字符串常量池中是不会存储相同内容的字符串的。

    // 字符串的一段源码
        public final class String implements java.io.Serializable, Comparable<String>, CharSequence{
            // the value is used for character storage
            private final char value[];

            // Cache the hash code for the string
            private int hash;
    */

    @Test
    public void test1() {
        //通过字面量定义的方式：此时的str1和str2的数据javaEE声明在方法区中的字符串常量池中。
        String str1 = "JavaEE1";
        String str2 = "JavaEE2";

        // 构造器的方式：此时的str3和str4保存的地址值，是数据在堆空间中开辟空间以后对应的地址值
        String str3 = new String("JavaEE3");
        String str4 = new String("JavaEE4");

        // 通过拼接字符串的方式：此时的str5，和str6保存的地址值是数据在堆空间中开辟空间以后对应的地址值
        String str5 = str1 + "hadoop";
        String str6 = str1 + str2;
    }

    /*
    字符串相关结论：
    方法区(Method Area)：用于存储类结构信息的地方，包括常量池、静态变量、构造函数等。
    1.字符串常量与字符串常量的拼接结果在常量池。且常量池中不会存在相同内容的常量（包括字符串常量和数值常量）。
    2.只要其中有一个字符串是变量，结果就在堆中。
    3.如果拼接的结果调用intern()方法，返回值就在常量池中
     */
    @Test
    public void test2() {
        String s1 = "javaEEhadoop";
        String s2 = "javaEE";
        String s3 = s2 + "hadoop";
        System.out.println(s1 == s3);//false

        final String s4 = "javaEE";//s4:常量
        String s5 = s4 + "hadoop";
        System.out.println(s1 == s5);//true

    }

    /*
    String 与基本数据类型、包装类之间的转换。

    String --> 基本数据类型：调用相应包装类的静态方法：valueOf(String str)
    String --> 包装类：调用相应包装类的静态方法：parseXxx(String str)
    基本数据类型、包装类 --> String:调用String的静态方法：valueOf(xxx)
     */

    /*
    String 与 字符数组char[]之间的转换

    String --> char[]:全部转换：调用String的public char[] toCharArray()
                        部分转换：有方法可以调用，但建议自己手写
    char[] --> String:全部转换：调用String的构造器String(char[])
                        部分转换：调用String的构造器String(char[], int offset, int length)
     */


    /*
    String与字节数组byte[]之间的转换
    编码：String --> byte[]:调用String的public byte[] getBytes()方法
                                       public byte[] getBytes(String setcharName) // 设置指定的编码字符集
    解码：byte[] --> String:调用String的构造器

    编码：字符串 -->字节  (看得懂 --->看不懂的二进制数据)
    解码：编码的逆过程，字节 --> 字符串 （看不懂的二进制数据 ---> 看得懂）

    说明：解码时，要求解码使用的字符集必须与编码时使用的字符集一致，否则会出现乱码。
     */

    @Test
    public void test3() {
        String s = "abc";
        byte[] bytes = s.getBytes();
        System.out.println(bytes);
    }

}