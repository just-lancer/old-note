package sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Duration, StreamingContext}

object SSMWindow {
  // Spark Streaming窗口操作
  def main(args: Array[String]): Unit = {
    // 获取Spark Streaming执行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("window")
    val streamContext = new StreamingContext(conf, Duration(3000))

    // 为窗口算子设置检查点
    streamContext.checkpoint("./Blossom07_Spark//windowCH")

    // 获取数据源
    val value: ReceiverInputDStream[String] = streamContext.socketTextStream("hadoop132", 9999)

    // 将数据转换成key-value类型
    val value1: DStream[(String, Int)] = value.flatMap(_.split(" ")).map((_, 1))

    // 进行窗口操作
    val value2: DStream[(String, Int)] = value1.window(Duration(9000), Duration(300))

    // 对窗口中的数据进行操作：单词统计
    val value3: DStream[(String, Int)] = value2.reduceByKey(_ + _)

    value3.print()

    // 执行Spark Streaming流处理
    streamContext.start()
    streamContext.awaitTermination()

  }

}
