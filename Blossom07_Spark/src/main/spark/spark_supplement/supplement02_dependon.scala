package spark_supplement

object supplement02_dependon {
  // RDD依赖关系

  /*
   1、RDD血缘关系
      RDD只支持粗粒度转换，即对于大量的记录只会执行单个转换或行动操作。
      将创建RDD的一系列Lineage（血统）记录下来，以便恢复丢失的分区。
      RDD的Lineage会记录RDD的元数据信息和转换行为，当该RDD的部分分区数据丢失时，
      它可以根据这些信息来重新运算和恢复丢失的数据分区。

   */

  /*
    2、RDD依赖关系
      这里所谓的依赖关系，其实就是两个相邻RDD之间的关系

      RDD窄依赖
        窄依赖表示每一个父(上游)RDD的Partition最多被子（下游）RDD的一个Partition使用，窄依赖我们形象的比喻为独生子女。

      RDD宽依赖
        宽依赖表示同一个父（上游）RDD的Partition被多个子（下游）RDD的Partition依赖，会引起Shufflec操作，总结：宽依赖我们形象的比喻为多生。

   */

  /*
    3、RDD阶段的划分
      RDD算子在执行时，每出现一次shuffle操作，那么就表示旧的执行阶段结束，新的执行阶段开始

    4、RDD任务划分
      RDD任务切分中间分为：Application、Job、Stage和Task
        Application：初始化一个SparkContext即生成一个Application；
        Job：一个Action算子就会生成一个Job；
        Stage：Stage等于宽依赖(ShuffleDependency)的个数加1；
        Task：一个Stage阶段中，最后一个RDD的分区个数就是Task的个数。
   */

}
