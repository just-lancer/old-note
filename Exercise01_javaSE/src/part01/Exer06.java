package part01;

public class Exer06 {
    //一个球从100米高度自由落下，每次落地后反跳回原高度的一半，再落下，求它在第10次落地时，共经过多少米？第10次反弹多高？
    // 给定一个double类型数n和整数类型m，求n + n/2 + n/2^2 + n/2^3 + ...+ n/2^m的和
    public static void main(String[] args) {
        double result = func(100, 5);
        System.out.println(result);
    }

    // n是初始的高度，m是第m次落地
    public static double func(double n, int m) {
        double init = n;
        double res = 0;//第i次落地时行走的路程，包含上升的路程
        for (int i = 1; i < m; i++) {
            res = res + n / 2;
            n = n / 2;
        }
        return res * 2 + init;
    }
}
