package exer;

import org.junit.Test;

import java.util.Date;

/**
 * Author: shaco
 * Date: 2022/10/29
 * Desc: 练习
 */
public class Demo {
    @Test
    public void test1() {
        // 1、java.util.Date类，需要掌握两个常用的构造器，和常用的两个方法
        // 使用两个不同构造器创建java.util.Date对象
        Date date1 = new Date();
        Date date2 = new Date(42183568456478L);
        System.out.println(date1); // 输出：Sat Oct 29 13:34:09 SGT 2022
        System.out.println(date2); // 输出：Thu Sep 30 01:54:16 SGT 3306

        // 使用两个常用的方法
        // 1、public String toString()：显示该java.util.Date对象表示的时间的年、月、日、时、分、秒、星期等信息
        System.out.println(date1.toString()); // 输出：Sat Oct 29 13:34:09 SGT 2022
        System.out.println(date2.toString()); // 输出：Thu Sep 30 01:54:16 SGT 3306

        // 2、public long getTime()：获取当前Date对象表示的时间所对应的毫秒数。（时间戳）
        System.out.println(date1.getTime()); // 输出：1667021649692
        System.out.println(date2.getTime()); // 输出：42183568456478

        // 2、java.sql.Date类，这个类有两个构造器，但是有一个过时了
        // 使用public Date(long date)构造器创建对象
        Date date3 = new java.sql.Date(13451734457L);
        System.out.println(date3); // 输出：1970-06-06

        // 使用两个常用的方法
        // 1、public String toString()：显示该java.sql.Date对象表示的时间的年月日信息
        System.out.println(date3.toString()); // 输出：1970-06-06

        // 2、public long getTime()：获取当前java.sql.Date对象表示的时间所对应的毫秒数。（时间戳）
        System.out.println(date3.getTime()); // 输出：13451734457

        // TODO 如何将java.util.Date对象转换成java.sql.Date？
        // 通过时间戳进行转换
        Date date4 = new Date();
        long time = date4.getTime();
        java.sql.Date date5 = new java.sql.Date(time);
        System.out.println(date4); // 输出：Sat Oct 29 13:46:19 SGT 2022
        System.out.println(date5); // 输出：2022-10-29
    }
}
