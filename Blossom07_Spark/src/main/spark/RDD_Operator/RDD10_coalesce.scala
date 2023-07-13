package RDD_Operator

import org.apache.spark.{SparkConf, SparkContext}

object RDD10_coalesce {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：coalesce
    /*
    coalesce算子定义
    def coalesce(numPartitions: Int, shuffle: Boolean = false,
           partitionCoalescer: Option[PartitionCoalescer] = Option.empty)
          (implicit ord: Ordering[T] = null): RDD[T]

    参数说明：
    numPartitions: Int，表示重分区后，分区数量是多少
    shuffle: Boolean，表示重分区时，是否进行重新洗牌，默认为false，表示不重新洗牌

    函数运行过程及作用：coalesce算子会对RDD进行重分区

    说明：coalesce算子能够对RDD的分区进行合并，也能够对RDD的分区进行扩展。当合并分区时，shuffle参数可以为false，
    此时，表示不进行重分区，那么合并分区时，会将一个分区的数据整体并入到另一个分区中(按分区编号，后向并入)，因此，
    可能会产生数据倾斜。当shuffle参数为true时，此时会进行重分区，即数据打乱重组，会使得各个分区的数据较为均衡。
    当扩展分区时，shuffle参数不能为false，因为如果扩展分区时，不进行数据shuffle，那么多出来的分区不会分得数据，
    分区的存在就是无意义的，因此，扩展分区会失败。

    为了使用方便，coalesce算子一般用作合并分区，而另一个算子repartition专门用作扩展分区，repartition算子底层也是
    调用了coalesce算子，并将shuffle参数设置为true

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    context.stop()

  }

}
