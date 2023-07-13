package RDD_Operator

object RDD34_save {
  def main(args: Array[String]): Unit = {
    /*
    RDD行动算子

    save系列算子

    函数声明：
    def saveAsTextFile(path: String): Unit  将RDD数据保存为文本文件
    def saveAsObjectFile(path: String): Unit  将RDD数据保存为对象文件
    def saveAsSequenceFile(
        path: String,
        codec: Option[Class[_ <: CompressionCodec]] = None): Unit   将RDD数据保存为二进制文件

        说明：只有key-value类型的数据才能调用saveAsSequenceFile算子

     */
  }

}
