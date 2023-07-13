package arraysexercise;

import java.util.Arrays;
import java.util.Scanner;

public class ArraysExercise07_1 {
	public static void main(String[] args) {
		// 给定一个正整数n，输出一个n行n列的逆时针旋转的螺旋数组
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入一个正整数：");
		int rows = sc.nextInt();
		int arrSource[][] = ArraysTools.creatRandomArrays(rows, rows);
		
		// 初始化元素，准备开始进行遍历
		int i = 0;
		int j = 0;
		int count = 1; 
		int p = 0;
		int n = 0;
		
		// 从此处开始进行方法一
		int arrResult1[][] = practice1(arrSource, i, j, count, p, n);
		
		// 在这里要对矩阵的中心的元素要进行判断，对于n*n矩阵，
		// 如果n是偶数，那么最内层也会是一个矩阵，为2*2矩阵
		// 如果n是奇数，那么最内存就是一个1*1矩阵，也就是最后一个元素
		// 该算法必须要对n为奇数时的情况进行判断，原因是每一层循环中，左上角，右上角，右下角，左下角的元素赋值都是在下一轮循环中进行的，
		// 对于n为偶数的情况，最后的2*2矩阵也就是最后四个元素一定能赋值，但是对于n为奇数的情况，最后一个元素是无法进行赋值的，原因就是
		// 当循环至此时，已经结束，没有下一轮循环了，所以对n为奇数时，要对矩阵的最中间的元素要单独进行判断一次
		if(rows % 2 != 0){
			arrResult1[rows/2][rows/2] = rows * rows;
		}
		
		for (int k = 0; k < arrResult1.length; k++) {
			System.out.println(Arrays.toString(arrResult1[k]));
		}

	}
	
	
	// 方法一：这个方法已经基本解决了这个问题，只是不是很完美。
	public static int[][] practice1(int[][] arr, int i, int j, int count, int p, int n){
		// 用于判断递归是否结束
		// 在此处 判断递归结束的的判断条件必须是用  >= ，原因是，n为奇数和偶数时，count的值不一样
		// 对于n为偶数时，递归结束时，count = arr.length * arr.length + 1，需要用 > 号
		// 对于n为奇数时，递归结束时，count = arr.length * arr.length，需要用=号
		// 而且必须在count的值达到arr.length * arr.length + 1或者arr.length * arr.length时结束循环，
		// 否则进入死循环，出StackOverflowError异常
		if (count >= arr.length * arr.length){
			return arr;
		}
		
		// 形参arr表示索要生成的二维数组
		// i表示行索引
		// j表示列索引
		// count表示arr元素的值
		// p是-1的指数，用于控制遍历的方向
		// n表示当前遍历的是第几层，从外到里，依次是第0曾，第1层，第2层...
		
		// 先进行行的遍历
		for(;j < arr.length - n - 1;){
			arr[i][j] = count;
			count++;
			j = j + (int)Math.pow(-1, p);
		}
		
		//再进行列的遍历
		for(; i < arr.length - n - 1;){
			arr[i][j] = count;
			count++;
			i = i + (int)(Math.pow(-1, p));
		}
		
		p++;
		
		for(;j > n;){
			arr[i][j] = count;
			count++;
			j = j + (int)Math.pow(-1, p);
		}
		
		for(;i > n;){
			arr[i][j] = count;
			count++;
			i = i + (int)(Math.pow(-1, p));
		}
		System.out.println(count);
		
		i++;
		j++;//为什么每次都只增加j，因为，每次都是从左上角对角线元素开始。而四次循环结束后，坐标会位于对角线元素同一行的左侧，所以只需要列++
		n++;
		p++;
//		System.out.println(i + "\t" + j + "\t" + n + "\t" + p);
		
		//要开始递归了
		practice1(arr, i, j, count, p, n);
		return arr;
	}
	
}
