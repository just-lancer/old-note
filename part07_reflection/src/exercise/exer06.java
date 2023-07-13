package exercise;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class exer06 {
    @Test
    public void test1() throws Exception {
        // 获取当前类的其他结构
        Class<Person> personClass = Person.class;

        // 1、获取当前Class类的父类，调用getSuperclass()方法
        //  public native Class<? super T> getSuperclass()
        //      返回当前Class类对象的父类
        Class<? super Person> superclass = personClass.getSuperclass();

        // 2、获取当前Class类的带泛型的父类，调用getGenericSuperclass()
        //  public Type getGenericSuperclass()
        //      返回当前Class类对象的带泛型的父类
        Type genericSuperclass = personClass.getGenericSuperclass();


        // 3、获取当前Class类的接口：
        //  public Class<?>[] getInterfaces()；不带泛型
        //  public Type[] getGenericInterfaces()；带泛型
        Class<?>[] interfaces = personClass.getInterfaces();
        Type[] genericInterfaces = personClass.getGenericInterfaces();

        // 4、获取运行时类所在的包，调用getPackage()
        //  public Package getPackage()
        Package aPackage = personClass.getPackage();

        // 5、获取运行时类声明的注解
        //  public <A extends Annotation> A getAnnotation(Class<A> annotationClass)
        //  public Annotation[] getAnnotations()
        Annotation annotation = personClass.getAnnotation(null);
        Annotation[] annotations = personClass.getAnnotations();
    }
}
