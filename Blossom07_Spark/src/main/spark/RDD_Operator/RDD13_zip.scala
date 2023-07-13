package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD13_zip {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：zip
    /*
    算子定义
    def zip[U: ClassTag](other: RDD[U]): RDD[(T, U)]

    函数作用：
    对两个RDD数据进行拉链操作

    说明：对两个RDD进行拉链操作时，两个RDD的数据类型可以不同。但两个RDD中数据数量必须相同更进一步讲，必须保证
    两个RDD的分区数量一样，每个分区中数据数量也是一样
    这是区别Scala中集合的拉链操作

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4))
    val value1: RDD[Int] = context.makeRDD(List(3, 4, 5, 6))

    val value2: RDD[(Int, Int)] = value.zip(value1)

    value2.collect().foreach(println)

    context.stop()

  }

}
