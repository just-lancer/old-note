package knowledge;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateTimeFormatterClass {
    @Test
    public void test() {
        // 创建LocalDateTime等对象
        LocalDateTime t1 = LocalDateTime.now();
        System.out.println(t1);

        // 方式一：预定义的标准格式。如：ISO_LOCAL_DATE_TIME;ISO_LOCAL_DATE;ISO_LOCAL_TIME
        // 创建DateTimeFormatter对象
        DateTimeFormatter formatter1 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String f1 = formatter1.format(t1);
        System.out.println(f1);

        // 方式二：本地化相关的格式。如：ofLocalizedDateTime()
        // FormatStyle.LONG / FormatStyle.MEDIUM / FormatStyle.SHORT :适用于LocalDateTime
        // 创建DateTimeFormatter对象
        DateTimeFormatter formatter2 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        String f2 = formatter2.format(t1);
        System.out.println(f2);

        // 方式三：定义的格式。如：ofPattern(“yyyy-MM-dd hh:mm:ss”)
        // 创建DateTimeFormatter对象
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String f3 = formatter3.format(t1);
        System.out.println(f3);
    }
}
