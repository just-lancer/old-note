package part01;

public class Exer01 {
    public static void main(String[] args) {
        func1(10);
    }

    // 动态规划：斐波那契数列
    public static int func1(int n) {
        if (n <= 2) {
            return 1;
        } else {
            int dp[] = new int[3];
            dp[0] = 1;
            dp[1] = 1;
            int i = 1;
            do {
                dp[2] = dp[0] + dp[1];
                System.out.print(dp[2] + "  ");
                dp[0] = dp[1];
                dp[1] = dp[2];
                i++;
            } while (i < n - 1);
            return dp[2];
        }
    }
}
