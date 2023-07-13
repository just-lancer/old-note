package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD09_distinct {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：distinct
    /*
    2、distinct算子定义
    def distinct()(implicit ord: Ordering[T] = null): RDD[T]
    def distinct(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T]

    参数说明：
    distinct算子具有两个重载的方法，其中参数numPartitions: Int表示并行度，即启用几个Task执行任务

    函数运行过程及作用：distinct函数会对RDD中重复的数据进行去重

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 3), 2)

    val value1: RDD[Int] = value.distinct(0)

    value1.collect().foreach(println)
    context.stop()

  }

}
