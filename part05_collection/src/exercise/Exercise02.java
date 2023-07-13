package exercise;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Exercise02 {
    @Test
    public void exer02(){
        ArrayList arrayList = new ArrayList();

        //添加10个元素到集合中
        String name = "学生";
        int i = 0;

        while (i < 10){
            i++;
            arrayList.add(new Student("学生-"+i, (int)(Math.random()*101)));//生成0~100范围内的成绩
        }

        // 遍历
        for(Object obj : arrayList){
            System.out.println(obj + "  ");
        }
        System.out.println();

        // 遍历并删除分数低于60的元素
        Iterator iterator = arrayList.iterator();
        while(iterator.hasNext()){
            Object next = iterator.next();
            if(next instanceof Student){
                Student stu = (Student)(next);
                if(stu.getGrade() < 60){
                    iterator.remove();
                }
            }
        }

        // 再遍历
        for(Object obj : arrayList){
            System.out.println(obj + "  ");
        }
    }
}
