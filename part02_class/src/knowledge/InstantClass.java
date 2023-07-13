package knowledge;

import org.junit.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class InstantClass {
    /*
    java.time.Instant类的学习
    常用方法
    > public static Instant now()  //获取具有本初子午线对应的标准时间的Instant对象
    > public java.time.OffsetDateTime atOffset(@NotNull java.time.ZoneOffset offset)  //将此瞬间与偏移量组合以创建 OffsetDateTime。
    > public long toEpochMilli() // 将此瞬间转换为自 1970-01-01T00:00:00Z 纪元以来的毫秒数。
    > public static Instant ofEpochMilli(long epochMilli) // 从 1970-01-01T00:00:00Z 的纪元使用毫秒获取 Instant 的实例。从指定的毫秒中提取秒和纳秒。
     */
    @Test
    public void test(){
        Instant now = Instant.now();
        System.out.println(now);

        OffsetDateTime offsetDateTime = now.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);

        long l = now.toEpochMilli();
        System.out.println(l);

        Instant instant = Instant.ofEpochMilli(1638880388088L);
        System.out.println(instant);
    }
}
