package sparksql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLEnv {
  // Spark SQL
  def main(args: Array[String]): Unit = {
    // 获取Spark SQL执行环境
    // 在老的版本中，SparkSQL提供两种SQL查询起始点：
    // 一个叫SQLContext，用于Spark自己提供的SQL查询；一个叫HiveContext，用于连接Hive的查询。
    // 因此在创建时Spark SQL执行环境，也会创建Spark Core执行环境
    // Spark SQL执行环境为SparkSession对象

    // Spark SQL执行环境的创建需要用到建造者模式：builder

    // 创建执行环境的配置对象
    val sparkSQLConfig: SparkConf = new SparkConf().setMaster("local[*]").setAppName("sparksql")

    // 创建Spark SQL执行环境
    val session: SparkSession = SparkSession.builder().config(sparkSQLConfig).getOrCreate()

    // 导入Spark SQL执行环境中的隐式转换
    import session.implicits._

    // 从Spark SQL执行环境中获取Spark Core的执行环境
    val sc: SparkContext = session.sparkContext

    // 创建RDD，仍需要Spark Core执行环境进行创建
    val rdd: RDD[(String, String, Int)] = sc.makeRDD(List(("001", "zhangsan", 20), ("002", "lisi", 30), ("003", "wangwu", 40)))
    println("RDD：" + rdd)

    // 将RDD转换成DataFrame
    val dataFrame: DataFrame = rdd.toDF("id", "name", "age")
    println("DataFrame：" + dataFrame)

    // 将DataFrame转换成RDD
    val rdd1: RDD[Row] = dataFrame.rdd
    println("DataFrame => RDD：" + rdd1)

    // 将RDD转换成DataSet
    val dataSet: Dataset[(String, String, Int)] = rdd.toDS()

    // 将DataSet转换成RDD
    val rdd2: RDD[(String, String, Int)] = dataSet.rdd
    println("DataSet => RDD：" + rdd2)

    // 利用样例类将RDD转换成DataSet
    // RDD转换为DataSet时，RDD中的元素必须是样例类对象，
    // 不然无法直接调用toDS()方法创建DataSet，因为方法没有参数
    val dataSet1: Dataset[User] = rdd.map(
      tupleCase => {
        User(tupleCase._1, tupleCase._2, tupleCase._3)
      }
    ).toDS()
    println("RDD => DataSet[Case]：" + dataSet1)

    // 将DataSet转换成RDD
    val rdd3: RDD[User] = dataSet1.rdd
    println("DataSet[Case] => RDD：" + rdd3)

    // 关闭资源
    sc.stop()
    session.stop()
  }

  // 创建样例类
  case class User(id: String, name: String, age: Int) {}

}
