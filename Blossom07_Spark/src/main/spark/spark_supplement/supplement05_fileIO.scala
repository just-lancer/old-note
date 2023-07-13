package spark_supplement

object supplement05_fileIO {
  // RDD文件读取与保存

  /*
   Spark的数据读取及数据保存可以从两个维度来作区分：文件格式以及文件系统。
   文件格式分为：text文件、csv文件、sequence文件以及Object文件；
   文件系统分为：本地文件系统、HDFS、HBASE以及数据库。

   */

  /*
    1、text文件
    // 读取输入文件
    val inputRDD: RDD[String] = sc.textFile("input/1.txt")

    // 保存数据
    inputRDD.saveAsTextFile("output")

   */

  /*
    2、sequence文件
      SequenceFile文件是Hadoop用来存储二进制形式key-value对而设计的一种平面文件(Flat File)。
      在SparkContext中，可以调用sequenceFile[keyClass, valueClass](path)

      // 保存数据为SequenceFile
      dataRDD.saveAsSequenceFile("output")

      // 读取SequenceFile文件
      sc.sequenceFile[Int,Int]("output").collect().foreach(println)

   */

  /*
    3、object对象文件
    对象文件是将对象序列化后保存的文件，采用Java的序列化机制。
    可以通过objectFile[T: ClassTag](path)函数接收一个路径，读取对象文件，返回对应的RDD，
    也可以通过调用saveAsObjectFile()实现对对象文件的输出。因为是序列化所以要指定类型。

    // 保存数据
    dataRDD.saveAsObjectFile("output")

    // 读取数据
    sc.objectFile[Int]("output").collect().foreach(println)

   */

}
