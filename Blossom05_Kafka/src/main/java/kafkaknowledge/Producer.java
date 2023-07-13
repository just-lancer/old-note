package kafkaknowledge;

public class Producer {
    /**
     *  4、生产者
     *      4.1 生产者消息发布原理
     *      4.2 生产者常用配置参数
     *      4.3 生产者异步消息发步
     *      4.4 生产者分区
     *          4.4.1 生产者默认分区策略
     *          4.4.2 自定义分区
     *      4.5 生产者优化
     *          4.5.1 消息发布效率优化
     *          4.5.2 数据可靠性分析及优化
     *          4.5.3 数据重复性分析及优化
     *          4.5.4 数据乱序分析及优化
     *
     */

    /**
     *  4、1生产者消息发布原理
     *      生产者客户端一般由Java代码实现，在生产者客户端中，消息的发布依赖两个线程的协调运行，这两个线程分别是main线程和sender线程
     *
     *      在Kafka生产者的逻辑中，main线程只关注向哪个分区中发送哪些消息；而sender线程只关注与哪个具体的broker节点建立连接，
     *      并将消息发送到所连接的broker。
     *
     *      主线程：
     *          > 主线程中创建的消息，会分别经过拦截器、序列化器和分区器，随后缓存到消息累加器中
     *              > 拦截器(interceptor)：生产者拦截器可以在消息发送之前对消息进行定制化操作，如过滤不符合要求数据，修改消息内容，数据统计等
     *              > 序列化器(serializer)：数据进行网络传输和硬盘读写都需要进行序列化
     *              > 分区器(partitioner)：对消息进行分区，便于发送到不同的分区中存储
     *
     *          > 消息累加器(RecoderAccumulator)：① 用于缓存经main线程处理好的消息；② sender线程会拉取其中的数据进行批量发送，进而提高效率
     *              > RecoderAccumulator的缓存大小默认为32M
     *              > RecoderAccumulator内部为每个分区都维护了一个双端队列，即Deque<ProduceBatch>消息写入缓存时，追加到队列的尾部；
     *
     *          > ProducerBatch：一个消息批次，由多条消息合并而成，默认大小为16K，sender从RecoderAccumulator中读取消息时，以ProducerBatch
     *              为单位进行读取，进而减少网络请求次数
     *
     *      sender线程：
     *          > sender线程从RecoderAccumulator中拉取到RecoderBatch后，会将<分区,Deque<Producer Batch>>的形式转换成<Node,List< ProducerBatch>
     *              的形式，即将消息的分区信息转换成对应的broker节点，随后，进一步封装成<Node,Request>的形式，这样形式的消息具备网络传输的条件。
     *              其中Request是Kafka各种协议请求。
     *
     *          > InFlightRequests：是一个容器，主要作用是缓存已经发出去但还没有收到服务端响应的请求。
     *              消息具备网络传输条件后，会被保存在InFlightRequests中，保存对象的具体形式为Map<NodeId，Deque<Request>>，其默认容量为5。
     *
     *      消息发送：
     *          目前为止，消息已经准备好，具备了进行网络传输的条件，Kafka broker在接收到消息后会对sender线程进行应答
     *
     *          > ack(acknowledgment)：生产者消息发送确认机制。ack有三个可选值0，1，-1(all)
     *              > ack = 0，生产者发送的消息不需要等待Kafka broker写入磁盘的应答；安全性低，但效率高
     *              > ack = 1，生产者发送的消息只需要leader分区接收到，并写入磁盘，Kafka broke便向生产者发送响应
     *              > ack = -1(all)，生产者发送的消息，需要所有的ISR副本都写入到磁盘，Kafka broker才会向生产者发送响应，安全性高，效率低
     *
     *          > ISR(In-sync replicas)，同步副本，在最长滞后时间内，能完成leader数据同步的副本称为同步副本。超过最长滞后时间，副本还未完成数据同步，
     *              会被踢出ISR列表，加入OSR列表，当OSR列表中的副本完成leader副本中数据的同步，那么该副本会再次加入ISR列表。
     *          > OSR(outof-sync replicas)，滞后同步副本
     *          > AR(all replicas)，全部副本，AR = ISR + OSR
     *
     *          > 消息发送成功，即ProducerBatch成功写入kafka broker中，此时，RecoderAccumulator和InFlightRequests会删除相应的ProducerBatch
     *          > 消息发送失败，会进行消息发送重试，重试次数默认为int类型最大取值
     *
     */

