package spark_supplement

object supplement04_partitioner {
  // RDD分区器

  /*
    Spark目前支持Hash分区和Range分区，和用户自定义分区。Hash分区为当前的默认分区。
    分区器直接决定了RDD中分区的个数、RDD中每条数据经过Shuffle后进入哪个分区，进而决定了Reduce的个数。

    说明：只有Key-Value类型的RDD才有分区器，非Key-Value类型的RDD分区的值是None
         每个RDD的分区ID范围：0 ~ (numPartitions - 1)，决定这个值是属于那个分区的。
   */

  /*
    1)	Hash分区：对于给定的key，计算其hashCode,并除以分区个数取余
    2)	Range分区：将一定范围内的数据映射到一个分区中，尽量保证每个分区数据均匀，而且分区间有序
    3)  自定义分区：自定义类继承partitioner类，并实现其抽象方法
          def numPartitions: Int  设置分区数量
          def getPartition(key: Any): Int   设置分区规则


    说明：自定义分区器的使用：在使用coalesce算子和repartition算子时，可以传入自定义分区器进行数据分区
    前提是，需要进行shuffle操作
   */

}


