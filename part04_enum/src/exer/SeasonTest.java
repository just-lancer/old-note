package exer;

/**
 * Author: shaco
 * Date: 2022/10/30
 * Desc: 手动定义枚举类
 */
public class SeasonTest {
    // TODO 1、声明私有的、常量属性
    private final String SEASONNAME;
    private final String SEASONDESC;

    // TODO 2、私有化构造器，并为属性赋值
    private SeasonTest(String SEASONNAME, String SEASONDESC) {
        this.SEASONDESC = SEASONDESC;
        this.SEASONNAME = SEASONNAME;
    }

    // TODO 3、创建枚举类的有限的、明确的对象，同时利用构造器进行私有常量属性赋值
    public static final SeasonTest SPRING = new SeasonTest("春天", "春暖花开");
    public static final SeasonTest SUMMER = new SeasonTest("夏天", "夏日炎炎");
    public static final SeasonTest AUTUMN = new SeasonTest("秋天", "秋高气爽");
    public static final SeasonTest WINTER = new SeasonTest("冬天", "冬日暖阳");

    // 至此，枚举类其实已经创建完成，如果有别的需求可以正常声明
    // TODO 4、其他需求，重写toString()方法

    @Override
    public String toString() {
        return "SeasonTest{" +
                "SEASONNAME='" + SEASONNAME + '\'' +
                ", SEASONDESC='" + SEASONDESC + '\'' +
                '}';
    }
}