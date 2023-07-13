package arraysexercise;

import java.util.Arrays;

public class ArraysExercise06 {
	public static void main(String[] args) {
		// 定义一个4行4列的二维数组，逐个从键盘输入值，然后将第1行和第4行的数据进行交换，将第2行和第3行的数据进行交换
		// 给定一个m行n列的二维数组，给定一组正整数i和j，那么交换第i行和第j列，i小于m，j小于n
		practice(5,5,0,2,1,2);
	}
	
	// m、n分别代表二维数组的行和列，i和j分别代表需要交换的两行，x和y分别代表需要交换的列。
	// 说明：若不想交换行，只需令i或者j等于0；若不想交换列，只需要零x或者y等于0
	public static void practice(int m, int n, int i, int j, int x, int y){
		int arr[][] = ArraysTools.creatRandomArrays(m, n);
		for(int count = 0; count < arr.length; count++){
			System.out.println(Arrays.toString(arr[count]));
		}
		//换行
		if(i > 0 && j > 0){
			i--;
			j--;
			int temp[] = new int[m];
			temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}
		
		//换列
		if(x > 0 && y > 0){
			x--;
			y--;
			for(int count = 0; count < arr.length; count++){
				int temp = arr[count][x];
				arr[count][x] = arr[count][y];
				arr[count][y] = temp;
			}
		}
		
		System.out.println();
		for(int count = 0; count < arr.length; count++){
			System.out.println(Arrays.toString(arr[count]));
		}
	}

}
