/**
 * Author: shaco
 * Date: 2022/8/20
 * Desc:
 */
public class Algorithm001_Sort {
    public static void main(String[] args) {
        // 冒泡排序
        int[] arr1 = new int[]{4, 0, 2, 5, 3, 100, 1, 14, 12, 13, 11};
        int[] res1 = func1(arr1);
        for (int i = 0; i <= res1.length - 1; i++) {
            System.out.print(res1[i] + "\t");
        }

        System.out.println();

        int[] arr2 = new int[]{4, 0, 2, 5, 3, 100, 1, 14, 12, 13, 11};
        int[] res2 = func2(arr2);
        for (int i = 0; i <= res2.length - 1; i++) {
            System.out.print(res2[i] + "\t");
        }

        System.out.println();

        int[] arr4 = new int[]{4, 0, 2, 5, 3, 100, 1, 14, 12, 13, 11};
        int[] res4 = func4(arr4, 0, arr4.length - 1);
        for (int i = 0; i <= res4.length - 1; i++) {
            System.out.print(res4[i] + "\t");
        }

    }

    // 冒泡排序1
    public static int[] func1(int[] arr) {
        int len = arr.length;
        for (int i = 0; i <= len - 1; i++) {
            for (int j = i + 1; j <= len - 1; j++) {
                // 升序排序
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    // 冒泡排序2
    public static int[] func2(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) { // i表示当前已经排序好了多少个数
            // 内层循环执行完一次，只是排序好一个数
            for (int j = 0; j < len - 1 - i; j++) {
                // 升序排序
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

        }
        return arr;
    }

    // 快速排序
    // 升序排序
    // {4, 0, 2, 5, 3, 100, 1, 14, 12, 13, 11}
    public static int[] func4(int[] arr, int startIndex, int endIndex) {
        // 递归跳出条件
        if (startIndex >= endIndex) {
            return arr;
        }

        int index = func3(arr, startIndex, endIndex);
        // 左边排序
        func4(arr, startIndex, index - 1);
        // 右边排序
        func4(arr, index + 1, endIndex);

        return arr;
    }

    // 这个方法调用一次，会对数组进行一次划分，并返回标准值的索引
    public static int func3(int[] arr, int startIndex, int endIndex) {
        // 选择基准
        int index = startIndex;
        int standard = arr[index];
        int left = startIndex;
        int right = endIndex;

        while (left < right) {
            while (left < right) {
                if (arr[right] > standard) {
                    right--;
                } else {
                    int temp = arr[right];
                    arr[right] = arr[index];
                    arr[index] = temp;
                    // 更新索引
                    index = right;
                    // 此时遇到了右边比标准值小的数，交换完位置后，要开始左边的比较
                    break;
                }
            }

            while (left < right) {
                if (arr[left] < standard) {
                    left++;
                } else {
                    int temp = arr[left];
                    arr[left] = arr[index];
                    arr[index] = temp;
                    // 更新索引
                    index = left;
                    // 遇到了左边比标准值大的数，交换完位置后，开始右边的比较
                    break;
                }
            }
        }

        // 此时，left = right
        arr[index] = standard;

        return index;
    }

} 
