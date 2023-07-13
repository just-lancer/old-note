package arraysexercise;

import java.util.Arrays;

public class ArraysExercise05 {
	public static void main(String[] args) {
		// 有n个整数，使其前面各数顺序向后移m个位置，最后m个数变成最前面的m个数
		int arr[] = ArraysTools.creatRandomArrays(20);
		int lastIndex = 8;
		//在此不做m必须小于n的判断，默认输入都是没有问题的
		
		System.out.println(Arrays.toString(arr));
		int arrOhter[] = practice(arr, lastIndex);
		System.out.println(Arrays.toString(arrOhter));
	}
	
	public static int[] practice(int[] arr, int m){
		for(int j = arr.length - m - 1; j >= 0; j--){
			for(int i = j; i < m + j; i++){
				int temp = arr[i];
				arr[i] = arr[i+1];
				arr[i+1] = temp;
			}
		}
		return arr;
	}
}
