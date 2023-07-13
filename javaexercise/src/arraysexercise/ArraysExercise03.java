package arraysexercise;

import java.util.Arrays;
import java.util.Scanner;

public class ArraysExercise03 {
	// 给定一个正整数n，返回斐波那契数列的前n项
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("请输入一个正整数：");
		int n = sc.nextInt();
		
		if(n <= 2){
			int arr[] = new int[n];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = 1;
			}
			System.out.println(Arrays.toString(arr));
		}
		
		else{
			int arr[] = new int[n];
			arr[0] = 1;
			arr[1] = 1;
			// 斐波那契数列：f(n+2) = f(n+1) + f(n),n>=0
			for (int i = 2; i < arr.length; i++) {
				arr[i] = arr[i-1] + arr[i-2];
			}
			
			System.out.println(Arrays.toString(arr));
		}
	}
}
