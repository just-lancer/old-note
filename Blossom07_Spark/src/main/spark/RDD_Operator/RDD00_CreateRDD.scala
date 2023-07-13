package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD00_CreateRDD {
  def main(args: Array[String]): Unit = {
    /*
    RDD（Resilient Distributed Dataset）叫做弹性分布式数据集，是Spark中最基本的数据抽象，它代表一个不可变、可分区、里面的元素可并行计算的集合
    RDD具有数据流模型的特点：自动容错、位置感知性调度和可伸缩性。
    RDD允许用户在执行多个查询时显式地将工作集缓存在内存中，后续的查询能够重用工作集，这极大地提升了查询速度。
     */

    /*
     创建RDD有四种方式
        ① 从内存中创建RDD，即利用一个集合创建RDD
        ② 从磁盘中创建RDD，即读取磁盘中的一个文件的数据，并创建RDD
        ③ 在一个旧的RDD基础上创建一个新的RDD
        ④ 直接new 一个RDD对象

        说明：方式③和方式④，我们自己使用的较少；使用较多的是框架开发维护人员
     */

    // 方式一创建RDD
    //    func1()
    // 方式二创建RDD
    func2()

  }

  def func1(): Unit = {
    // 从内存中创建RDD，即利用一个集合创建RDD

    // 创建spark连接对象
    val file: SparkConf = new SparkConf().setMaster("local[*]").setAppName("file")
    val context = new SparkContext(file)

    val ints: Array[Int] = Array(1, 2, 3, 4)

    // 调用spark连接对象的方法，创建RDD对象
    //    parallelize[T: ClassTag](seq: Seq[T],numSlices: Int = defaultParallelism): RDD[T]
    //    第一个参数seq是一个Scala集合的Seq对象，表示需要被创建成RDD对象的集合
    //    第二个参数numSlices是一个默认参数，表示该RDD对象的分区数量。
    //        对于从内存创建的RDD对象，其分区的数量有三种方式可以设置。
    //        方式一：spark框架的默认值，defaultParallelism，该默认值表示默认的并行度，与运行的spark的核数有关
    //        方式二：spark框架的配置参数，scheduler.conf.getInt("spark.default.parallelism", totalCores)，通过spark.default.parallelism配置项能够设置RDD的分区数
    //        方式三：通过parallelize()函数的第二个参数进行配置
    //        三种方式配置分区数量的优先级，从低到高
    val value: RDD[Int] = context.parallelize(ints)

    value.collect().foreach(println)

    // spark框架对parallelize()方法进行了进一步的封装，调用spark连接对象的makeRDD()方法也能够创建对象
    // makeRDD()方法中调用了parallelize()方法，目的是为了方便方法的调用
    val value1: RDD[Int] = context.makeRDD(ints)
    value1.collect().foreach(println)

    // 对于分区数量的说明
    /*
    从内存中创建RDD对象，其分区数量可以通过默认值，配置项，方法参数三种方式配置
    对于数据在每个分区的分配策略，遵循以下两个原则
      一：均匀分布
      二：当存在不均匀的情况，后向分配

      例如，(1,2,3,4,5)分为三个分区
      5 / 3 = 1...2
      由于分区不均匀，所以，多余的两个数据会均分到后两个分区，即
      [1]、[2, 3]、[4,5]
      需要说明的是，spark是先将各个分区所得到的数据计算好之后，再进行数据分配
     */

    // 关闭资源
    context.stop()

  }

  def func2(): Unit = {
    // 从磁盘中创建RDD，即读取磁盘中的一个文件的数据，并创建RDD

    // 创建spark连接对象
    val memory: SparkConf = new SparkConf().setMaster("local[*]").setAppName("memory")
    val context = new SparkContext(memory)

    // 利用spark连接对象调用方法读取文件
    // textFile(path: String, minPartitions: Int = defaultMinPartitions): RDD[String]
    //    第一个参数path表示所要读取的文件的路径
    //    第二个参数minPartitions表示将读取文件，创建RDD对象后，将数据进行分区的分区数，
    //          该参数有默认值，为defaultMinPartitions = math.min(defaultParallelism, 2)，表示最小分区数量

    /*
    从文件中创建RDD对象的分区的说明：
    spark框架从文件中创建RDD对象并进行分区的策略采用的是Hadoop的文件分区策略
    -- ① 统计出所读取文件的数据的字节数，byte
    -- ② 获分区数量，num取
    -- ③ 字节数除以分区数，求得商和余数，byte / num = shang...yushu
          当字节数的余数小于余数的商的10%时，那么会创建num个分区，当字节数的余数大于余数的商的10%时，那么会创建num+1个分区，
          这也是为什么称为最小分区数

     */

    /*
    确定分区数后，还需要确定每个分区所分配的数据，遵循以下原则
     1. 数据以行为单位进行读取
          spark读取文件，采用的是hadoop的方式读取，所以一行一行读取，和字节数没有关系
     2. 数据读取时以字节偏移量为单位，偏移量不会被重复读取
     3. 数据分区的偏移量范围的计算
        num个分区，那么有
        第一个分区所获得的数据的偏移量：[0, num * 1]
        第二个分区所获得的数据的偏移量：[num * 1, num * 2]
        第三个分区所获得的数据的偏移量：[num * 2, num * 3]
        ...
        第n个分区所获得的数据的偏移量：[num * (n - 1), num * n]

     */

    // ① 读取一个文件
    val value: RDD[String] = context.textFile("Blossom07_Spark/data/word.txt")

    // ② 读取两个或多个文件，将多个文件的路径传递给path参数，并用逗号隔开。注意，不要为了美观，在逗号后面添加空格
    val value1: RDD[String] = context.textFile("Blossom07_Spark/data/word1.txt,Blossom07_Spark/data/word.txt")

    // ③ 读取两个或多个文件，当这些文件具有相同的部分，可以使用通配符'*'进行描述，类似于正则表达式
    val value2: RDD[String] = context.textFile("Blossom07_Spark/data/word*")

    // ④ 读取多个文件，并且获取每条数据来源于哪个文件夹，调用spark连接对象的wholeTextFile方法
    //    该方法与textFile方法除了会表示数据来源之外，没有区别
    val value3: RDD[(String, String)] = context.wholeTextFiles("Blossom07_Spark/data/word*")

    // ⑤ 读取一个文件夹下的所有文件，给path参数传递文件夹的路径即可。注意，读取一个文件夹下的所有文件，该文件夹下不能有文件夹
    //    因为文件夹不是一个文件，所有会报IO异常
    val value4: RDD[String] = context.textFile("Blossom07_Spark/data")

    //    value.collect().foreach(println)
    //    value1.collect().foreach(println)
    //    value2.collect().foreach(println)
    //    value3.collect().foreach(println)
    value4.collect().foreach(println)


    // 关闭spark连接
    context.stop()

  }
}
