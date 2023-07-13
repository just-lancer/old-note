package arraysexercise;

import java.util.Arrays;
import java.util.Scanner;

public class ArraysExercise07_2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("请输入一个正整数:");
		int num = sc.nextInt();
		
		//创建一个n*n的二维数组，并对每个元素进行随机赋值
		int arr[][] = ArraysTools.creatRandomArrays(num, num);
		
		// 设置初始化函数参数
		int i = 0;
		int j = 0;
		int m = arr.length;
		int n = arr[0].length;
		int count = 1;
		int p = 0;
		
		int arrResult[][] = practice2(arr, i, j, m, n, count, p);
		
		// 打印输出数组
		for(int k = 0; k < arrResult.length; k++){
			System.out.println(Arrays.toString(arrResult[k]));
		}
		
	}
	
	public static int[][] practice2(int[][] arr, int i, int j, int m, int n, int count, int p){
		// arr：二维数组
		// i、j：行索引和列索引
		// m、n：行索引和列索引行走的步数
		// count：赋值的元素
		// p：-1的指数，用于控制索引行走的方向
		if(count == arr.length * arr[0].length + 1){
			return arr;
		}
		
		int y = 0; // y循环的次数，也就是用于控制列行走的步数
		while (y < n){
			arr[i][j] = count;
			y++;
			count++;
			j = j + (int)(Math.pow(-1, p));
		}
		// 此时，i = 0, j = n, 并且右上角的元素arr[0][n-1]已经赋值了
		i = i + (int)(Math.pow(-1, p));
		j = j - (int)(Math.pow(-1, p));
		m--;
//		System.out.println("i:" + i + "; j:" + j + "; m:" + m + "; n:" + n);
		
		int x = 0; // y循环的次数，也就是用于控制列行走的步数
		while (x < m){
			arr[i][j] = count;
			x++;
			count++;
			i = i + (int)(Math.pow(-1, p));
		}
		// 此时，x=0,i=1,x=1,i=2,x=2,i=3
		i = i - (int)(Math.pow(-1, p));
		j = j - (int)(Math.pow(-1, p));
		n--;
//		System.out.println("i:" + i + "j:" + j + "; m:" + m + "; n:" + n );
		
		p++;//换方向了
		
		practice2(arr, i, j, m, n, count, p);
		return arr;
		
	}
}
