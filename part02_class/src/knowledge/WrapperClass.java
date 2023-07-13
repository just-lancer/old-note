package knowledge;

public class WrapperClass {
    /**
     * 针对8种基本数据类型数据，Java定义了相应的应用类型--包装类，这样Java才是真正意义上的面向对象语言了
     *
     *  byte    -->     Byte
     *  short   -->     Short
     *  int     -->     Integer
     *  long    -->     Long
     *  char    -->     Character
     *  float   -->     Float
     *  double  -->     Double
     *  boolean -->     Boolean
     *
     *  基本数据类型、包装类、字符串之间的相互转换
     *  ① 基本数据类型与包装类之间的转换
     *      > 自动装箱、自动拆箱
     *          Integer in = 10; // 自动拆箱
     *          int i = in; // 自动拆箱
     *
     *      > 手动装箱、手动拆箱
     *          Integer in = new Integer(10); // 调用构造器进行手动装箱
     *          Integer in1 = Integer.valueOf(20); // 调用静态方法valurOf()进行手动装箱
     *
     *          int i = in.intValue(in);// 调用包装类的xxxValue()方法进行手动拆箱
     *
     *  ② 基本数据类型与字符串之间的转换
     *      > 基本数据类型转 --> 字符串
     *          String s = 10 + ""; // 连接符：+
     *          String s1 = String.valueOf(20); // 调用字符串的静态方法valueOf()
     *
     *      > 字符串 --> 基本数据类型
     *          int i = Integer.parseInt("123"); // 调用相应包装类的parseInt(String s)方法，该方法的返回值是int类型
     *
     *  ③ 包装类与字符串之间的转换
     *      > 包装类 --> 字符串
     *          Integer i = 10;
     *          String s = i.toString();// 调用包装类的toString()方法
     *
     *      > 字符串 --> 包装类
     *          Integer in = Integer.valueOf("123");//调用相应包装类的valueOf(String s)方法
     *          Integer in1 = new Integer("123");// 调用相应包装类的构造器
     *
     *
     *  包装类对象的缓存问题
     *      包装类的数据在缓存值的范围内时，会直接从内存中去除对象，超出范围会创建新的对象
     *      Byte、Short、Integer、Long的缓存范围是：-128~127
     *      Character的缓存范围是：0~127
     *      Boolean的缓存范围是：true和false
     */
}
