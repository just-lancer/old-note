package arraysexercise;

public class ArraysTools {
	
	// 给定一个正整数n，生成一个元素为0~1000的随机整数数组
	public static int[] creatRandomArrays(int n){
		int arr[] = new int[n];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int)(Math.random() * 1000);
		}
		return arr;
	}
	
	// 给定一组正整数m和n，生成一个m行n列的二维数组，数组元素为0~1000的随机整数
	public static int[][] creatRandomArrays(int m, int n){
		int arr[][] = new int[m][n];
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < arr[i].length; j++){
				arr[i][j] = (int)(Math.random() * 1000);
			}
		}
		return arr;
	}
}

