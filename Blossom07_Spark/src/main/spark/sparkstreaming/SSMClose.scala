package sparkstreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Duration, StreamingContext, StreamingContextState}

object SSMClose {
  // Spark Streaming优雅地关闭程序与数据恢复
  def main(args: Array[String]): Unit = {
    // 优雅的关闭
    // func1()

    // 优雅的启动程序
    func2()
  }

  // 优雅的关闭程序
  def func1(): Unit = {
    // 创建执行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("close")
    val streamingContext = new StreamingContext(conf, Duration(3000))

    // 设置检查点
    streamingContext.checkpoint("./Blossom07_Spark//checkpoint")

    // 创建一个新的线程，用于优雅关闭程序
    new Thread(
      new Runnable {
        override def run(): Unit = {
          // 创建标识，当满足关闭条件时，设置为true，执行关闭操作
          val flag = true

          // 设置程序启动大约5秒后关闭
          Thread.sleep(5000)

          if (flag) {
            // stop()方法，两个参数，第一个参数为true时，用于关闭SparkContext对象，无论StreamContext对象是否在运行
            // 第二个参数用于优雅关闭Spark Streaming
            // 当执行环境的状态为ACTIVE时，关闭环境
            if (streamingContext.getState() == StreamingContextState.ACTIVE) {
              streamingContext.stop(true, true)
            }
          }
        }
      }
    ).start()

    // 获取数据源
    val value: ReceiverInputDStream[String] = streamingContext.socketTextStream("hadoop132", 9999)

    value.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print()

    // 启动执行环境
    streamingContext.start()
    streamingContext.awaitTermination()

  }

  // 优雅的启动
  def func2(): Unit = {
    // 从检查点中启动环境
    val context1: StreamingContext = StreamingContext.getActiveOrCreate("./Blossom07_Spark//checkpoint",
      () => {
        // 创建执行环境
        val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("start")
        val context = new StreamingContext(conf, Duration(3000))

        // 定义数据处理逻辑
        // 获取数据源
        val value: ReceiverInputDStream[String] = context.socketTextStream("hadoop132", 9999)

        value.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print()

        // 返回执行环境
        context
      })

    // 设置检查点
    context1.checkpoint("./Blossom07_Spark//checkpoint")

    // 启动执行环境
    context1.start()
    context1.awaitTermination()

  }

}
