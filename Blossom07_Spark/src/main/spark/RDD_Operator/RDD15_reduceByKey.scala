package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD15_reduceByKey {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：reduceByKey
    /*
    算子定义
    def reduceByKey(func: (V, V) => V): RDD[(K, V)]
    def reduceByKey(func: (V, V) => V, numPartitions: Int): RDD[(K, V)]

    函数作用：对于key-value类型的数据，reduceByKey算子会将具有相同key的value进行聚合，
    聚合规则由func: (V, V) => V进行指定，对于参数numPartitions表示并行度

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    val value: RDD[(String, Int)] = context.makeRDD(List(("a", 1), ("a", 2), ("a", 3), ("b", 4)), 2)

    val value1: RDD[(String, Int)] = value.reduceByKey(_ + _)

    value1.collect().foreach(println)

    context.stop()

  }

}
