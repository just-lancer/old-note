package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD12_intersection_union_substract {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：intersection & union & substract
    /*
    算子定义
    def intersection(other: RDD[T]): RDD[T]
    def union(other: RDD[T]): RDD[T]
    def subtract(other: RDD[T]): RDD[T]

    函数作用：
    求两个RDD的交集、并集、差集

    说明：对两个RDD求交、并、差集时，两个RDD的数据类型必须相同，即泛型类型相同。但两个RDD中数据数量可以不同

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4))
    val value1: RDD[Int] = context.makeRDD(List(3, 4, 5, 6))

    val value2: RDD[Int] = value.intersection(value1)
    val value3: RDD[Int] = value.union(value1)
    val value4: RDD[Int] = value.subtract(value1)

    value2.collect().foreach(print)
    println()
    value3.collect().foreach(print)
    println()
    value4.collect().foreach(print)


    context.stop()

  }

}
