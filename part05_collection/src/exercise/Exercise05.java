package exercise;

import java.util.Iterator;
import java.util.LinkedList;

public class Exercise05 {
    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        for (int i = 1; i < 20; i++) {
            linkedList.add(new Employee("员工" + i, (int) (Math.random() * 27 + 22), (int) (Math.random() * 16000 + 4000)));
        }

        // 遍历：迭代器
        Iterator iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            System.out.println(next);
        }

        // 遍历：增强for循环
        for (Object o : linkedList) {
            if (o instanceof Employee) {
                Employee e = (Employee) o;
                if (e.getSalary() < 10000) {
                    System.out.println(e);
                }
            }
        }
    }
}
