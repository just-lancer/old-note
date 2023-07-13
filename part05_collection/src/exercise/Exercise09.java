package exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Exercise09 {
    public static void main(String[] args) {
        // 定义一个整数n，表示需要添加的学生的个数
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入需要添加的学生的个数：");
        int n = sc.nextInt();

        HashMap<String, String> mate = new HashMap<>();
        while (n > 0) {
            System.out.print("请输入学生姓名：");
            String name = sc.next();
            System.out.println();

            System.out.print("请输入学生联系方式：");
            String phone = sc.next();

            mate.put(name, phone);
            n--;
        }

        // 遍历
        Set<Map.Entry<String, String>> entries = mate.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry);
        }

    }
}
