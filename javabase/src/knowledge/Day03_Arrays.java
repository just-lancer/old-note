package knowledge;
import java.lang.Math;

public class Day03_Arrays {
	public static void main(String[] args) {
		
	

	/**
	 * 数组学习
	 * 1、数组定义
	 *	数组是多个相同类型的数据按照一定的顺序排列的集合，并使用一个名字命名，并通过编号的方式对这些数据进行统一管路
	 *	
	 * 2、数组的常见概念：
	 *	数组名
	 *	索引或下标
	 *	数组元素
	 * 	数组长度
	 *	
	 * 3、数组的特点
	 *	> 数组的长度一旦确定，就不能修改
	 *	> 数组中的元素都是有序排列的
	 * 	> 数组本身是引用数据类型，数组的元素，既可以是基本数据类型，也可以是引用数据类型
	 *	> 创建数组会在内存中创建一整块连续的空间
	 *	
	 * 4、数组的分类：
	 *	按数组维度分：一维数组、二维数组......
	 *	按元素数据类型分：基本数据类型元素数组、引用数据类型元素的数组
	 */
	
	/**
	 * 一维数组
	 * 1、数组的声明，两种方式：以整数型数组为例
	 * 		int[] arr;
	 * 	或       int arr[];
	 * 
	 * 2、数组的初始化，两种方式
	 * 	静态初始化：int arr[] = {123, 456, 789}; 或 int arr[] = new int[]{123, 456, 789};
	 * 	动态初始化：int arr[] = new int[3]; // 此处的数字3用于指明数组初始化长度
	 * 			 arr[0] = 123;
	 * 			 arr[1] = 456;
	 * 			 arr[2] = 789;
	 * 
	 * 说明：两种声明方式、两种初始化方式，所以结合起来会有4中数组的声明初始化方式
	 * 	
	 * 3、数组的引用
	 * 	> 定义数组时，利用new关键字后，系统为数组分配内存空间，我们才能引用数组中的元素
	 *  > 数组元素的引用方式为：数组名[数组元素下标]
	 *  > 数组对象具有一个属性length，可以用于获得数组长度
	 *  
	 *  
	 * 4、数组元素的默认初始化值
	 * 	数组元素是引用数据类型，其元素相当于类的成员变量，因此数组一旦声明并在内存空间中分配空间，
	 * 	其每个元素都会按照成员变量的方式被隐式初始化。
	 * 	根据元素的数据类型不同，元素的默认初始化值也不同
	 * 		对于基本数据类型：
	 * 			byte、short、int --> 0
	 * 			long --> 0L
	 * 			float --> 0.0F
	 * 			double --> 0.0
	 * 			char --> 0或者'\u0000'
	 * 			boolean --> false
	 * 
	 * 		对于引用数据类型，默认初始化值为null
	 * 
	 * 5、一维数组元素的遍历
	 * 6、一维数组的内存解析
	 * 	
	 */
	
	
	
	/**
	 * 二维数组
	 * 1、二维数组的声明：两种方式：
	 * 		int arr[][];
	 * 	或	int[][] arr;
	 * 
	 * 2、二维数组的初始化，两种方式：静态初始化和动态初始化
	 * 	静态初始化：
	 * 		int arr[][] = new int[][]{{1}, {23,45}, {678,8910}};
	 *  或	int arr[][] = {{1}, {23,45}, {678,8910}};
	 *  
	 *  动态初始化：
	 *  	> int arr[][] = new int[2][3];//声明了一个两行三列的数组
	 *  	> int arr[][] = new int[3][];// 声明了一个包含三个一维整数数组的二维数组
	 *		
	 *	注意：① Java中，二维数组中每个一维数组所包含元素的个数都可以不一样，即多维数组不必都是规则矩阵形式
	 *		② 声明二维数组的时候，必须先声明行，后声明列；声明了行之后可以不用声明列
	 *			例如：int arr[][] = new int[][3];// 这种写法是错误的
	 *		③ 特殊写法：int x[],y[]; // x是一维数组，y是二维数组
	 * 
	 * 3、数组的引用
	 * 4、数组的遍历
	 * 5、数组的内存结构
	 */
	
	/**
	 * 数组的常见算法
	 * 1、对于数值型数组
	 * 		最值，求和、平均数等
	 * 2、数组的赋值与复制
	 * 		array2 = array1;
	 * 		赋值（内存理解）：讲array1保存的数组的地址值赋给了arrays2，使得arrays1和arrays2共同指向堆空间中的同一个数组实体
	 * 
	 * 		复制：
	 * 		for (int i = 0; i < arrays1.length; i++){
	 * 				arrays2[i] = arrays1[i];
	 * 		}
	 * 
	 */
	
	
	// 数组元素的反转
	int arr[] = new int[10];
	//1~100随机产生10个数
	for(int i = 0; i < arr.length; i++){
		arr[i] = (int)(Math.random()*101);
	}
	
	for(int i = 0; i < arr.length; i++){
		System.out.print(arr[i] + "\t");
	}
	System.out.println();
	// 定义两个指针
	
	for (int i = 0, j = arr.length -1;i < j; i++, j--){
		int temp = 0;
		temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	for (int i = 0; i < arr.length; i++){
		System.out.print(arr[i] + "\t");
	}
	System.out.println();
	
	
	// 数组指定元素的查找
	// 线性查找
	// 二分查找
	// 数组的排序算法
	
	// 冒泡排序
	// 对数组arr进行从小到大的排序
	for(int j = 0; j < arr.length; j++){// j表示已经拍好顺序的元素的个数
		for(int i = 0; i < arr.length - 1 - j; i++){// i表示需要排序元素当前还需和多少个元素进行排序
			if (arr[i] > arr[i+1]){
				int temp = 0;
				temp = arr[i];
				arr[i] = arr[i+1];
				arr[i+1] = temp;
			}
		
		}
	}
	
	for (int i = 0; i < arr.length; i++){
		System.out.print(arr[i] + "\t");
	}
	
	/**
	 * Arrays工具类
	 * java。util.Arrays类即为操作数据的工具类，包含了用来操作数组（比如排序、搜索）的各种方法
	 * 常用、常见的方法
	 * 
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}	
	
}
