package arraysexercise;

import java.util.Scanner;

public class ArraysExercise07_3 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("输入一个数字");
		int len = scanner.nextInt();
		int[][] arr = new int[len][len];

		int s = len * len;
		/*
		 * k = 1:向右 k = 2:向下 k = 3:向左 k = 4:向上
		 */
		int k = 1;
		int i = 0, j = 0;
		for (int m = 1; m <= s; m++) {
			if (k == 1) {
				if (j < len && arr[i][j] == 0) {
					arr[i][j++] = m;
				} else {
					k = 2;
					i++;
					j--;
					m--;
				}
			} else if (k == 2) {
				if (i < len && arr[i][j] == 0) {
					arr[i++][j] = m;
				} else {
					k = 3;
					i--;
					j--;
					m--;
				}
			} else if (k == 3) {
				if (j >= 0 && arr[i][j] == 0) {
					arr[i][j--] = m;
				} else {
					k = 4;
					i--;
					j++;
					m--;
				}
			} else if (k == 4) {
				if (i >= 0 && arr[i][j] == 0) {
					arr[i--][j] = m;
				} else {
					k = 1;
					i++;
					j++;
					m--;
				}
			}
		}
	}
}
