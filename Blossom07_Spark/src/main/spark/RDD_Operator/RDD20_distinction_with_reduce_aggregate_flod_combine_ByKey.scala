package RDD_Operator

object RDD20_distinction_with_reduce_aggregate_flod_combine_ByKey {
  /*
    def reduceByKey(func: (V, V) => V): RDD[(K, V)]
    def aggregateByKey[U: ClassTag](zeroValue: U)(seqOp: (U, V) => U, combOp: (U, U) => U): RDD[(K, U)]
    def foldByKey(zeroValue: V)(func: (V, V) => V): RDD[(K, V)]
    def combineByKey[C](createCombiner: V => C,
                        mergeValue: (C, V) => C,
                        mergeCombiners: (C, C) => C): RDD[(K, C)]

    四者的区别：
    reduceByKey，分区内数据计算规则和分区间数据计算规则相同，不需要额外的初始值，直接对具有相同key的value进行计算
    aggregateByKey，分区内数据计算规则和分区间数据计算规则不同，并且需要额外的初始值，用于与分区第一个数据进行计算
    flodByKey，当分区内计算规则和分区间计算规则相同时，aggregateByKey可以简化成flodByKey，只用传递初始值和一个计算规则
    combineByKey，是上述三个函数的底层实现函数，需要三个参数，第一个参数用于将集合的第一个数据转换成所需要的数据格式
                  第二个和第三个数据，分别是分区内的数据计算规则和分区间的数据计算规则

   */

}
