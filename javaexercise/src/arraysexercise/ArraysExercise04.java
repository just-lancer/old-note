package arraysexercise;

import java.util.Arrays;
import java.util.Scanner;

public class ArraysExercise04 {
	// 输入数组，最大的与第一个元素交换，最小的与最后一个元素交换，输出数组。 
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("请输入一个大于1的正整数：");
		int n = sc.nextInt();
		int arr[] = ArraysTools.creatRandomArrays(n);
		
		int firstNum = arr[0];
		int lastNum = arr[arr.length - 1];
		
		if(firstNum < lastNum){
			int temp = firstNum;
			firstNum = lastNum;
			lastNum = temp;
		}
		
		for (int i = 1; i < arr.length - 1; i++) {
			if (arr[i] >= firstNum){
				int temp = arr[i];
				arr[i] = firstNum;
				firstNum = temp;
			}
			else if(arr[i] <= lastNum){
				int temp = arr[i];
				arr[i] = lastNum;
				lastNum = arr[i];
			}
		}
		
		System.out.println(Arrays.toString(arr));
	}
	
	
}
