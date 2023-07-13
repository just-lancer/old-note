package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD06_groupBy {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：groupBy
    /*
    2、groupBy算子定义
    def groupBy[K](f: T => K)(implicit kt: ClassTag[K]): RDD[(K, Iterable[T])]

    参数说明：
    f: T => K，函数类型参数，将RDD中的每个元素都进行函数操作，并得到结果

    函数运行过程及作用：groupBy会将具有相同函数结果的元素分到同一个组中。分组结果为一个二元组，
    第一个元素为函数的运算结果，第二个元素为分到同一个组中的所有RDD元素

    说明：不同于map、mapPartitions、mapPartitionWithIndex、glom等一些列算子，groupBy算子会对原分区的数据进行shuffle操作
    比如，当原来的分区少于分组后的分区数量。
    凡是有shuffle操作的地方，数据都会写入到磁盘中，用磁盘来进行交互

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4), 3)
    val value1: RDD[(Int, Iterable[Int])] = value.groupBy(ele => ele % 2)

    value1.collect().foreach(println)

    context.stop()

  }

}
