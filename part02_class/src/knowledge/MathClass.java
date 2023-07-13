package knowledge;

public class MathClass {
    /**
     * java.lang.Math类包含了用于执行基本数学运算的方法，类中所有方法均为静态方法，属性均为全局变量（public static final）
     *
     * 属性：
     *  public static final double PI:圆周率
     *  public static final double E:自然底数
     *
     * 方法：
     *  public static double abs(double a):返回a的绝对值
     *  public static double ceil(double a):返回大于等于a的最小整数，即向下取整
     *  public static double floor(double a):返回小于a的最大整数，即向上取整
     *  public static double round(double a):返回最接近参数的long，相当于四舍五入
     *  public static double pow(double a, double b):返回a的b次幂
     *  public static double sqrt(double a):返回a的平方根
     *  public static double max(double a, double b):返回a,b中的最大值
     *  public static double min(double a, double b):返回a,b中的最小值
     *  public static double random():返回[0,1)的随机值，取值范围呈现均匀分布
     *
     */

    /**
     * java.util.Random 用于产生随机数
     *  构造器：
     *      public Random():创建一个随机数生成器，默认以当前系统时间作为种子
     *      public Random(long seed):创建一个随机数生成器，使用传入参数seed作为种子
     *
     *  方法：
     *      int nextInt()：返回一个整数范围内的int型数据，数值满足均匀分布
     *      int nextInt(int n)：返回一个int型随机数，范围为[0,n)之间
     *      void nextBytes(byte[] bytes):生成随机字节并将其置于用户提供的byte数组中
     *      long nextLong()：返回一个long型随机数，范围为long数据的范围，分布为均匀分布
     *      float nextFloat()：返回下一个伪随机数，它是取自此随机数生成器序列的、在0.0和1.0之间均匀分布float值
     *      double nextDouble()：返回下一个伪随机数，它是取自此随机数生成器序列的、在0.0和1.0之间均匀分布的 double值
     *      boolean nextBoolean()：返回下一个伪随机数，它是取自此随机数生成器序列的均匀分布的boolean值
     *      double nextGaussian()：返回下一个伪随机数，它是取自此随机数生成器序列的、呈高斯（“正态”）分布的double值，其平均值是0.0标准差是1.0。
     *
     */

    /**
     * java.Math包下有BigInteger类和BigDecimal类
     *  BigInteger类：可以表示不可变的任意精度的整数。
     *      BigInteger类提供了所有Java的基本整数操作符的对应物，并提供了Java.lang.Math的所有相关方法。
     *      此外，BigInteger类还提供了：核运算、GCD计算、质数测试、素数生成、位操作以及一些其他的操作
     *
     *      构造器：
     *          public BigInteger(String val):根据传入的字符串构建BigInteger对象
     *
     *      方法：
     *          public BigInteger abs():返回此BigInteger的绝对值的BigInteger
     *          public BigInteger add(BigInteger val):返回(this + val)的BigInteger
     *          public BigInteger subtract(BigInteger val):返回(this - val)的BigInteger
     *          public BigInteger multiply(BigInteger val):返回(this * val)的BigInteger
     *          public BigInteger divide(BigInteger val):返回(this / val)的BigInteger
     *          public BigInteger abs(BigInteger val):返回(this % val)的BigInteger
     *          public BigInteger pow(int exponent):返回this的exponent次幂的BigInteger
     */

    /**
     * BigDecimal类：支持不可变、任意精度的有符号十进制浮点数
     *      构造器：
     *          public BigDecimal(double val)
     *          public BigDecimal(String val)
     *
     *      常用方法：
     *          public BigDecimal add(BigDecimal val):返回(this + val)的BigDecimal
     *          public BigDecimal subtract(BigDecimal val):返回(this - val)的BigDecimal
     *          public BigDecimal multiply(BigDecimal val):返回(this * val)的BigDecimal
     *          public BigDecimal divide(BigDecimal val):返回(this / val)的BigDecimal
     *              注意：虽然BigDecimal支持任意精度的浮点数，但是小数向出可能会出现除不断的情况
     *
     */
}
