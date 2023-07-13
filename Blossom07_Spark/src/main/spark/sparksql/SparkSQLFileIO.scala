package sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object SparkSQLFileIO {
  def main(args: Array[String]): Unit = {
    // Spark SQL 数据加载与保存

    // parquet
    // parquet()

    // json
    // json()

    // csv
    // csv()

    // mysql
    // mysql()

    // hive
    hive()
  }

  def parquet(): Unit = {
    // 获取执行环境
    val session = SparkUtils.session

    // 加载数据——通用API
    val df1: DataFrame = session.read.load("E:\\IntelliJ_IDEA_workplace\\Blossom07_Spark\\data\\users.parquet")
    df1.show()

    // 加载数据——特定API
    val df2: DataFrame = session.read.parquet("E:\\IntelliJ_IDEA_workplace\\Blossom07_Spark\\data\\users.parquet")
    df2.show()

    // 保存数据——通用API
    df1.write.save("E:\\IntelliJ_IDEA_workplace\\Blossom07_Spark\\parquet1")

    // 保存数据——特定API
    df2.write.parquet("E:\\IntelliJ_IDEA_workplace\\Blossom07_Spark\\parquet2")

    // 关闭资源
    session.stop()
  }

  def json(): Unit = {
    // 获取执行环境
    val session = SparkUtils.session

    // 加载数据——通用API
    // Json格式不是Spark SQL的默认数据加载或保存格式，所以需要通过format指定格式
    val df1: DataFrame = session.read.format("json") load ("E:\\IntelliJ_IDEA_workplace\\Blossom07_Spark\\data\\people.json")
    df1.show()

    // 加载数据——特定API
    val df2: DataFrame = session.read.json("E:\\IntelliJ_IDEA_workplace\\Blossom07_Spark\\data\\people.json")
    df2.show()

    // 保存数据——通用API
    df1.write.format("json").save("E:\\IntelliJ_IDEA_workplace\\Blossom07_Spark\\jsonoutput1")

    // 保存数据——特定API
    df2.write.json("E:\\IntelliJ_IDEA_workplace\\Blossom07_Spark\\jsonoutput2")

    // 关闭资源
    session.stop()
  }

  // CSV文件
  def csv(): Unit = {
    // 获取执行环境
    val session = SparkUtils.session

    // 加载数据——通用API
    // 读取CVS文件，需要通过option指定文件的分隔符以及表头的处理
    val df1: DataFrame = session.read.format("csv").
      option("sep", ";").option("inferSchema", "true").option("header", "true").
      load("E:\\IntelliJ_IDEA_workplace\\Blossom07_Spark\\data\\people.csv")
    df1.show()

    // 加载数据——特定API
    // 仍任需要指定分隔符和表头
    val df2: DataFrame = session.read.
      option("sep", ";").option("inferSchema", "true").option("header", "true").
      csv("E:\\IntelliJ_IDEA_workplace\\Blossom07_Spark\\data\\people.csv")
    df2.show()

    // 保存数据时，如果不指定分隔符，默认分隔符是逗号
    //            如果不指定是否保存表头，默认不保存表头
    // 保存数据——通用API
    df1.write.format("csv").save("E:\\IntelliJ_IDEA_workplace\\Blossom07_Spark\\csvoutput1")

    // 保存数据——特定API
    df2.write.option("sep", "。").csv("E:\\IntelliJ_IDEA_workplace\\Blossom07_Spark\\csvoutput2")

    // 关闭资源
    session.stop()
  }

  // MySQL数据源
  def mysql(): Unit = {
    // 获取执行环境
    val session = SparkUtils.session

    // 加载数据源
    // 对于数据源，都需要在调用load()方法前，调用format()，指定格式为jdbc
    val df: DataFrame = session.read.
      format("jdbc").option("url", "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC").
      option("driver", "com.mysql.cj.jdbc.Driver").
      option("user", "root").
      option("password", "1234").
      option("dbtable", "user").load()
    df.show()

    // 关闭资源
    session.stop()
  }

  // hive
  def hive(): Unit = {
    // 获取执行环境
    val session = SparkUtils.session

    // 加载数据源
    // 对于数据源，都需要在调用load()方法前，调用format()，指定格式为jdbc
    val df: DataFrame = session.read.
      format("jdbc").option("url", "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC").
      option("driver", "com.mysql.cj.jdbc.Driver").
      option("user", "root").
      option("password", "1234").
      option("dbtable", "user").load()
    df.show()

    // 关闭资源
    session.stop()
  }
}

// 获取Spark SQL连接对象
object SparkUtils {
  val conf = new SparkConf().setMaster("local[*]").setAppName("fileIO")
  val session = SparkSession.builder().config(conf).getOrCreate()
  val sc = session.sparkContext
}