package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Case01 {
  def main(args: Array[String]): Unit = {
    // 案例1：
    // 1)	数据准备
    //    agent.log：时间戳，省份，城市，用户，广告，中间字段使用空格分隔。
    // 2)	需求描述
    //    统计出每一个省份每个广告被点击数量排行的Top3

    // 1、创建spark连接对象
    val caseConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("case1")
    val context = new SparkContext(caseConf)

    // 2、读取文件数据，创建RDD对象
    val value: RDD[String] = context.textFile("Blossom07_Spark/data/agent.log")

    // 3、调用map函数进行转换
    val value1: RDD[((String, String), Int)] = value.map(
      line => {
        val strings = line.split(" ")
        ((strings(1), strings(4)), 1)
      }
    )

    // 4、分组聚合
    val value2: RDD[((String, String), Int)] = value1.reduceByKey((x, y) => x + y)

    // 5、将数据转换成想要的格式，(String, String, Int)
    val value3: RDD[(String, String, Int)] = value2.map {
      // 模式匹配
      case ((province, advertising), amount) => (province, advertising, amount)
    }

    // 6、对省份分组
    val value4: RDD[(String, Iterable[(String, String, Int)])] = value3.groupBy(tup => tup._1)

    // 7、对省份中的数据进行排序，并且获取前三
    val value5: RDD[(String, List[(String, String, Int)])] = value4.mapValues(
      iter => iter.toList.sortBy(tup => tup._3)(Ordering[Int].reverse).take(3)
    )

    // 执行，获得结果
    value5.collect().foreach(println)

    // 8、关闭连接
    context.stop()
  }

}
