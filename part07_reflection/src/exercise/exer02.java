package exercise;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class exer02 {
    public static void main(String[] args) {
        // Class类的常用方法

        // static final Class forName(String classPath);创建一个Class对象
        try {
            Class clazz = Class.forName("java.lang.String");
            System.out.println(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // public Object newInstance()
        //        throws InstantiationException, IllegalAccessException;调用一个类的空参构造器创建一个对象
        try {
            String s = String.class.newInstance();
            s = s + "string";

            Person person = Person.class.newInstance();
            System.out.println(person);
            System.out.println(s);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // public String getName();返回Class对象表示的结构的名称
        String name1 = String.class.getName();
        String name2 = Person.class.getName();
        int[] ints = new int[3];
        String name3 = ints.getClass().getName();
        System.out.println(name1);
        System.out.println(name2);
        System.out.println(name3);

        // public native Class getSuperclass();返回Class对象的父类
        Class superclass = String.class.getSuperclass();
        Class superclass1 = Person.class.getSuperclass();
        Class superclass2 = new int[3].getClass().getSuperclass();
        System.out.println(superclass);
        System.out.println(superclass1);
        System.out.println(superclass2);

        // public Class[] getInterfaces();返回Class对象实现的所有接口
        Class[] interfaces = String.class.getInterfaces();
        System.out.println("interfaces = " + Arrays.toString(interfaces));
        Class<?>[] interfaces1 = new int[3].getClass().getInterfaces();
        System.out.println(Arrays.toString(interfaces1));

        // public ClassLoader getClassLoader();返回加载此类的类加载器
        ClassLoader c1 = String.class.getClassLoader();
        ClassLoader c2 = Person.class.getClassLoader();
        ClassLoader c3 = new int[3].getClass().getClassLoader();
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c2.getParent());
        System.out.println(c3);

        // public Constructor<T> getConstructor(Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException
        // 获取该Class对象的一个构造器，根据传入的参数，获取相应的构造器对象
        try {
            Constructor<Person> constructor = Person.class.getConstructor();
            // 获取到构造器了，现在调用newInstance()方法创建对象
            Person person = constructor.newInstance();
            System.out.println(constructor);
            System.out.println(person);

            Constructor<Person> constructor1 = Person.class.getConstructor(String.class, int.class);
            System.out.println(constructor1);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test1() {
        //  public Constructor<?>[] getConstructors() throws SecurityException;获取Class类对象的所有构造器
        Constructor<?>[] constructors = Person.class.getConstructors();
        System.out.println(Arrays.toString(constructors));

        // public Field[] getDeclaredFields() throws SecurityException;获取Class类对象所有声明为public的属性
        try {
            Field[] fields = Person.class.getFields();
            Field name1 = Person.class.getField("age");
            System.out.println(name1);

            System.out.println(Arrays.toString(fields));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // public Method getMethod(String name, Class<?>... parameterTypes)
        //     *         throws NoSuchMethodException, SecurityException：
        Method[] methods = Person.class.getMethods();
        System.out.println(Arrays.toString(methods));
    }


}
