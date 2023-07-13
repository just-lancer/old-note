package arraysexercise;

import java.util.Arrays;

public class ArraysExercise02 {
	//面试题目：创建一个长度为6的int型数组，要求取值为1-30，同时元素值各不相同
	public static void main(String[] args) {
		int arr[] = new int[6];
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int)(Math.random()*6);
			for(int j = 0; j < i; j++){
				if(arr[i] == arr[j]){
					i--;
					break;
				}
			count++;
			}
		}
		
		System.out.println(Arrays.toString(arr));
		System.out.println(count);
	}
}
