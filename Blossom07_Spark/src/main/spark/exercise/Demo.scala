package exercise

import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Duration, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object Demo {
  def main(args: Array[String]): Unit = {
    //Spark Core
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("demo")
    new SparkContext(conf)

    // Spark SQL
    val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    // Spark Straming
    val context = new StreamingContext(conf, Duration(3000))

    val value: ReceiverInputDStream[String] = context.socketTextStream("hadoop132", 9999)

  }

}
