package exercise;

import java.util.HashSet;

public class Exercise06 {
    // 创建Set集合，并向其中添加10个100以内的随机偶数，并遍历显式
    public static void main(String[] args) {
        HashSet hashSet = new HashSet();
        // 添加
        int i = 0;
        while (i <= 10) {
            int num = (int) (Math.random() * 101);
            if (num % 2 == 0) {
                hashSet.add(num);
                i++;
            } else {
                continue;
            }
        }

        // 遍历
        for (Object o : hashSet) {
            System.out.println(o);
        }
    }
}
