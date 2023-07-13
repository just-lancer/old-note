import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object test {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[1]").setAppName("test")
    val sc = new SparkContext(conf)

    // 获取数据源
    val value1: RDD[String] = sc.makeRDD(List[String]("hello world", "hello spark"))

    val value2: RDD[String] = value1.flatMap(ele => ele.split(" "))

    val value3: RDD[(String, Int)] = value2.map(ele => (ele, 1))

    val value4: RDD[(String, Int)] = value3.reduceByKey((x, y) => x + y)

    value4.collect().foreach(print)

  }

}
