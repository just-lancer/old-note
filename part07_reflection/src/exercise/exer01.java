package exercise;

public class exer01 {
    public static void main(String[] args) {
        // 创建Class类对象的四种方法
        // 以字符串类为例

        // 方式一：调用类的class属性
        Class clazz1 = String.class;
        System.out.println(clazz1);

        // 方式二：类对象调用getClass()方法
        Class clazz2 = "abc".getClass();
        System.out.println(clazz2);

        // 方式三：调用Class类的静态方法forName(String classPath)，传入全类名
        // Class只有两个重载的forName()静态方法
        try {
            Class clazz3 = Class.forName("java.lang.String");
            System.out.println(clazz3);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 方式四：利用类加载器
        ClassLoader classLoader = Person.class.getClassLoader();
        try {
            Class clazz4 = classLoader.loadClass("java.lang.String");
            System.out.println(clazz4);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