    /**
     *  4.2 生产者常用配置参数
     *      > bootstrap.servers     生产者连接集群所需的broker地址清单。可以设置1个或者多个，中间用逗号隔开。
     *                              这里并非需要所有的broker地址，因为生产者从给定的broker里查找到其他broker信息，zookeeper或者controler中有相关信息
     *      > key.serializer        指定发送消息的key的序列化类型。一定要写全类名
     *      > value.serializer      指定发送消息的value的序列化类型。一定要写全类名
     *      > buffer.memory         RecordAccumulator缓冲区总大小，默认32m。
     *      > batch.size            当RecordAccumulator中ProducerBatch数据量达到batch.size(默认值16 K)时，形成一个ProducerBatch，
     *                              以备sender进行网络传输
     *      > linger.ms             每隔linger.ms(默认值为0 ms)的间隔，在RecordAccumulator形成一个ProducerBatch，并添加到分区队列中，
     *                              以备sender进行网络传输
     *      > acks                  0，生产者发送数据之前，无需获得数据写入磁盘的应答；1，生产者发送数据需要等待leader副本写入磁盘的应答；
     *                              -1(all)，生产者发送数据需要等待所有ISR列表中的副本写入磁盘的应答。默认值为-1
     *      > retries               当消息发送出现错误的时候，系统会重发消息，retries表示重试次数，默认是int最大值2147483647
     *                              如果设置了重试，还想保证消息的有序性，需要设置MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION=1，
     *                              否则在重试此失败消息期间，其他的消息可能发送成功了
     *
     *      > retry.backoff.ms      两次重试之间的时间间隔，默认是100ms
     *      > enable.idempotence    是否开启幂等性，默认true，开启幂等性
     *      > compression.type      生产者发送的数据的压缩方式。默认是none，也就是不压缩。支持压缩类型：none、gzip、snappy、lz4和zstd
     *      > max.in.flight.requests.per.connection         已经发出去但还没有收到kafka broker响应的请求，即InFlightRequests的容量，默认为5
     *                                                      开启幂等性要保证该值是 1-5的数字
     *
     */

    /**
     *  4.3 生产者异步消息发布
     *      生产者发布消息的方式默认为异步方式，具体体现在Kafka broker对生产者发布的消息的响应。
     *      即当Kafka broker接收到消息后，根据ack应答级别，会返回消息的元数据给生产者
     *
     */

    /**
     *  4.4、生产者分区
     *      对topic进行分区能够在生产者端和消费者端提高数据的并行度；另一方面，对topic进行分区以适应broker，方便数据在集群中的管理
     *
     *  4.4.1 生产者默认分区策略
     *      对于用户而言，当调用send方法发送消息之后，消息就自然而然的发送到了broker中。然而在这一过程中，消息还需要经过
     *      拦截器、序列化器和分区器（Partitioner）的一系列处理后才能被真正地发往broker。
     *
     *      在Kafka中，ProducerRecord对象代表生产者产生的消息，即一组键值对。根据其重载构造器，可以知悉Kafka的默认分区策略：
     *      public ProducerRecord(String topic, Integer partition, Long timestamp, K key, V value, Iterable<Header> headers)
     *          > 当显示指明消息的分区号(partition)时，直接将指明的值作为消息的partition值
     *          > 当没有显示指明消息的分区号(partition)，但指明了key值，那么会将key的hashcode值与分区数取余的结果作为分区号
     *          > 当既没有分区号，也没有key值，Kafka会采用sticky partition(粘性分区器)，即随机选择一个分区（就是那个16K的批），并尽可能一直使用该分区，
     *              直到该分区被装满或者时间已到达，那么Kafka会再随机一个分区进行使用，如果分区和上次相同，会再随机一个分区，直到出现新的分区。
     *
     *  4.4.2 自定义分区
     *      自定义分区步骤：
     *          > 创建一个类实现Partition接口，并实现partition()、close()、configure()方法
     *          > partition()方法是分区逻辑
     *          > 在生产者配置中，添加自定义分区器参数
     *
     */

