package note

import org.apache.spark.{SparkConf, SparkContext}

object Create_SparkConnect {
  def main(args: Array[String]): Unit = {
    // 创建spark配置对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("demo")
    // 创建Spark执行环境，即上下文环境
    val context = new SparkContext(conf)
  }
}
