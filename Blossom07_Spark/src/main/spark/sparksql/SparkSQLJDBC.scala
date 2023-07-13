package sparksql

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLJDBC {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("fileIO")
    val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    val sc: SparkContext = session.sparkContext

    val df: DataFrame = session.read.format("jdbc")
      .option("url", "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC")
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("user", "root")
      .option("password", "1234")
      .option("dbtable", "user")
      .load()

    df.show()

    // 关闭资源
    sc.stop()
    session.stop()
  }

}
