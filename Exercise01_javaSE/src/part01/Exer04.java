package part01;

public class Exer04 {
    public static void main(String[] args) {
        String s = func("abc");
        System.out.println(s);
    }

    // 将一个字符串反转，将新的字符串返回
    public static String func(String str) {
        if (str == null) {
            return str;
        } else if (str.length() == 1) {
            return str;
        } else {
            String other = "";
            for (int i = str.length() - 1; i >= 0; i--) {
                other = other + str.charAt(i);
            }
            return other;
        }
    }
}
