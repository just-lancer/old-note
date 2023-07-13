package stringexercise;

import java.util.Scanner;

public class StringExercise01 {

	public static void main(String[] args) {
		// 定义一个方法：统计指定字符串中，某个字串出现的次数
		Scanner sc = new Scanner(System.in);
		System.out.print("请输入一个字符串：");
		System.out.println();
		String str1 = sc.next();
		System.out.print("请输入一个字串");
		String str2 = sc.next();

		int result = practice(str1, str2);
		System.out.print(result);
	}

	public static int practice(String str1, String str2) {
		if (str1.length() < str2.length()) {
			return 0;
		} else if (str1.length() == str2.length()) {
			if (str1.equals(str2)) {
				return 1;
			} else
				return 0;
		} else {// 子串长度小于目标字符串长度
				// 利用截取方法，subString()
			int count = 0; // 子串出现的次数
			for (int i = 0; i <= str1.length() - str2.length(); i++) {
				if (str2.equals(str1.substring(i, str2.length() + i))) {
					count = count + 1;
				} else {
					continue;
				}
			}
			return count;
		}

	}
}