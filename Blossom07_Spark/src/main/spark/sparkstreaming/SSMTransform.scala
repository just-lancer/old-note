package sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Duration, StreamingContext}

object SSMTransform {
  // Spark Streaming无状态转换通用算子
  def main(args: Array[String]): Unit = {
    // 获取配置对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("transform")
    val streamingContext = new StreamingContext(conf, Duration(3000))

    // 获取数据流：监控端口
    val value: ReceiverInputDStream[String] = streamingContext.socketTextStream("hadoop132", 9999)

    // 使用transfrom进行无状态转换：进行word count
    val value4: DStream[(String, Int)] = value.transform(
      rdd => {
        val value1: RDD[String] = rdd.flatMap(_.split(" "))
        val value2: RDD[(String, Int)] = value1.map((_, 1))
        val value3: RDD[(String, Int)] = value2.reduceByKey(_ + _)
        value3
      }
    )

    value4.print()

    // 启动Spark Streaming
    streamingContext.start()
    streamingContext.awaitTermination()

  }
}