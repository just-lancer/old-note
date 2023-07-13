package part01;

public class Exer08 {
    public static void main(String[] args) {
        System.out.println(func(98765));
    }

    //输入一个正整数n，将它倒序输出
    public static int func(int n) {
        if (n < 10) {
            return n;
        } else {
            int shang = 0;
            int yushu = 0;
            int res = 0;
            while (n > 0) {
                yushu = n % 10;
                shang = n / 10;
                res = res * 10 + yushu;
                n = shang;
            }
            return res;
        }
    }
}
