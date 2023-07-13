package RDD_Operator

object RDD32_countByValue {
  def main(args: Array[String]): Unit = {
    /*
    RDD行动算子

    countByValue算子

    函数声明：
    def countByValue()(implicit ord: Ordering[T] = null): Map[T, Long]

    函数作用：根据RDD中的元素值相同的个数。返回的类型为Map[K,V], K : 元素的值，V ：元素对应的的个数

    说明：countByValue中的Value不是指key-value中的Value，而是指数组或者所有集合中的元素，对于数组或单值集合
    而言，其value就是每个元素，对于多值集合，如key-value，tuple，其value是其元素整体

     */
  }

}
