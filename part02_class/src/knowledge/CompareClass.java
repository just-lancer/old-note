package knowledge;

public class CompareClass {
    /**
     *  为了实使对象之间能够进行比较，那么对象所属的类必须实现Comparable接口或者在需要比较时直接指定比较大小的方式
     *
     *  Java实现对象排序（大小比较）有两种方式：
     *      自然排序：java.lang.Comparable
     *      定制排序：java.util.Comparator
     *
     *
     *  自然排序：java.lang.Comparable
     *      实现Comparable接口的类必须重写comparaTo(Object obj)方法，两个对象即通过comparaTo(Object obj)方法的返回值来比较大小
     *      如果当前对象this大于形参Object，则返回正整数；如果当前对象this小于形参obj，则返回负整数，如果当前对象this等于形参obj则返回0
     *
     *      Comparable的典型实现类：（默认按照从小到大排序）
     *      String：按照字符串中字符的Unicode值进行比较，字典排序
     *      Charactor：按照字符的Unicode值进行比较
     *      包装类及BigInteger、BigDecimal类，按照对应数值的大小进行
     *      Date、Time等，后面的日期、时间大于前面的日期
     *
     *
     *  定制排序：java.util.Comparator
     *      当元素的类型没有实现java.lang.Comparable接口而又不方便修改代码、
     *      或者实现了java.lang.Comparable接口，但排序规则不适合当前操作，
     *      那么就需要使用Comparator接口了
     *
     *      继承java.util.Comparator接口需要重写方法中的compara(Object o1, Object o2)方法
     *      compara(Object o1, Object o2)用于比较o1和o2对象，
     *      如果方法返回正整数，表示o1大于o2；如果方法返回负整数，表示o1小于o2；如果方法返回0，表示o1等于o2
     *
     *      Comparator接口的实现类对象可以作为参数传递给一些排序函数，或者构造器
     *
     */
}
