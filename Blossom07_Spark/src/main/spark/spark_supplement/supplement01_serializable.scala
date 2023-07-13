package spark_supplement

import org.apache.spark.{SparkConf, SparkContext}

object supplement01_serializable {
  // RDD序列化
  /*
   1、为什么要进行序列化，以及对什么东西进行序列化
        从计算的角度, 算子之外的代码都是在Driver端执行, 算子内的代码都是在Executor端执行。
        那么在scala的函数式编程中，就会导致算子内经常会用到算子外的数据，这样就形成了闭包的效果，
        如果使用的算子外的数据无法序列化，就意味着无法通过网络传输给Executor端执行，就会发生错误，所以需要在执行任务计算前，
        检测闭包内的对象是否可以进行序列化，这个操作我们称之为闭包检测。

        Scala2.12版本后闭包编译方式发生了改
   */

  /*
   2、如何进行序列化
      在spark变成过程中，凡是RDD算子用到的属性或方法，其所在的类必须继承serializable接口即可
   */

  /*
   3、Kryo序列化框架
      Java的序列化能够序列化任何的类。但是比较重（字节多），序列化后，对象的提交也比较大。
      Spark出于性能的考虑，Spark2.0开始支持另外一种Kryo序列化机制。Kryo速度是Serializable的10倍。
      当RDD在Shuffle数据的时候，简单数据类型、数组和字符串类型已经在Spark内部使用Kryo来序列化。

      说明：即使使用Kryo序列化，也要继承Serializable接口。

      在需要使用Kryo框架进行序列化时，只需要在创建spark连接对象时，设置序列化机制即可。
      即调用set()方法，设置配置项spark.seriablizable项为org.apache.spark.serializer.KryoSerializer

      同时调用registerKryoClasser()方法注册所需要序列化的类，将所需要的类组装成一个数组传入，进行注册

      假设需要注册两个类，ClassA和ClassB，那么创建spark连接对象的步骤及过程如下


   */

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("serializer").set("spark.seriablizable", "org.apache.spark.serializer.KryoSerializer").registerKryoClasses(Array(classOf[ClassA], classOf[ClassB]))

    val context = new SparkContext(conf)
  }

  class ClassA {

  }

  class ClassB {

  }

}
