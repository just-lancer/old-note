package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD04_flatMap {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：flatMap
    /*
    2、flatMap算子定义
      def flatMap[U: ClassTag](f: T => TraversableOnce[U]): RDD[U]

    参数说明：
      f: T => TraversableOnce[U]，函数参数，对RDD中的数据的一种操作，返回结果的数据类型必须是能够进行扁平化的数据类型，集合

    函数运行过程及作用：首先，flatMap会将RDD中的每个元素进行函数操作(f: T => TraversableOnce[U])，所得到的结果必须是能够扁平化的
                       随后，对所得到的结果进行扁平化，即拆分成一个个元素

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    val value: RDD[Any] = context.makeRDD(List(List(1, 2), List(3, 4), 5))

    val value1: RDD[Int] = value.flatMap(
      ele => {
        ele match {
          case list: List[Int] => list
          case int: Int => List(int)
          case _ => Nil
        }
      }
    )

    value1.collect().foreach(println)

    context.stop()

  }

}
