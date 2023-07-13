package sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Duration, StreamingContext}

object SSMIntroduction {
  // SparkStraming 入门案例
  // 监听一个scoket端口，进行单词计数
  def main(args: Array[String]): Unit = {
    // 创建SparkStraming执行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("introduction")
    val ssc: StreamingContext = new StreamingContext(conf, Duration.apply(3000))

    // 监控一个socket端口
    val input: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop132", 9999)

    // 数据处理逻辑
    val value1: DStream[String] = input.flatMap(_.split(" "))

    val value2: DStream[(String, Int)] = value1.map(str => (str, 1))

    val value3: DStream[(String, Int)] = value2.reduceByKey(_ + _)

    value3.print()

    // 启动流处理环境
    ssc.start()
    ssc.awaitTermination()

  }

}
