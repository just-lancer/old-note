package exercise

import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object ACC {
  // Spark累加器的使用
  // 累加器就相当于flink的累加器，也是有输入和输出的
  // 累加器是一个只写变量

  def main(args: Array[String]): Unit = {
    // 创建Spark配置对象
    val accConf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("ACCExercise")

    // 创建Spark上下文环境
    val context = new SparkContext(accConf)

    // 需求：单词计数
    // 创建RDD
    val rdd: RDD[String] = context.makeRDD(List("hello", "world", "hello", "spark"))

    // 申明累加器
    // 第一步：创建累加器
    val myAccumulator = new MyAccumulator

    // 第二步：向Spark注册累加器
    context.register(myAccumulator, "exerciseSACC")

    // 注册完成后，自定i累加器对象myAccumulator现在就是一个可以使用的累加器，在之后的代码中可以直接使用

    // 执行RDD逻辑，在每个Executor中，利用累加器进行单词计数
    rdd.foreach(
      num => {
        myAccumulator.add(num)
      }
    )

    // 现在在Driver中获取累加器的结果，依然是调用累加器的value方法
    println(myAccumulator.value)

    // 关闭资源
    context.stop()
  }

}

class MyAccumulator extends AccumulatorV2[String, mutable.Map[String, Int]] {
  // 自定义泛型IN -> String，表示输入数据为String
  //          OUT ->  mutable.Map[String, Int]，表示输出数据为可变map
  // 定义一个输出的map，这个map就相当于是一个累加器，不过不是全局的，是每个Executor的，所以，最后还需要将它们在Driver合并
  var out: mutable.Map[String, Int] = mutable.Map()

  // 用于判断累加器是否是初始累加器
  // 对于计数（值）累加器而言，值为0，表示是初始累加器
  // 对于List累加器而言，值为Nil，表示初始累加器
  // ......
  override def isZero: Boolean = {
    out.isEmpty
  }

  // 为当前累加器创建一个副本，也就是该类的对象
  override def copy(): AccumulatorV2[String, mutable.Map[String, Int]] = {
    new MyAccumulator
  }

  // 重置该累加器，即重置后，该累加器调用isZero()方法，返回值为true
  override def reset(): Unit = {
    out.clear()
  }

  // 将输入值和累加器进行累计计算的逻辑
  // 该方法每来一条数据都会执行一次
  override def add(v: String): Unit = {
    val i: Int = out.getOrElse(v, 0)
    out.update(v, i + 1)
  }

  // 定义该累加器的当前值，即累加器的输出值，
  // 这是因为各个Executor之间的累加器还需要合并，
  // 这个方法主要是是给累加器对象调用，用以获得累加器值的方法
  override def value: mutable.Map[String, Int] = {
    out
  }

  // 将各个Executor中的累加器对象进行两两合并的逻辑
  // 该方法，由累加器对象进行调用
  override def merge(other: AccumulatorV2[String, mutable.Map[String, Int]]): Unit = {
    val map1 = out
    val map2 = other.value
    println("=====")
    println(out)
    // Scala中，集合的聚合方法，aggregate、flod，它们的数据类型问题，要注意
    // 两个累加器合并
    out = map1.foldLeft(map2)(
      // m1 : mutable.Map[String, Int]
      // m2 : (String, Int)
      (m1, m2) => {
        val i: Int = m1.getOrElse(m2._1, 0)
        m1.update(m2._1, i + m2._2)
        m1
      }
    )
    println("*********")
    println(out)

  }
}