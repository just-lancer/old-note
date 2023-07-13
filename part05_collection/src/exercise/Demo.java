package exercise;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Author: shaco
 * Date: 2022/11/5
 * Desc: 遍历集合的三种方式
 */
public class Demo {
    @Test
    public void test() {
        ArrayList arr = new ArrayList();
        arr.add(123);
        arr.add(new Integer(123));
        arr.add("abc");
        arr.add(true);
        arr.add(0.34);
        arr.add(new String[]{"abc", "456"});
        arr.add(new Circle());

        // TODO 1、普通for循环
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }

        // TODO 2、迭代器
        Iterator iterator = arr.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        // TODO 3、增强for循环/forEach
        for (Object obj : arr) {
            System.out.println(obj);
        }
    }


    @Test
    public void test2() {
        ArrayList arr = new ArrayList();
        arr.add(123);
        arr.add(new Integer(123));
        arr.add("abc");
        arr.add(true);
        arr.add(0.34);
        arr.add(new String[]{"abc", "456"});
        arr.add(new Circle());

        // TODO 1、集合转换成数组
        Object[] objects = arr.toArray();
        System.out.println(Arrays.toString(objects));

        // TODO 2、数组转换成集合
        // 第一种写法
        String[] str = new String[]{"abc", "123"};
        System.out.println(Arrays.asList(str));
        // 第二种写法
        System.out.println(Arrays.asList("hello java", "hello world"));

        //TODO 将基本类型数组转换成集合，有些地方需要注意
        // 第一种写法，如果将基本类型数组直接作为asList()方法的参数传入，那么asList()方法会直接将该数组作为一个对象转换成集合的一个元素
        int[] ints = {1, 2, 3, 3, 4};
        System.out.println(Arrays.asList(ints)); //[[I@5d6f64b1]
        // 第二种写法，如果将需要转换成集合元素的基本类型数据，一个个写入asList()方法中，那么这些基本类型数据会逐个转换成集合的元素
        System.out.println(Arrays.asList(1, 2, 3, 4, 5)); // [1, 2, 3, 4, 5]
    }
}
