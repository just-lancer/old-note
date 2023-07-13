package RDD_Operator

import org.apache.spark.{SparkConf, SparkContext}

object RDD14_partitionBy {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：partitionBy
    /*
    算子定义
    def partitionBy(partitioner: Partitioner): RDD[(K, V)]

    函数作用：将key-value类型的数据进行重分区
    partitioner: Partitioner，分区器对象，用于指定key-value类型数据分区的规则

    spark框架自带一种分区器HashPartitioner，该分区器按照key-value类型数据的key的hash值对分区数量取余进行数据分区
        对于key是null的数据，全部放在0号分区
    自定义分区器：需要自定义类继承抽象类partitioner，并重写其抽象方法

    说明：单值类型集合的coalesce和repartition算子，是对分区的数量进行重新设置，并将数据重新洗牌后分配；
    而key-value类型的集合的partitionBy是只对数据进行重新分配，而分区的数量不变

    说明：当一个key-value类型RDD经过两次重分区，并且两次重分区的分区器相同（利用==进行判断），以及分区数相同，
    那么会自动忽略第二次的重分区，即不会产生新的RDD对象

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)


    context.stop()

  }

}
