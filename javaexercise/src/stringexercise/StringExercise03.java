package stringexercise;

import java.util.Scanner;

public class StringExercise03 {
	public static void main(String[] args) {
		// 统计一个字符串在另一个字符串中出现的次数
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入一个字符串：");
		String str1 = sc.next();
		System.out.println("请输入另一个字符串：");
		String str2 = sc.next();

		int res = practice(str1, str2);
		System.out.println(res);
	}

	// 判断字符串str2在str1中出现的次数
	public static int practice(String str1, String str2) {
		int count = 0;
		if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
			return 0;
		} else {
			if (str1.length() < str2.length()) {
				return 0;
			} else if (str1.length() == str2.length()) {
				if (str1.equals(str2)) {
					return 1;
				} else {
					return 0;
				}
			} else {
				for (int i = 0; i <= str1.length() - str2.length(); i++) {
					if (str1.charAt(i) == str2.charAt(0)) {
						if (str2.equals(str1.substring(i, str2.length() + i))) {
							count++;
						} else {
							continue;
						}
					} else {
						continue;
					}
				}
				return count;
			}
		}

	}
}
