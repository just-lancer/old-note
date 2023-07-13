package knowledge;

public class SimpletonPatternSafe {
    public static void main(String[] args) {

    }
}

class Simpleton1{
    // 创建一个单例模式
    // 饿汉式
    // 第一步，由于只能创建一个对象，所以必须将构造器私有化
    private Simpleton1(){

    }

    //创建一个对象，由于静态方法只能调用静态属性和方法，所以该属性也必须静态化
    private static Simpleton1 s = new Simpleton1(); // 这算是属性的初始化

    // 由于构造器私有化，无法创建对象，因此该get方法必须设置为静态方法
    public static Simpleton1 getSimpleton(){
        return s;
    }
}

class Simpleton2 {
    // 懒汉式
    // 线程不安全的
    private Simpleton2(){

    }

    private static Simpleton2 s;

    public static Simpleton2 getSimpleton2(){
        // 当所有的线程都运行到这个get方法的时候会出现线程不安全问题，共享数据为s
        synchronized (Simpleton2.class) {
            if (s == null){
                s = new Simpleton2();
            }
        }

        return s;
    }

}