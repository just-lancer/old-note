package knowledge;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

public class LocalTime {
    /*
     * LocalDate,LocalTime,LocalDateTime,三个时间类都是java.time包下的三个类
     * 这三个类分别代表了，本地日期、本地时间、本地日期和时间，具有相同的方法可以调用
     */

    /*
     * LocalDate,LocalTime,LocalDateTime的常用方法
     * 1.now()系列方法
     * > public static LocalDateTime now() // 从默认时区的系统时钟获取当前日期时间
     * > public static LocalDateTime now(ZoneId zone) // 从指定时区的系统时钟获取当前日期时间
     *
     * 2.of()系列方法，of()方法也是一个静态方法，用于根据指定的日期，时间格式创建LocalDate,LocalTime,LocalDateTime对象
     * 		具有多个重载方法
     * > public static LocalDateTime of(int year, Month month, int dayOfMonth, int hour, int minute)
     *
     * 3.getXxx()相关方法
     * > getDayOfMonth() 获得月份天数（1~31）
     * > getDayOfYear() 获得年份天数（1~366）
     * > getDayOfWeek() 获得周天数，也就是返回星期几
     * > getYear() 获得年份
     * > getMonthValue() 获得月份（1~12）
     * > getMonth() 获得月份，英文的单词
     * > getHour()\getMinute()\getSecond() 获取小时、分钟、秒
     *
     * 4.withXxx()系列方法  修改指定值
     * > public LocalDateTime withDayOfMonth(int dayOfMonth)/withDayOfYear(int dayOfYear) 将月份天数、年份天数修改为指定的值，并返回新的对象
     * > public LocalDateTime withMonth(int month)/withYear(int year) 将月份、年份修改为指定的值，并返回新的对象
     * > public LocalDateTime withHour(int hour)/withMinute(int minute)/withSecond(int second) 将小时、分钟、秒修改为指定的值，并返回新的对象
     *
     * 5.plusXxx()系列方法 增加指定的值
     * > public LocalDateTime plusYears(long years)/plusMonths(long months)/plusWeeks(long weeks)/plusDays(long days)/plusHours(long hours)/plusMinutes(long minutes)/plusSeconds(long seconds)
     *   返回此 LocalDateTime 的副本，并添加指定的年、月、周、日、小时、分钟、秒数。
     *
     * 6.minusXxx()系列方法 减去指定的值
     * > public LocalDateTime minusYears(long years)/minusMonths(long months)/minusWeeks(long weeks)/minusDays(long days)/minusHours(long hours)/minusMinutes(long minutes)/minusSeconds(long seconds)
     *   返回此 LocalDateTime 的副本，并减去指定的年、月、周、日、小时、分钟、秒数。
     */

    @Test
    public void test() {
        // 会出现偏移量
        Date date = new Date(2021, 12, 7);
        System.out.println(date);

        //以LocalDateTime为例
        // 创建LocalDateTime对象，由于三个类的构造器是private的，所以不能直接调用其构造器，
        // 创建对象需调用now()方法
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        // 利用of()方法创建LocalDate,LocalTime,LocalDateTime对象
        LocalDateTime ldt1 = LocalDateTime.of(2021, 12, 7, 16, 34);
        System.out.println(ldt1);

        LocalDateTime ldt2 = ldt1.withYear(2022);
        System.out.println(ldt2);
    }
}
