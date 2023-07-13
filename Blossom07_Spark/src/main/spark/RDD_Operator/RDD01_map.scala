package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD01_map {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：map
    /*
    1、map算子定义
      def map[U: ClassTag](f: T => U): RDD[U]

      map算子具有一个参数，是函数类型

      说明1：map算子会对调用该算子的RDD的每一条数据依次进行函数f: T => U运算，得到的结果会构成一个新的RDD，作为map算子的返回值

      说明2：map算子对单个分区数据的计算是有序的，即一个分区中的一条数据执行完全部的map操作后，才会对下一条数据进行map操作
            对于分区间的数据，是无序的，即各分区数据并行计算
            总体而言，map的计算是来一个计算一个，效率较低，但不会长时间占据内存

      说明3：对于key-value类型的数据，如果需要进行映射操作，并且映射过程中，key不变，只对value进行映射，那么可以使用
            mapValues()算子
     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4))

    val value1: RDD[Int] = value.map(_ * 2)
    value1.collect().foreach(println)

    context.stop()

  }

}
