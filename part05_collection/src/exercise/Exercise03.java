package exercise;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Exercise03 {
    // List接口中定义的方法
    @Test
    public void test1() {
        ArrayList arr = new ArrayList();
        arr.add(123);
        arr.add("String");
        arr.add(new Person("张三", 18));
        arr.add(false);

        // 增：void add(int index, Object obj)
        arr.add(1, 456);
        System.out.println(arr);

        // 增：void addAll(int index, List l)
        ArrayList arr1 = new ArrayList();
        arr1.add(987);
        arr1.add(654);
        arr.addAll(2, arr1);
        System.out.println(arr);

        // 删：Object remove(int index)
        Object remove = arr.remove(3);
        System.out.println(arr);
        System.out.println(remove);

        // 改: boolean set(int index, Object obj)
        Object set = arr.set(0, 321);
        System.out.println(arr);
        System.out.println(set);

        // 查：Object get(int index)
        Object o = arr.get(4);
        System.out.println(arr);
        System.out.println(o);

    }

    @Test
    public void test2() {
        ArrayList arr = new ArrayList();
        arr.add(123);
        arr.add("String");
        arr.add(new Person("张三", 18));
        arr.add(false);
        arr.add(123);
        arr.add("hahah");

        // int indexOf(Object obj)  int lastIndexOf(Object obj)
        int befor = arr.indexOf(123);
        int after = arr.lastIndexOf(123);
        System.out.println("befor = " + befor);
        System.out.println("after = " + after);

        // List subList(int beginIndex, int endIndex)
        List list = arr.subList(2, 5);
        System.out.println(list);


    }
}
