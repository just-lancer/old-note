package RDD_Operator

import org.apache.spark.{SparkConf, SparkContext}

object RDD11_sortBy {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：sortBy
    /*
    sortBy算子定义
    def sortBy[K](f: (T) => K, ascending: Boolean = true, numPartitions: Int = this.partitions.length)
        (implicit ord: Ordering[K], ctag: ClassTag[K]): RDD[T]


    参数说明：
    f: T => K，函数参数，用于指定排序字段或依据
    ascending: Boolean = true，Boolean类型，用于指定排序方式，升序还是降序排列，默认值为true，表示默认升序排列
    numPartitions: Int = this.partitions.length，Int类型，用于指定排序后，RDD的分区数量，默认值表示分区数量不变

    函数运行过程及作用：sortBy算子会将RDD中的每条数据都进行函数运算f: T => K，并利用其结果，进行排序

    说明：sortBy算子，默认是存在shuffle操作，比如，6，5，4，3，2，1六个数存储在2各分区内，第一个分区数据为6，5，4
    第二个分区数据为3，2，1，那么升序排序后，第一个分区的数据为1，2，3，第二个分区的数据为4，5，6。当然，也可以重新
    设定分区数量


     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    context.stop()

  }

}
