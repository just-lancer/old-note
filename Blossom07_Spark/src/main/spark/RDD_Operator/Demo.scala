package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Demo {
  def main(args: Array[String]): Unit = {
    // 创建spark连接对象
    val exercise: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Demo")
    val context = new SparkContext(exercise)

    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4))

    value.countByValue()


    // 关闭连接资源
    context.stop()

  }


}
