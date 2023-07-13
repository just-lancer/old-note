package RDD_Operator

import org.apache.spark.{SparkConf, SparkContext}

object RDD22_cogroup {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：cogroup
    /*
    算子定义
    def cogroup[W](other: RDD[(K, W)]): RDD[(K, (Iterable[V], Iterable[W]))]

    函数作用：connect + group
    分组连接，对两个key-value类型的RDD数据，先在每个RDD内部，对具有相同key的数据进行分组，分组结果为一个
    key-value，其中value是右value构成的一个迭代器；随后对两个已分组的RDD数据进行连接，相同key的value连接成
    一个二元组，其值由两个分组后的迭代器构成

    参数说明：
     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)


    context.stop()

  }

}
