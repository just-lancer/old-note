package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD02_mapPartitions {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：mapPartitions
    /*
    2、mapPartitions算子定义
      def mapPartitions[U: ClassTag](f: Iterator[T] => Iterator[U], preservesPartitioning: Boolean = false): RDD[U]

      mapPartitions有两个参数，第一个参数是f: Iterator[T] => Iterator[U]函数类型，作用是将一个输入的迭代器转换成另一个迭代器，并输出
      第二个参数是preservesPartitioning: Boolean = false，具有默认值

      说明一：mapPartitions一次获取的是一个分区的数据，并用迭代器对象封装了该分区的数据，所以在进行函数映射操作时，要用迭代器进行映射

      说明：不同与map算子，mapPartitions算子会将一个分区的数据全部拿到后，才会依次对数据进行f: Iterator[T] => Iterator[U]函数的映射操作，
      这也是为什么mapPartitions的函数参数是将迭代器转换成另一个迭代器

      mapPartitions算子的效率高于map算子，但会长时间占据内存，当内存计算资源不足时，会导致任务执行失败

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4))

    val value1: RDD[Int] = value.mapPartitions(
      iterator => {
        iterator.map(elem => elem * 2)
      }
    )

    value1.collect().foreach(println)

    context.stop()

  }

}
