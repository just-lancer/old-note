package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD03_mapPartitionsWithIndex {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：mapPartitionsWithIndex
    /*
    2、mapPartitionsWithIndex算子定义
    def mapPartitionsWithIndex[U: ClassTag](f: (Int, Iterator[T]) => Iterator[U], preservesPartitioning: Boolean = false): RDD[U]

    参数说明：
      f: (Int, Iterator[T]) => Iterator[U]，函数参数，Int类型参数表示数据的分区编号，Iterator[T]表示该分区的全部数据的迭代器
      preservesPartitioning: Boolean = false
      mapPartitionsWithIndex算子的作用与mapPartitions相同，不同点在于，mapPartitionsWithIndex算子能对指定的分区数据进行操作

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4), 3)

    val value1: RDD[(Int, Int)] = value.mapPartitionsWithIndex(
      (index, iterator) => {
        iterator.map((_, index))
      }
    )

    value1.collect().foreach(println)


    context.stop()

  }

}
