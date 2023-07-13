package RDD_Operator

import org.apache.spark.{SparkConf, SparkContext}

object RDD21_join {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：join
    /*
    算子定义
    对于key-value类型数据的连接操作
    内连接：def join[W](other: RDD[(K, W)]): RDD[(K, (V, W))]
    左外连接：def leftOuterJoin[W](other: RDD[(K, W)]): RDD[(K, (V, Option[W]))]
    右外连接：def rightOuterJoin[W](other: RDD[(K, W)]): RDD[(K, (V, Option[W]))]

    函数作用：对具有相同key的value进行连接，连接的结果仍然是一个Key-value类型的数据，其中key为两个key-value数据的key（它们具有相同的key），
    value为一个二元组，由两个key-value类型数据的value构成

    对于join算子，相当于SQL语句中的内连接，即两个RDD中每个key-value都会进行匹配，每当出现相同的key，那么就会得到一条结果
    对于leftOuterJoin，相当于左外连接
    对于rightOuterJoin，相当于右外连接

    join的执行逻辑是，对两个RDD进行了一次双重遍历，先遍历调用算子的RDD的第一个元素，然后用这个元素去匹配第二个RDD的所有数据，匹配成功，那么生成一条数据
    遍历完成后，遍历第二个元素，直到遍历完所有的元素

    参数说明：
     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)


    context.stop()

  }

}
