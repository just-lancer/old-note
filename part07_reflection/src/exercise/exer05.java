package exercise;

import org.junit.Test;

import java.lang.reflect.Constructor;

public class exer05 {
    // 获取当前Class类对象的构造器

    @Test
    public void test1() throws Exception {
        Class clazz = Person.class;
        Object o = clazz.newInstance();

        // 方法一：调用getConstructor()方法
        //  public Constructor<T> getConstructor(Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException
        //        根据传入的参数，返回当前Class类对象的构造器，包括父类构造器，权限为public
        Constructor constructor = clazz.getConstructor();

        // 方法二：调用getConstructors()方法
        //  public Constructor<?>[] getConstructors() throws SecurityException
        //      返回当前Class类对象的所有构造器，包括父类的，权限为：public
        Constructor[] constructors = clazz.getConstructors();

        // 方法三：调用getDeclaredConstructor()方法
        //  public Constructor<T> getDeclaredConstructor(Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException
        //        根据传入的构造器名称，返回当前Class类对象自己定义的构造器，无视访问权限
        Constructor declaredConstructor = clazz.getDeclaredConstructor();

        // 方法四：调用getDeclaredConstructors()方法
        //  public Constructor<?>[] getDeclaredConstructors() throws SecurityException
        //      返回当前Class类对象自己定义的所有构造器，无视访问权限
    }
}
