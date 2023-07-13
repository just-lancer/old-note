package knowledge;

public class ArraysToolsClass {
    /**
     * 数组工具类
     *  java.util.Arrays数组工具类，提供了许多对数组进行操作的静态方法，并且每种方法都有许多的重载形式
     *
     *  常用方法：
     *      查找：
     *      public static int binarySearch(int[] a, int key):利用二分查找算法，查找数组a中是否存在key，若存在则返回key第一次出现的索引；若不存在，则返回-1
     *
     *      排序：
     *      public static void sort(int[] a):在数组a的基础上直接进行排序，默认按照从小到大的顺序进行
     *      public static void sort(int[] a, int beginIndex, int endIndex):在a的基础上，对[beginIndex, endIndex)范围内的元素按从小到大进行排序
     *
     *      复制：
     *      public static void copyOf(int[] a, int newLength):根据数组a，复制一个长度为newLength的新数组，并返回新数组
     *      public static void copyOfRangeOf(int[] a, int from, int to):将数组a中，索引范围为[from, to)的元素复制，并返回该复制的数组
     *
     *      填充：
     *      static void fill(int[] a, int val):用val填充整个数组a
     *      static void fill(int[] a, int fromIndex, int toIndex, int val):在数组a的基础上，用val填充数组a索引范围为[fromIndex, toIndex)的部分
     *
     *      其他：
     *      static boolean equals(int[] a, int[] b):判断两个数组的长度和元素是否完全相同
     *      ststic String toString(int[] a):将数组a的元素，拼接为一个字符串，并返回
     *
     */
}
