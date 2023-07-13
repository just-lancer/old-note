package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark_Introduce_Case {
  def main(args: Array[String]): Unit = {
    // spark入门案例
    // word count案例
    // 读取一个文件，统计文件中每个单词出现的次数

    // TODO 为了使用spark计算引擎，首先需要创建spark环境
    //      创建spark环境可以这样理解：spark是一个服务，要想利用spark进行数据计算，那么首先需要连接到该服务上，就像连接MySQL服务一样

    // 创建spark连接配置
    val wordcount: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wordcount")

    // 创建spark连接。获取连接即获取一个连接对象，spark的连接对象称为上下文对象，即Context
    val context: SparkContext = new SparkContext(wordcount)

    // 利用spark连接对象可以读取文件，可以利用spark进行数据计算

    // 1、读取文件，按行获取数据
    val value: RDD[String] = context.textFile("E:\\IntelliJ_IDEA_workplace\\Blossom07_Spark\\data/word.txt")

    // 2、对获取的每行数据进行扁平化，分解为一个一个的单词
    val value1: RDD[String] = value.flatMap(_.split(" "))

    // 3、对单词进行分组
    val value2: RDD[(String, Iterable[String])] = value1.groupBy(word => word)

    // 4、对分组后的数据进行聚合，直接判断分组后，value字段的大小即可
    val value3: RDD[(String, Int)] = value2.map(element => (element._1, element._2.size))

    // 5、计算逻辑已经准备好，现在可以开始执行计算，并将计算结果打印输出到控制台
    val tuples: Array[(String, Int)] = value3.collect()
    tuples.foreach(element => println(element))

    // TODO spark处理数据完成后，需要关闭spark连接资源
    val unit: Unit = context.stop()
  }

}
