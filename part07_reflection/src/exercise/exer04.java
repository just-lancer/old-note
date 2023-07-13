package exercise;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class exer04 {
    @Test
    public void test1() throws Exception {
        // 获取方法的所有结构
        // 方法的定义结构
        // @XXXX
        // 权限修饰符   返回值类型  方法名(形参列表) 抛出的异常

        // 获取Class类对象的方法
        // 方法一：public Method getMethod(String name, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException
        //        根据传入的方法的名称，返回当前Class类对象的声明为public的方法，包含其父类的方法
        Method method1 = Person.class.getMethod("show");
        System.out.println(method1);

        // 方法二：public Method[] getMethods() throws SecurityException
        //      获取当前Class类对象声明的权限为public的方法，包含其父类的方法
        //        对于子类重写父类的方法，只返回子类重写的方法
        Method[] method2 = Person.class.getMethods();
        for (Object obj : method2) {
            System.out.println(obj);
        }

        // 方法三：public Method getDeclaredMethod(String name, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException
        //        根据传入的方法的名称，返回当前Class类对象自己声明的方法，无视其访问权限
        Method method3 = Person.class.getDeclaredMethod("eat");
        System.out.println(method3);
        System.out.println();

        // 方法四：public Method[] getDeclaredMethods() throws SecurityException
        //      返回当前Class类对象自己声明的所有方法，无视访问权限
        Method[] method4 = Person.class.getDeclaredMethods();
        System.out.println(Arrays.toString(method4));
    }

    @Test
    public void test2() throws Exception {
        Method method = Person.class.getMethod("toString");

        // 获取方法的其他结构
        // 1、获取方法的访问权限，调用getModifiers();
        //      public native int getModifiers();
        int modifiers = method.getModifiers();
        // Modifier类中用整数类型的静态常量表示四种访问权限
        System.out.println(Modifier.toString(modifiers));

        // 2、获取方法的注解
        // > public <T extends Annotation> T getAnnotation(Class<T> annotationClass)
        //      根据指定的注解名返回当前方法的注解

        // > public Annotation[] getAnnotations()
        //      返回当前方法的所有注解
        Annotation[] annotations = method.getAnnotations();

        // > public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass)
        //      根据传入的注解名称，返回该方法自身定义的注解

        // > public Annotation[] getDeclaredAnnotations()
        //      返回该方法所有的注解
        Annotation[] declaredAnnotations = method.getDeclaredAnnotations();


        // 3、返回值类型：调用getReturnType()方法
        //      public Class<?> getReturnType()，返回该方法的返回值的类型
        Class<?> returnType = method.getReturnType();

        // 4、方法名称，调用getName()方法
        //      public String getName()
        String name = method.getName();

        // 5、形参列表，调用getParameterTypes()
        //      public Class<?>[] getParameterTypes()，将该方法的所有形参，以数组的形式返回
        Class<?>[] parameterTypes = method.getParameterTypes();

        // 6、抛出的异常，调用getExceptionTypes()
        //      public Class<?>[] getExceptionTypes()，将该方法的所有抛出异常，以数组的形式返回
        Class<?>[] exceptionTypes = method.getExceptionTypes();

    }
}
