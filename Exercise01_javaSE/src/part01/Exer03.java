package part01;

public class Exer03 {
    public static void main(String[] args) {
        func(5);
    }

    public static void func(int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= 2 * n - 1; j++) {
                if (j <= n - i || j >= n + i) {
                    System.out.print(" ");
                } else {
                    System.out.print("*");
                }
            }
            System.out.println();
        }
    }
}
