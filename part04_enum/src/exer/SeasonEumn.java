package exer;

/**
 * Author: shaco
 * Date: 2022/10/30
 * Desc: 使用eumn创建枚举类
 */
public enum SeasonEumn {
    // TODO 1、直接列举出该枚举类的有限个确定的对象，如果有属性，同时写上属性值
    SPRING("春天", "春暖花开"),
    SUMMER("夏天", "春暖花开"),
    AUTUMN("秋天", "春暖花开"),
    WINTER("冬天", "冬日暖阳");

    // TODO 2、根据枚举类对象的属性，添加对应的类成员属性，声明为私有的
    private String SeasonName;
    private String SeasonDesc;

    // TODO 3、由于枚举类声明了私有的成员属性，所以需要添加相应的构造器，为私有的属性赋值
    private SeasonEumn(String SeasonName, String SeasonDesc) {
        this.SeasonName = SeasonName;
        this.SeasonDesc = SeasonDesc;
    }

    // TODO 4、其他需求
    // 重写toString()方法

    @Override
    public String toString() {
        return "SeasonEumn{" +
                "SeasonName='" + SeasonName + '\'' +
                ", SeasonDesc='" + SeasonDesc + '\'' +
                '}';
    }
}
