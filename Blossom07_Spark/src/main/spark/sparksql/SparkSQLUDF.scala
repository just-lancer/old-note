package sparksql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql._
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.{SparkConf, SparkContext}


object SparkSQLUDF {
  // 自定义函数
  def main(args: Array[String]): Unit = {
    // 创建执行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("udf")
    val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    val sc: SparkContext = session.sparkContext
    // 导入Spark SQL隐式转换
    import session.implicits._

    // 创建RDD和DataFrame
    val rdd: RDD[(String, String, Int)] = sc.makeRDD(List(("id", "zhangsan", 20), ("id", "lisi", 20), ("id", "wangwu", 40)))
    val df: DataFrame = rdd.toDF("user_id", "user_name", "user_age")

    // 自定义函数需求，使得调用函数后，能在每个用户的前面添加一个前缀
    // 自定义函数（UDF），直接使用Spark SQL执行环境对象进行函数注册即可
    // register()函数需要两个参数，分别是自定义函数的函数名和函数的执行逻辑
    session.udf.register("perfName", (name: String) => {
      "name : " + name
    })
    // 注册完成后，perfName就是一个SQL函数了

    // 创建临时视图
    df.createOrReplaceTempView("user")

    // SQL查询
    session.sql("select user_id, perfName(user_name), user_age from user").show()

    println("******************************************")

    // 自定义聚合函数需求：求用户的平均年龄
    // 自定义聚合函数（UDAF aggregate），需要继承Spark 指定的抽象类
    // 自定义聚合函数有强类型实现和弱类型实现，在Spark 3.0 版本后，就不推荐弱类型实现了
    // 以下展示为强类型展示
    // 1、自定义聚合函数继承抽象类Aggregate，并指定泛型

    // 2、注册自定义的聚合函数
    // 依然是利用Spark SQL执行环境进行注册
    val myUDAF = new MyUDAF()
    session.udf.register("MyAvg", functions.udaf(myUDAF))
    // 现在在SQL语句中可以直接使用该方法了

    // 3、使用自定义聚合函数
    session.sql("select user_name, MyAvg(user_age) as avg from user group by user_name ").show()

    // 关闭执行环境
    sc.stop()
    session.stop()
  }

  // Aggregator的泛型说明：
  // IN   输入数据的类型
  // BUF  缓冲区数据的类型，其实就是flink中的累加器
  // OUT  输出数据的类型
  class MyUDAF extends Aggregator[Double, Tuple2[Double, Long], Double] {
    // 设置缓冲区数据的初始值
    override def zero: (Double, Long) = {
      (0, 0)
    }

    // 每一条数据到来时，数据与缓冲区数据的聚合规则
    override def reduce(buff: (Double, Long), data: Double): (Double, Long) = {
      (buff._1 + data, buff._2 + 1)
    }

    // 各个Executor的累加器的聚合（合并）规则
    override def merge(buff1: (Double, Long), buff2: (Double, Long)): (Double, Long) = {
      (buff1._1 + buff2._1, buff1._2 + buff2._2)
    }

    // 依据各Executor合并后的缓冲区的值（累加器的值），输出结果
    override def finish(reduction: (Double, Long)): Double = {
      if (reduction._2 == 0) {
        0
      } else {
        reduction._1 / reduction._2
      }
    }

    // 因为Spark是分布式计算，所以缓冲数据和最终结果需要在网络中传输，需要指定编码方式
    // 这些都是固定的写法
    // 缓冲区编码方式
    override def bufferEncoder: Encoder[(Double, Long)] = Encoders.tuple(Encoders.scalaDouble, Encoders.scalaLong)

    // 输出结果编码方式
    override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
  }

}
