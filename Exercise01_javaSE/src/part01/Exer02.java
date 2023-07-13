package part01;

public class Exer02 {
    public static void main(String[] args) {
//        System.out.println(func(5));
        func(5);


    }

    // 给定一个正整数n，求：n! + (n-1)! + ...+ 2! + 1!
    // 动态规划
    public static int func(int n) {
        int res = 1;
        int result = 0;
        for (int i = 1; i <= n; i++) {
            res = res * i;
            result = result + res;
            System.out.print(result + "  ");
        }
        return result;
    }
}
