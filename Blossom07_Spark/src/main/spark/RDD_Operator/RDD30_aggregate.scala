package RDD_Operator

object RDD30_aggregate {
  def main(args: Array[String]): Unit = {
    /*
    RDD行动算子

    aggregate算子

    函数声明：
    def aggregate[U: ClassTag](zeroValue: U)(seqOp: (U, T) => U, combOp: (U, U) => U): U

    函数作用：分区的数据通过初始值和分区内的数据进行聚合，然后再和初始值进行分区间的数据聚合

    说明：不同于转换算子aggregateByKey，行动算子aggregate的初始值在各个分区内会参与一次计算，在分区内计算完
    之后，还会参与一次分区间的计算
     */
  }

}
