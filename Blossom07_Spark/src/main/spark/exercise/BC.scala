package exercise

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object BC {
  // 广播变量的使用
  // 广播变量也和flink中的广播流相同，会分发到每个Executor
  // 广播变量是一个只读变量

  def main(args: Array[String]): Unit = {
    // 需求：利用广播变量实现join

    // 创建Spark配置对象
    val accConf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("ACCExercise")

    // 创建Spark上下文环境
    val context = new SparkContext(accConf)

    // 创建RDD对象
    val rdd1: RDD[(String, Int)] = context.makeRDD(List("a" -> 2, "b" -> 3, "c" -> 1, "d" -> 5, "e" -> 7))
    val bcValue: List[(String, Int)] = List("b" -> 99, "c" -> 4, "e" -> 20, "a" -> 10)

    // 申明广播变量
    val bc: Broadcast[List[(String, Int)]] = context.broadcast(bcValue)
    // 现在bc就是一个可以直接使用的广播变量了

    // RDD处理逻辑
    val value: RDD[(String, (Int, Int))] = rdd1.map(
      tuple1 => {
        // 使用广播变量
        // 将广播变量转换成对应的RDD，以便调用其方法
        val bcRDD: List[(String, Int)] = bc.value

        // 定义一个值，用于构建返回的结果
        var bcSecondValue = 0


        // 遍历RDD
        // 当二元元组的第一个元素匹配时，合并结果
        // 如果不匹配，什么都不做，直接过滤
        for (tuple2 <- bcRDD) {
          if (tuple2._1 == tuple1._1) {
            bcSecondValue = tuple2._2
          }
        }

        // 拼接结果
        (tuple1._1, (tuple1._2, bcSecondValue))
      }
    )

    value.collect().foreach(println)

    // 关闭资源
    context.stop()

  }
}
