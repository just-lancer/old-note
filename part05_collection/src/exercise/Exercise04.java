package exercise;

import org.junit.Test;

import java.util.*;

public class Exercise04 {
    @Test
    public void test1() {
        // Set接口没有额外定义多的方法，因此都是使用的Collection接口中的方法
        HashSet hashSet = new HashSet();
        hashSet.add(123);
        hashSet.add(456);
        hashSet.add("String");
        hashSet.add(false);
        hashSet.add(123);

        // hashSet的遍历
        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void test2() {
        // Set接口没有额外定义多的方法，都是使用的Collection接口中的方法

        //LinkedHashSet举例
        LinkedHashSet hashSet = new LinkedHashSet();
        hashSet.add(123);
        hashSet.add(456);
        hashSet.add("String");
        hashSet.add(false);
        hashSet.add(123);

        // hashSet的遍历
        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void test3() {
        // TreeSet举例，自然排序演示
        TreeSet treeSet = new TreeSet();
        treeSet.add(new Women("张三", 22));
        treeSet.add(new Women("李四", 24));
        treeSet.add(new Women("王五", 18));
        treeSet.add(new Women("赵六", 20));
        System.out.println(treeSet);

    }

    @Test
    public void test4() {
        // TreeSet举例，定制排序，Comparator
        TreeSet treeSet = new TreeSet(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Singer && o2 instanceof Singer) {
                    Singer s1 = (Singer) o1;
                    Singer s2 = (Singer) o2;

                    // 此处设计两层排序，首先按年龄从大到小排序，再按照歌曲数量从小到大
                    // 从此实例可知，Comparable和Comparator两个都是默认从小达到排序
                    // 默认前者大于后者，返回正数，前者小于后者，返回负数，两者相等，返回0
                    if (s1.getAge() > s2.getAge()) {
                        return -1;
                    } else if (s1.getAge() < s2.getAge()) {
                        return 1;
                    } else {
                        if (s1.getNum() > s1.getNum()) {
                            return 1;
                        } else if (s1.getNum() < s2.getNum()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }

                } else {
                    throw new RuntimeException("输入数据的类型不是Singer类型");
                }
            }
        });

        treeSet.add(new Singer("张学友", 50, 600));
        treeSet.add(new Singer("黎明", 55, 430));
        treeSet.add(new Singer("刘德华", 45, 500));
        treeSet.add(new Singer("张国荣", 60, 340));
        treeSet.add(new Singer("郭富城", 49, 210));
        treeSet.add(new Singer("谭亚麟", 60, 300));

        // 遍历TreeSet
        for (Object o : treeSet) {
            System.out.println(o);
        }

        //treeSet.add(new Man("张三", 19)); // 抛异常
    }
}
