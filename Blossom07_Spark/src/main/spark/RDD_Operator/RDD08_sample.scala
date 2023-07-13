package RDD_Operator

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDD08_sample {
  def main(args: Array[String]): Unit = {
    // RDD转换算子：sample
    /*
    2、sample算子定义
    def sample(withReplacement: Boolean, fraction: Double, seed: Long = Utils.random.nextLong): RDD[T]

    参数说明：
    withReplacement，Boolean类型，表示抽样是否有放回，true表示有放回，false表示不放回
    fraction，当抽样为有放回抽样，该参数表示，每条数据被抽到的次数；当抽样为不放回抽样，该参数表示抽样的基准值。
              即，抽样时，为每条数据“打分”，分值高于该基准值的数据会被抽取。因此，当抽样为不放回抽样时，该参数
              的取值范围为[0,1]
    seed，随机种子

    函数运行过程及作用：sample算子会从RDD中随机抽取数据，抽取原则是，根据随机种子，为RDD中的每个数据“打分”，
    当分值高于fraction参数的值时，将该数据抽取出来

     */

    // 举例
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("map")
    val context = new SparkContext(conf)

    val value: RDD[Int] = context.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

    val value1: RDD[Int] = value.sample(true, 1.2, 1)

    value1.collect().foreach(print)

    context.stop()

  }

}