    /**
     *  4.5 生产者优化
     *      4.5.1 生产者消息发布效率优化
     *          利用缓存的手段，减少网络请求的次数，提高消息发布效率。可以设置的参数有三个：
     *              > buffer.memory     RecoderAccumulator的大小，默认32M
     *              > batch.size        数据批次(ProducerBatch)的大小，默认16K
     *              > linger.ms         数据批次(ProducerBatch)等待消息写入的时间间隔，默认0 ms
     *
     *          利用压缩，提升消息的发布效率，参数为：
     *              > compression.type  数据压缩方式，默认为none，即不压缩；可支持参数有：none、gzip、snappy、lz4和zstd
     *
     *      4.5.2 数据可靠性分析及优化
     *          数据可靠性主要为数据是否会丢失，具体体现在数据是否能够在Kafka broker中写入磁盘，这与Kafka的ack应答级别有关。
     *          数据发布的简要流程：
     *              生产者发布数据到Kafka broker中 ----> leader接收数据 ----> leader向磁盘中写入数据 ----> follower同步数据 ----> ack应答
     *
     *          > ack = 0，生产者发布的消息无需等待Kafka broker写入磁盘就判定数据发布成功，因此具有数据丢失的风险
     *          > ack = 1，生产者发布的消息在leader接收并写入磁盘中，判定为数据发布成功，如果在leader接收并写入磁盘后，
     *                  在follower同步leader的数据之前，leader发生故障，也会出现数据丢失的风险
     *          > ack = -1(all)，生产者发布的消息在ISR列表中所有副本都同步并写入磁盘后，判定数据发布成功。当分区副本设置为1，或者
     *                  ISR列表应答最小副本数量为1（就只剩下leader），那么此时的ack应答级别和ack = 1相同，因此也存在数据丢失的风险。
     *                  ISR列表应答最小副本数量的配置参数：min.insync.replicas，默认为1。
     *
     *          彻底解决数据可靠性问题：
     *              数据完全可靠 = (ack = -1) + (分区副本数量 >= 2) + (ISR最小应答副本数量 >= 2)
     *
     *      4.5.3 数据重复性分析及优化
     *          数据发布的简要流程：
     *              生产者发布数据到Kafka broker中 ----> leader接收数据 ----> leader向磁盘中写入数据 ----> follower同步数据 ----> ack应答
     *
     *          数据重复性问题的原因仍在ack应答方面。当ack = -1时，在leader和follower都进行了数据同步和磁盘写入后，leader准备进行ack应答时，
     *          leader出现故障，那么此时会选举出新的leader，而生产者由于没有收到ack应答，判定数据传输失败，进而进行数据传输重试，因此在Kafka broker
     *          上会出现重复的数据，尽管这种事件的概率极小。
     *
     *          解决数据重复性问题的手段：幂等性和事务
     *              > 幂等性是针对生产者的特性，幂等性可以保证生产者发布的消息，不会丢失、也不会重复。
     *
     *              > 实现幂等性的关键在于服务端能否区分请求是否重复，进而过滤重复请求。
     *
     *              > 区分请求是否重复，关键在于两点：一是请求中是否有唯一标识；二是记录已处理过的请求
     *
     *              > Kafka中，消息的唯一标识是一组编号：<ProducerID, Partition, SequenceNumber>
     *                      ProducerID：每个生产者客户端启动时，Kafka都会为其分配一个唯一的PID，该PID对用户不可见，即不能被用户修改
     *                      Partition：标识消息的分区号
     *                      SequenceNumber：对于每个PID，其对应的Producer发送数据的每个<Topic, Partition>都对应一个从0开始单调递增的Sequence Number
     *
     *              利用消息的唯一标识就能解决数据的重复性问题。然而幂等性只能保证在单会话中消息不会重复，原因是，当生产者重启后，Kafka会分配新的PID
     *              为了解决跨会话数据重复性问题，Kafka还引入了事务新特性。
     *
     *              Kafka中幂等性配置参数为enable.idempotence，默认值为true，即Kafka默认开启幂等性
     *
     *      4.5.4 数据乱序分析及优化
     *          说明：数据只能在同一个分区内呈现出有序性，在不同的分区间要求数据有序，没有这方面的需求，而且分区间数据有序，那么数据就成了串行传输，效率低
     *
     *          数据乱序出现的地方在InFlightRequests这个容器中，这个容器缓存着已发送但还没有收到ack应答的消息。当InFlightRequests中排在前面的消息发送失败，
     *          并进行重试时，排在后面的消息如果发送成功，那么就会出现数据乱序。
     *
     *          解决方案有两种：
     *              > 将InFlightRequests的容量设为1，但这样的生产者效率会很低
     *              > 开启幂等性，原因在于消息的发送会连带着消息的元数据一起发送，而消息的元数据信息包含消息的唯一标识<ProducerID, Partition, Sequece Number>
     *                  因此，通过Sequence Number可以判断消息产生的顺序，进而使得消息能够顺序存储。
     *
     */


}
