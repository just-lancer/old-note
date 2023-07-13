package review;

/**
 * Author: shaco
 * Date: 2022/10/16
 * Desc: 单例模式（懒汉式）线程安全性解决
 */
public class Single_Case {
    // TODO 1、私有化构造器
    private Single_Case() {
    }

    // TODO 2、内部申明类的对象，并私有化
    // TODO 5、由于静态方法内部无法调用非静态结构，所以类的对应也必须声明为静态的
    private static Single_Case single_case = null;

    // TODO 3、提供公有化方法返回类的对象
    // TODO 4、由于类的构造器被私有化，外部无法创建对象，所以该公共方法必须声明为静态的，便于被调用
    public static Single_Case getSingle_case() {
        // 此处容易发生线程安全的问题
        // 改进
        if (single_case == null) {
            // 静态方法内部不能使用this关键字，此时多线程效率较低
            synchronized ("this") {
                if (single_case == null) {
                    single_case = new Single_Case();
                }
            }
        }
        return single_case;
    }
}
