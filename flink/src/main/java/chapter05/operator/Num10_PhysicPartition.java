package chapter05.operator;

public class Num10_PhysicPartition {
    /**
     * Flink的逻辑分区
     *      对于DataStream的keyBy()方法，实际上只是对数据做了逻辑上的分区，即具有相同key的value分成一个组。
     *      在后续的算子处理过程中，同一个组的value确实会被分到同一个并行子任务中，而这一物理过程不是由keyBy()执行的
     *
     * Flink的物理分区
     *      1、shuffle()方法：重新洗牌，将数据随机地分配到下游算子的并行任务中去，这个随机分配满足均匀分布
     *      2、rebalance()方法：轮询分区，按照下游算子并行子任务的序号，依次分发数据
     *      3、rescale()方法：重缩放分区，与轮询分区相似，不同的地方在于，上游算子的一个并行子任务中的数据只会按照轮询的方式分配给下游算子的部分并行子任务中
     *      4、broadcast()方法：将输入数据复制并发送到下游算子的所有并行任务中去
     *      5、global()方法：将上游算子的所有数据都分配到下游算子的一个并行子任务中，强行将下游算子的并行度置为1
     *      6、partitionCustom()：自定义分区。
     *          public <K> DataStream<T> partitionCustom(Partitioner<K> partitioner, KeySelector<T, K> keySelector)
     *          partitionCustom()方法需要传入两个参数，
     *          第一个参数Partitioner<K> partitioner，是一个分区器对象，指定数据的分区规则
     *          第二个参数KeySelector<T, K> keySelector，是一个Key选择器，用于指定数据的key，因为分区是需要按照key决定数据去往哪个分区的
     *
     */
}
