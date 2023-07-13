package exercise;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class exer03 {
    // 获取类的各个结构

    @Test
    public void test1() throws Exception {
        // 获取Classd类对象的属性结构
        // 方法一：public Field getField(String name) throws NoSuchFieldException, SecurityException
        //        根据传入的属性的名称，返回当前Class类对象相应的权限为public的属性结构
        //       注意：是包含父类的属性结构的
        Field field1 = Person.class.getField("age");
        System.out.println(field1);

        // 方法二：public Field[] getFields() throws SecurityException
        //      返回当前Class类对象的所有权限为public的属性结构
        //      注意：是包含父类的属性结构的
        Field[] field2 = Person.class.getFields();
        for (Object obj : field2) {
            System.out.println(obj);
        }

        // 方法三：public Field[] getDeclaredField(String name) throws SecurityException
        //      根据传入的属性的名称，返回当前Class类对象相应的属性结构
        //      注意：不包含父类的属性结构；无视属性的访问权限
        Field field3 = Person.class.getDeclaredField("name");
        System.out.println(field3);

        // 方法四：public Field[] getDeclaredFields() throws SecurityException
        //      返回当前Class类对象所有的属性结构
        //      注意：不包含父类的属性结构；无视属性的访问权限
        Field[] field4 = Person.class.getDeclaredFields();
        for (Object obj : field4) {
            System.out.println(obj);
        }
        System.out.println("********");

    }

    @Test
    public void test2() throws Exception {
        // 现在已经知道如何获取Class类对象的属性，其类型是Field，那么，如何使用？
        //      需要明确的是，属性结构包含：权限修饰符 属性的类型 属性名，这三者构成了一个Field对象

        // 第一步：创建Class类对象
        Class personClass = Person.class;

        // 第二步：利用Class对象创建Person对象，调用newInstance()方法
        Object o = personClass.newInstance();
        Person p = (Person) o;

        //第三步：获取Class对象的属性
        Field name = personClass.getField("name");

        // 第四步：将属性Field类的属性设置为可以访问的，调用setAccessible()方法
        name.setAccessible(true);

        // 以下是如何使用Feild类对象，注意Feild类对象表示的是一个Class类的属性结构
        // 属性的结构：权限修饰符  数据类型  属性名称

        // 获取属性的值，调用Field中的get()方法
        // public Object get(Object obj) throws IllegalArgumentException, IllegalAccessException
        //      Object obj：想要哪个类的属性就传入哪个类的实例对象
        Object name1 = name.get(p);
        System.out.println(name1);

        // 将属性重新赋值，调用set()方法
        // public void set(Object obj, Object value) throws IllegalArgumentException, IllegalAccessException
        //       Object obj：设置哪个对象；Object value：设置哪个属性值
        name.set(p, "Jerry");
        System.out.println(name.get(p));


        // 获取属性的数据类型，调用getType()
        // public Class getType()
        name.getType();
        System.out.println(name.getType());

        // 获取属性结构的权限修饰符，调用getModifiers()方法
        // public native int getModifiers();
        int modifiers = name.getModifiers();
        System.out.println(modifiers);
        //Modifier类中用整数类型表示public 缺省  protected  private这四个访问权限
        System.out.println(Modifier.toString(modifiers));
    }


}
