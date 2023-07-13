package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD07_filter {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：filter
    /*
    2、filter算子定义
    def filter(f: T => Boolean): RDD[T]

    参数说明：
    f: T => Boolean，函数类型参数，对RDD中的每条数据进行逻辑判断

    函数运行过程及作用：filter对RDD中的每条数据进行逻辑判断，并得到Boolean类型的值，当Boolean的值为true时，
    保留该条数据，当Boolean的值为false时，过滤掉该条数据

    说明：该算子也是按分区进行数据过滤的，因此可能会出现数据过滤后的分布不均衡（数据倾斜）

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4))

    val value1: RDD[Int] = value.filter(
      ele => {
        ele % 2 == 0 // 保留偶数元素
      }
    )

    value1.collect().foreach(println)

    context.stop()

  }

}
