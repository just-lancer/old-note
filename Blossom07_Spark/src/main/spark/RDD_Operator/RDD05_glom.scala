package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD05_glom {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：glom
    /*
    2、glom算子定义
      def glom(): RDD[Array[T]]

    参数说明：

    函数运行过程及作用：将同一个分区的数据直接转换为相同类型的内存数组(Araay)进行处理，分区不变

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4), 2)
    val value1: RDD[Array[Int]] = value.glom()

    value1.collect().foreach(data => println(data.mkString(",")))

    context.stop()

    func()
    func1()

  }

  def func(): Unit = {
    // 分区间最大值求和
    val demo: SparkConf = new SparkConf().setMaster("local[*]").setAppName("demo")
    val context = new SparkContext(demo)

    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4), 2)

    val value1: RDD[Int] = value.mapPartitions(
      iter => {
        List(iter.max).iterator
      }
    )

    val sum: Double = value1.sum

    println(sum)

    value1.collect().foreach(println)

    context.stop()
  }

  def func1(): Unit = {
    val demo: SparkConf = new SparkConf().setMaster("local[*]").setAppName("demo")
    val context = new SparkContext(demo)

    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4), 1)
    val value1: RDD[Array[Int]] = value.glom()

    val value2: RDD[Int] = value1.map(_.max)

    println(value2.collect().sum)

    context.stop()
  }

}
