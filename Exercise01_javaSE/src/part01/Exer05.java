package part01;

public class Exer05 {
    // 判断101-200之间有多少个素数，并输出所有素数
    public static void main(String[] args) {
        int count = 0;
        for (int i = 101; i < 201; i++) {
            if (i % 2 == 0) {
                continue;
            } else {
                for (int j = 3; j <= (int) (Math.sqrt(i)); j++) {
                    if (i % j == 0) {
                        count++;
                        System.out.println(i);
                        break;
                    } else {
                        continue;
                    }
                }
            }
        }

        System.out.println("count = " + count);
    }
}
