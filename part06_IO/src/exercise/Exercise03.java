package exercise;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Exercise03 {
    public static void main(String[] args) {
        FileReader fa = null;
        FileWriter fb = null;
        try {
            // 利用字节流进行复制粘贴
            // 1、创建源文件流和目标文件流
            fa = new FileReader("E:\\test\\a.txt");
            fb = new FileWriter("E:\\test\\b.txt");

            // 2、读取数据，写入数据
            int num;
            char[] ch = new char[4];
            while ((num = fa.read(ch)) != -1) {
                // fb开始写入数据
                fb.write(ch, 0, num);
            }
        } catch (IOException e) {
            System.out.println("出错了！");
        } finally {
            try {
                if (fa != null)
                    fa.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fa != null)
                    fb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
