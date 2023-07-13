package RDD_Operator

import org.apache.spark.{SparkConf, SparkContext}

object RDD18_flodByKey {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：flodByKey
    /*
    算子定义
    def foldByKey(zeroValue: V)(func: (V, V) => V): RDD[(K, V)]

    函数作用：当分区内计算规则和分区间计算规则相同时，aggregateByKey就可以简化为foldByKey

    参数说明：
    zeroValue：计算的初始值，即各分区内的key-value数据进行第一次两两计算时，另一个值就是zeroValue
                而分区间的两两计算，则是两个key-value的value进行计算，不需要额外添加初始值

    func: (V, V) => V：分区内和分区间的计算规则

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)


    context.stop()

  }

}
