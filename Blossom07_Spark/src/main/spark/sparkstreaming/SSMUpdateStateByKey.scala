package sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Duration, StreamingContext}

object SSMUpdateStateByKey {
  // Spark Streaming有状态转换
  def main(args: Array[String]): Unit = {
    // 创建Spark Streaming执行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("updataStateByKey")
    val streamingContext = new StreamingContext(conf, Duration(3000))

    // 设置有状态转换的检查点文件
    streamingContext.checkpoint("./Blossom07_Spark//ch")

    // 获取数据源
    val value: ReceiverInputDStream[String] = streamingContext.socketTextStream("hadoop132", 9999)

    // 有状态转换：进行word count
    // 先将数据转换成key-value
    val value1: DStream[String] = value.flatMap(_.split(" "))

    val value2: DStream[(String, Int)] = value1.map((_, 1))

    val value3: DStream[(String, Int)] = value2.updateStateByKey(
      (seq: Seq[Int], buff: Option[Int]) => {
        val i: Int = seq.foldLeft(0)(_ + _)
        val i1: Int = buff.getOrElse(0)
        Some(i + i1)
      }
    )

    value3.print()

    // 启动流处理执行环境
    streamingContext.start()
    streamingContext.awaitTermination()
  }

}
