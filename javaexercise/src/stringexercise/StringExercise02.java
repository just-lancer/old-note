package stringexercise;

import java.util.Scanner;

public class StringExercise02 {
	public static void main(String[] args) {
		// 反转字符串
		Scanner sc = new Scanner(System.in);
		System.out.print("请输入一个字符串：");
		String str = sc.next();

		String s = practice(str);
		System.out.println(s);
	}

	public static String practice(String s) {
		if (s == null) {
			return null;
		}

		if (s.length() <= 1) {
			return s;
		} else {
			char strChar[] = new char[s.length()];
			for (int i = s.length() - 1; i >= 0; i--) {
				strChar[s.length() - 1 - i] = s.charAt(i);
			}
			return new String(strChar);
		}
	}
}
