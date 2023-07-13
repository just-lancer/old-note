package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD17_aggregateByKey {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：aggregateByKey
    /*
    算子定义
    def aggregateByKey[U: ClassTag](zeroValue: U)(seqOp: (U, V) => U, combOp: (U, U) => U): RDD[(K, U)]

    函数作用：对RDD数据根据不同的规则，进行分区内计算，和进行分区间计算

    参数说明：
    zeroValue: U，计算的初始值，即各分区内的key-value数据进行第一次两两计算时，另一个值就是zeroValue
                  而分区间的两两计算，则是两个key-value的value进行计算，不需要额外添加初始值
    seqOp: (U, V) => U：表示分区内的计算规则
    combOp: (U, U) => U：表示分区间的计算规则

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    val value: RDD[(String, Int)] = context.makeRDD(List(("a", 1), ("a", 2), ("a", 3), ("a", 4)), 2)

    val value1: RDD[(String, Int)] = value.aggregateByKey(0)((x, y) => math.max(x, y), (m, n) => m + n)

    value1.collect().foreach(print)

    context.stop()

    func()

  }

  def func(): Unit = {
    // 对具有相同key的value求平均值
    val exercise: SparkConf = new SparkConf().setMaster("local[*]").setAppName("exercise")
    val context = new SparkContext(exercise)

    val value: RDD[(String, Int)] = context.makeRDD(List(("a", 1), ("a", 2), ("a", 3), ("b", 4), ("b", 5)), 2)

    val value1: RDD[(String, (Int, Int))] = value.aggregateByKey((0, 0))( // 元组第一个值表示value求和，第二个值表示数据量n
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
