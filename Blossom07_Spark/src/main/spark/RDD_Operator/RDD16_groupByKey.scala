package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD16_groupByKey {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：groupByKey
    /*
    算子定义
    def groupByKey(): RDD[(K, Iterable[V])]
    def groupByKey(numPartitions: Int): RDD[(K, Iterable[V])]
    def groupByKey(partitioner: Partitioner): RDD[(K, Iterable[V])]

    函数作用：对于key-value类型的RDD，将具有相同key的value分到同一个组中

    groupBy算子与groupByKey算子的区别：
    都是进行分组，区别在于，① groupBy算子需要指定分组方式，而groupByKey
    明确以key-value数据中的key进行分组；② 二者的返回值二元组的第二个数据不同，groupBy是将具有相同特征的数据
    整个都放在一起，形成迭代器，而groupByKey是将具有相同key的value，放在一起，形成一个迭代器。

    groupByKey和reduceByKey的区别：
    groupByKey只对key-value类型的数据进行分组，对于多分区的数据而言，由于各分区并行执行，因此在得到最终的结果之前，
    已完成分组计算的分区还需要等待未完成计算分区的执行，当分区分组数据过大时，可能会导致内存溢出，因此已完成分组计算
    的分区数据将会落盘以等待其他分区，即shuffle操作

    reduceByKey对key-value类型的数据进行分组后，还会对数据进行聚合，对于多分区而言，同样由于分区间并行执行，因此在
    得到最终的分组结果之前，已完成分组计算的分区还需要等待未完成分区计算分区的执行，但不同于groupByKey，reduceByKey
    在分区数据分组结束后，可以在分区内进行数据聚合，并将聚合的结果落盘，以减少磁盘IO操作，提高效率。

    因此，进行相同的分组聚合操作，reduceByKey的效率远高于groupByKey，原因在于，reduceByKey可以在数据落盘前，进行
    预聚合，而groupByKey的聚合操作只能在全部分区的数据都已分好组之后进行

    对于reduceByKey还有一点需要说明：reduceByKey的预聚合和最终的聚合，其聚合的规则必须相同，即预聚合-分区内聚合和
    最终聚合-分区间聚合规则相同

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    val value: RDD[(String, Int)] = context.makeRDD(List(("a", 1), ("a", 2), ("a", 3), ("b", 4)), 2)

    val value1: RDD[(String, Iterable[Int])] = value.groupByKey()

    value1.collect().foreach(println)

    context.stop()

  }

}
