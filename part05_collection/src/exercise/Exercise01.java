package exercise;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Exercise01 {
    public static void main(String[] args) {
    }

    @Test
    public void test1() {
        /**
         * Collection接口中定义的常用方法
         */
        ArrayList coll = new ArrayList();
        // boolean add(Object obj)
        coll.add(123);
        coll.add(456);
        coll.add("String1");
        coll.add(new Integer[]{100, 200, 300});
        coll.add(new String[]{"hello", "world"});
        coll.add(new Person("张三", 18));
        System.out.println(coll);
        System.out.println();

        // boolean addAll(Object obj)
        Collection coll1 = new ArrayList();
        coll1.add("haha");
        coll1.add("hehe");
        coll1.add("enen");
        coll.addAll(coll1);
//        coll.add(coll1);
        System.out.println(coll);

        // boolean equals(Object obj)
        System.out.println(coll.equals(coll1));

        // booleab isEmpty()
        System.out.println(coll.isEmpty());
        // int size()
        System.out.println(coll.size());
        // String toString()
        System.out.println(coll.toString());
        // void clean()
        coll.clear();
        System.out.println(coll.size());
    }

    @Test
    public void test2() {
        /**
         * Collection接口中定义的常用方法
         */
        Collection coll = new ArrayList();
        // boolean add(Object obj)
        coll.add(new Integer(123));
        coll.add("String");
        coll.add(new Integer[]{100, 200, 300});
        coll.add(new Person("张三", 18));
        coll.add(new Man("张三", 18));
        System.out.println(coll);

        // boolean contains(Object obj)
        //说明一：
        // > 情况1：
        System.out.println(coll.contains("String")); // true

        // 情况2：
        Collection coll1 = new ArrayList();
        coll1.add("String");
        System.out.println(coll.contains(coll1)); // false

        // 情况1和2的结果不同，一个是常量，另一个是一个对象，要注意这点

        // 说明二：
        System.out.println(coll.contains(new Person("张三", 18))); // 此时Person类重写了equals()方法，所以结果是true
        System.out.println(coll.contains(new Man("张三", 18))); // 此时Man类没有重写了equals()方法，所以结果是false

        // 对于存入Collection集合中的元素，其所在类必须要重写equals()方法，因为在操作集合的时候会判断集合中是否存在该元素，
        // 通过集合中的每一个元素调用equals()方法与当前元素进行比较

        // 说明三：
        // 在这里需要说明的是：数组无法重写Object的equals()方法，所以添加两个内容相同的数组，这两个数组也是两个不同的数组
        System.out.println(coll.contains(new Integer[]{100, 200, 300})); // false
        System.out.println(coll.contains(new String[]{"haha", "hehe", "enen"})); // false
        System.out.println(coll.contains(new Person("张三", 18))); // true
        System.out.println(coll.contains(new Man("张三", 18))); // false
    }

    @Test
    public void test3() {
        //Collection接口中定义的常用方法
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);
        coll.add("String");
        coll.add(new String[]{"haha", "hehe", "enen"});
        coll.add(new Integer[]{100, 200, 300});
        coll.add(new Person("张三", 18));
        coll.add(new Man("张三", 18));
        System.out.println(coll);

        Collection coll1 = new ArrayList();
        coll1.add(123);
        coll1.add(456);
        coll1.add("String");

        // boolean containsAll(Collection coll)
        System.out.println(coll.containsAll(coll1));

        // containsAll()方法的检查：元素的顺序，判别的结果与元素的顺序无关
        Collection coll2 = new ArrayList();
        coll2.add(456);
        coll2.add(123);
        coll2.add("String");
        System.out.println(coll.containsAll(coll2));
    }

    @Test
    public void test4() {
        //Collection接口中定义的常用方法
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add("String");
        coll.add(new String[]{"haha", "hehe", "enen"});
        coll.add(new Person("张三", 18));
        System.out.println(coll);

        // boolean retainAll(Collection coll); // 获取当前元素的交集
        Collection coll1 = new ArrayList();
        coll1.add(123);
        coll1.add(456);
        System.out.println(coll.retainAll(coll1));
        System.out.println(coll);
    }

    @Test
    public void test5() {
        //Collection接口中定义的常用方法
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add("String");
        coll.add(new String[]{"haha", "hehe", "enen"});
        coll.add(new Person("张三", 18));
        System.out.println(coll);

        // boolean remove(Object obj) 删除指定的元素
        Object obj = coll.remove(1234);
        System.out.println(coll);
        System.out.println(obj);

        // boolean removeAll(Collection coll)
        ArrayList coll2 = new ArrayList();
        coll2.add(123);
        coll2.add("String");
        System.out.println(coll.removeAll(coll2));
        System.out.println(coll);
    }

    @Test
    public void test6() {
        ArrayList coll = new ArrayList();
        coll.add(123);
        coll.add("String");
        coll.add(new String[]{"haha", "hehe", "enen"});
        coll.add(new Person("张三", 18));
        System.out.println(coll);

        // Object[] toArray(Collection coll)
        // 将集合转换成数组
        Object[] obj = coll.toArray();
        for (int i = 0; i < obj.length; i++) {
            System.out.println(obj[i]);
        }

        // 将数组转换成集合
        int arr[] = new int[]{123, 456, 789};
        Collection coll1 = Arrays.asList(arr); // 只有一个元素，原因是将数组arr解析成一个Integer数组了，能用一个元素盛放
        System.out.println(coll1);

        Collection coll2 = Arrays.asList(123, 456, 789);
        System.out.println(coll2);//三个元素
    }

    @Test
    public void test7() {
        ArrayList coll = new ArrayList();
        coll.add(123);
        coll.add("String");
        coll.add(new String[]{"haha", "hehe", "enen"});
        coll.add(new Person("张三", 18));
        System.out.println(coll);

        // Iterator iterator() 迭代器
        Iterator iterator = coll.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        // 增强for循环
        for (Object obj : coll) {
            System.out.println(obj);
        }
    }
}
