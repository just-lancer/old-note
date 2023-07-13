package RDD_Operator

object RDD35_foreach {
  def main(args: Array[String]): Unit = {
    /*
    RDD行动算子

    foreach算子

    函数声明：
    def foreach(f: T => Unit): Unit

    函数作用：分布式遍历RDD中的每一个元素，调用指定函数

    collect是在Driver端进行数据采集，因此采集的数据能够具有顺序型
    而foreach是分布式执行，其元素遍历操作是在excutor端进行，是并行执行的，所以数据会出现乱序


     */
  }

}
