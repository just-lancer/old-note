package knowledge;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeClass {
    //常用的时间类的学习
    /*
    JDK 8之前常用的时间相关API
    1、System.currentTimeMillis(); // 获取当前时间的时间戳

    2、java.util.Date类
        两个构造器
        >java.util.Date(); // 创建具有当前时间时间戳的Date对象
        >java.util.Date(long time); // 创建具有指定时间戳的Date对象

        两个方法
        >toString(); // 打印当前时间戳对应的年月日、时分秒
        >public long getTime(); // 获取当前Date对象

    3、java.sql.Date类
        java.sql.Date类是java.util.Date类的子类
        具有相同的两个构造器和两个方法

        如何将java.util.Date类型数据转换成java.util.Date类型数据
        中间值：long型数据时间戳

    4、SimpleDateFormat类，非线程安全类，用于对Date对象进行格式化和解析
        两个构造器
        >SimpleDateFormat(); // 默认构造器，其创造的对象会按默认格式对Date对象进行解析和格式化
        >SimpleDateFormat(String pattern); // 其创造的对象能按指定格式对Date对象进行解析和格式化

        两个方法
        >public final String format(java.util.Date date) //格式化方法，将Date对象输出为字符串
        >public java.util.Date parse(String source) throws java.text.ParseException // 将字符串解析成一个Date对象

     */

    @Test
    public void test() {
        long systemTime = System.currentTimeMillis();
        System.out.println(systemTime);

        Date d1 = new Date();
        Date d2 = new Date(systemTime);
        System.out.println(d1);
        System.out.println(d1.toString());
        System.out.println(d1.getTime());
        System.out.println(d2);
        System.out.println(d2.toString());
        System.out.println(d2.getTime());
        System.out.println("*****");

        java.sql.Date d3 = new java.sql.Date(systemTime);
        System.out.println(d3.toString());
        long time = d3.getTime();
        System.out.println(time);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        String s = simpleDateFormat.format(d1);
        System.out.println(s);
        try {
            Date date = simpleDateFormat.parse("2021-12-6 10-55-55");
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        // 5、Calendar类，日历类，他是一个抽象类
        // Calendar类常用的属性
        /*
         * Calendar.AM	            从午夜到中午之前这段时间的 AM_PM 字段值
         * Calendar.PM	            从中午到午夜之前这段时间的 AM_PM 字段值
         * Calendar.YEAR	        年份
         * Calendar.MONTH	        月份
         * Calendar.DATE	        日期
         * Calendar.HOUR	        小时(12小时制)
         * Calendar.MINUTE	        分钟
         * Calendar.SECOND	        秒
         * Calendar.WEEK_OF_YEAR	年内的某星期
         * Calendar.WEEK_OF_MONTH	月内的某星期
         * Calendar.DAY_OF_YEAR	    年内的某天
         * Calendar.DAY_OF_MONTH	月内的某天
         * Calendar.DAY_OF_WEEK	    星期内的某天(从周日开始计算)
         * Calendar.HOUR_OF_DAY	    小时(24小时制)
         */
        // 创建一个Calendar对象
        Calendar c1 = Calendar.getInstance();
        // 调用Calendar对象的常用方法
        // 1. public int get(int field)  传入field字段（这些常量的值都是int型）获得给定日历字段的值，其中field是Calendar类的常用属性
        int year = c1.get(Calendar.YEAR);
        int month = c1.get(Calendar.MONTH);
        int days = c1.get(Calendar.DAY_OF_MONTH);
        int hours = c1.get(Calendar.HOUR);
        int hours_of_day = c1.get(Calendar.HOUR_OF_DAY);
        int minute = c1.get(Calendar.MINUTE);
        int second = c1.get(Calendar.SECOND);
        System.out.println("今天是" + year + "年" + month + "月" + days + "日，" + hours_of_day + "时(" + hours + "时)" + minute + "分" + second + "秒");
        // 从这个例子当中也可以知道，Calendar调用默认的getInstance()方法创建的Calendar对象，默认的时间就是当前时间

        // 2.public void set()系列的方法
        // ① public void set(int field, int value) 将Calendar对象的某一属性设置成指定属性
        c1.set(Calendar.DAY_OF_MONTH, 8);
        c1.set(Calendar.DAY_OF_WEEK, 3);
        int days1 = c1.get(Calendar.DAY_OF_MONTH);
        int days2 = c1.get(Calendar.DAY_OF_WEEK);
        System.out.println("一个月中的第：" + days1 + "天");
        System.out.println("一周中的第：" + days2 + "天");
        //②public final void set(int year, int month,int date) 将Calendar对象的年月日设置成指定的年月日
        c1.set(2022, 1, 7);
        year = c1.get(Calendar.YEAR);
        month = c1.get(Calendar.MONTH);
        days = c1.get(Calendar.DAY_OF_MONTH);
        System.out.println("今天是" + year + "年" + month + "月" + days + "日");
        // ③public final void set(year, month, date, hourOfDay, minute) 设置指定的年月日，小时、分钟
        // ④public final void set(year, month, date, hourOfDay, minute, second) 设置指定的年月日，时分秒

        // 3. public final void setTime(Date date) 将指定的Date类型时间转化成Calendar对象的时间
        // 4. public final Date getTime() 返回一个表示此 Calendar 时间值（从历元至现在的毫秒偏移量）的 Date对象
        Date date = new Date(1638776783728L);
        System.out.println(date);
        System.out.println(date.getTime());
        c1.setTime(new Date(1638776783728L));
        System.out.println(c1.getTime());

        // 5. public void setTimeInMillis(long millis) 用给定的 long 类型值设置此 Calendar 的当前时间值。
        c1.setTimeInMillis(1638776783728L);
        System.out.println(c1.getTime());

        //6. public long getTimeInMillis() 返回此 Calendar的时间戳，以毫秒为单位。
        Calendar c2 = Calendar.getInstance();
        long value = c2.getTimeInMillis();
        System.out.println(value);
    }
}
