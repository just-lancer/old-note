package part01;

public class Exer07 {
    public static void main(String[] args) {
        System.out.println(func(5));
    }

    //有n步台阶，一次只能上1步或2步，共有多少种走法？
    public static int func(int n) {
        if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        } else {
            int res = 0;
            // 定义一个数组int dp[]，其中dp[n]表示走上第n阶台阶所需的步数
            // 则有dp[n] = dp[n-1] + dp[n-2],其中n >= 3,
            int dp[] = new int[n];
            dp[0] = 1;
            dp[1] = 2;
            for (int i = 2; i < n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
            return dp[dp.length - 1];
        }
    }

}
