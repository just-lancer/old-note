package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD19_combineByKey {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：combineByKey
    /*
    算子定义
    def combineByKey[C](createCombiner: V => C,
                        mergeValue: (C, V) => C,
                        mergeCombiners: (C, C) => C): RDD[(K, C)]


    函数作用：是reduceByKey、aggregateByKey、flodByKey等函数的底层函数，最通用的函数

    参数说明：
    createCombiner: V => C，将RDD分区中的第一个数据进行转换，转换成所需要的数据格式，这样就不需要额外指定初始值
    mergeValue: (C, V) => C，分区内数据的计算规则
    mergeCombiners: (C, C) => C): RDD[(K, C)]，分区间数据的计算规则
     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)


    context.stop()
    func()

  }

  def func(): Unit = {
    // 对具有相同key的value求平均值
    val exercise: SparkConf = new SparkConf().setMaster("local[*]").setAppName("exercise")
    val context = new SparkContext(exercise)

    val value: RDD[(String, Int)] = context.makeRDD(List(("a", 1), ("a", 2), ("a", 3), ("b", 4), ("b", 5)), 2)

    val value1: RDD[(String, (Int, Int))] = value.combineByKey( // 元组第一个值表示value求和，第二个值表示数据量n
      first => {
        (first, 1)
      },
      (tup, v) => {
        (tup._1 + v, tup._2 + 1)
      },
      (tup1, tup2) => {
        (tup1._1 + tup2._1, tup1._2 + tup2._2)
      }
    )

    val value2: RDD[(String, Int)] = value1.mapValues(tup => {
      tup._1 / tup._2
    })

    value2.collect().foreach(println)

    context.stop()

  }

}
