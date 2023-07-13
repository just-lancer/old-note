package exercise;

import java.io.FileReader;
import java.io.IOException;

public class Exercise02 {
    public static void main(String[] args) {
        FileReader fd = null;
        try {
            // 利用字符流进行数据的复制粘贴
            // 1、创建流对象
            fd = new FileReader("E:\\test\\a.txt");

            // 2、调用read()方法进行数据读取
            char[] ch = new char[4];
//            int num = fd.read(ch);
//            System.out.println(num);

            // 3、控制台打印数据
            // 第一种写法
//            while (num != -1) {
//                System.out.print(new String(ch, 0 ,num));
//                num = fd.read(ch);
//            }

            // 第二种写法
            int num;
            while ((num = fd.read(ch)) != -1) {
                System.out.print(new String(ch, 0, num));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4、调用close()方法释放流资源
            if (fd != null) {
                try {
                    fd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
