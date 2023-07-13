package kafkaknowledge;

public class Broker {
    /**
     *  5、Broker
     *      5.1 zookeeper维护的Kafka节点信息
     *      5.2 broker工作流程：副本leader选举机制
     *      5.3 分区副本leader和follower故障时的数据同步问题
     *      5.4 broker文件存储
     *          5.4.1 文件存储机制
     *          5.4.2 文件清理机制
     *      5.5 broker高效数据读写
     *      5.6 broker常用配置参数
     *
     */

    /*
     * 5.1 zookeeper维护的Kafka节点信息（Kafka 2.8及以后Kafka可以自己维护节点信息）
     *      Kafka需要依赖zookeeper进行元数据管理
     *      zookeeper中，Kafka节点所包含的信息有很多，需要重点关注的有三个节点信息
     *      |---- Kafka
     *          |---- brokers
     *          |---- ids    记录了当前Kafka集群中有哪些broker节点，例如，/Kafka/brokers/ids [0,1,2] 表示当前有0，1，2三个broker正常工作
     *          |---- topics 记录了当前主题的各个分区以及分区副本的ISR，例如
     *                      /Kafka/brokers/topics/[topic]/partitions/[partitionID]/state  {"leader":1 ,"isr":[1,0,2]}
     *                      表示当前主题下，指定分区的副本(ISR)有1,0,2，其中1号broker节点上的副本为leader副本
     *          |---- controller 该节点记录了中央控制器(center controller)所在的Kafka broker，例如，/kafka/controller {"broker" : 1}
     *                      表示当前集群的中央控制器在broker ：1节点上
     *
     *      zookeeper在Kafka中的作用：
     *          > broker节点注册信息
     *          > topics注册信息
     *          > leader选举和follower信息同步管理，中央控制器注册信息
     *
     *          > 生产者负载均衡
     *          > 消费者负载均衡
     *          > 分区与消费者关系
     *          > 消息消费进度offset管理，Kafka0.9版本后由Kafka自己来管理
     *          > 消费者注册
     *
     */

    /**
     *  5.2 broker工作流程：副本leader选举机制
     *      Kafka集群一般由多个broker服务构成
     *      broker工作流程从broker服务启动开始
     *
     *      > 每个broker启动时，都会到zookeeper的/kafka/brokers/ids上节点进行注册，即创建属于自己的节点信息。
     *              节点信息包括broker的IP地址和端口号。broker注册的节点类型是临时节点，当broker宕机，相应节点会被删除。
     *
     *      > 当所有的broker在zookeeper上注册完成后，会进行中央控制器(center controller)注册，不同于broker的注册，
     *              controller的注册方式是抢占式注册，一旦由controller在zookeeper上注册成功，那么该controller便成为
     *              所有controller的'leader'。
     *
     *      > 选举出来的controller会监听/Kafka/brokers/ids节点中broker的变化，随后进行分区副本leader的选举。
     *              leader选举规则：
     *                  -- broker节点必须存活，即leader必须是ISR列表中的节点
     *                  -- broker在AR列表中的顺序越靠前，成为leader的优先级越高
     *
     *      > 当controller选举出分区副本的leader后，会将分区副本的leader的相关信息注册到zookeeper的
     *              /kafka/bokers/topics/[topic]/partitions/[partitionID]/state节点上，随后其他非'leader'controller
     *              到该节点同步分区副本的信息，以便于当'leader'controller挂掉，能够随时注册成为'leader'，提供服务
     *
     *      > 至此，Kafka broker集群的正常启动完成，第一次分区副本leader选举完成。当工作中的分区副本leader挂掉，controller
     *              会再次进行leader选举。
     *
     *      > 当正在工作的leader挂掉，zookeeper中/kafka/brokers/ids节点的信息会进行相应的更新，controller监听该节点中leader
     *              属性的broker产生变化，那么会开始进行分区副本leader的选举。首先，从/kafka/brokers/topics/[topic]/partitions/[partitionID]/state
     *              节点中获取ISR列表和leader信息，重新开始分区副本leader的选举，选举规则依然不变，选举出'leader'后，依然将信息同步到该节点。
     *
     */

    /**
     *  5.3 分区副本leader和follower故障时的数据同步问题
     *      LEO（Log End Offset），标识当前日志文件中下一条待写入的消息的offset
     *      HW（High Watermark）：所有副本中最小的LEO
     *
     *      follower故障时的数据同步
     *          >  follower发生故障后会被踢出ISR，进入OSR
     *          > 期间，leader继续接收数据，其他follower继续同步leader的数据
     *          > 当故障的follower恢复后，会读取本地磁盘记录的HW，并将文件中offset高于HW的数据删除，并从HW开始同步leader的数据
     *          > 当follower的数据同步跟上整个分区副本后，即可将该follower重新加入ISR列表。即follower的LEO大于等于该分区的HW。
     *
     *              说明：follower的LEO大于等于分区HW，表明follower已经跟上了ISR列表中同步leader数据最慢的follower，最慢的follower
     *              都能够在ISR中，那么该follower也能够在ISR中。
     *
     *      leader故障时的数据同步
     *          > 当leader发生故障时，controller会重新选举出新的leader
     *          > 以当前分区的HW为准，ISR中其他follower高于HW的数据会被截取掉
     *          > 随后leader和follower开始正常工作
     *
     *          说明：leader发生故障时的数据同步只能保证副本间数据的一致性，无法保证数据不重复
     *
     */

    /**
     *  5.4 broker文件存储机制
     *  5.4.1 文件存储机制
     *      topic：主题，Kafka中数据管理的逻辑单元，并不实际存储数据，类似数据库管理系统中的库和表
     *      partition：分区，topic物理上的分组，一个topic能够分为多个partition，每一个partition是一个有序的队列
     *      segment：分片，partition物理上的进一步细分，一个partition由多个segment组成
     *      offset：偏移量，偏置。每个partition都由一系列有序的、不可变的消息组成，每条消息都用一个连续的序列号维护，用于标识该消息在partition中的位置
     *
     *      Kafka文件存储机制：
     *          > 一个topic可以由多个partition组成，每个partition都是一个文件夹，其命名方式为：主题名-序列号，即[topic_name]-[num]
     *              其中序列号从0开始
     *
     *          > 一个partition由多个segment组成，每个segment是一系列文件的集合，其命令方式为：
     *              partition全局的第一个segment从0（20个0）开始，后续的每一个segment文件名是上一个segment文件中最后一条消息的offset值
     *              这一系列文件包含主要的三个文件：(20个0).index/.log/.timeindex，其中.log是真实数据的存储文件，.index是数据的索引文件
     *              .timeindex是消息时间戳
     *
     *              默认条件下，每个segment文件大小为1G，即.log .index .timeindex等文件大小之和为1G
     *
     *      假设/kafka/data为Kafka中数据存储的根目录，即在Kafka配置文件中配置log.dir=/kafka/data
     *
     *      Kafka文件目录结构：以单topic，单分区，单segment为例，分区名为first
     *      |---- kafka/data
     *          |---- first-0
     *              |---- 00000000000000000000.log
     *              |---- 00000000000000000000.index
     *              |---- 00000000000000000000.timeindex
     *              |---- ......
     *
     *      segment中，.log文件、.index文件解析
     *      .log文件中存储具体的数据
     *      .index文件维护了数据的相对offset，这样能够使得offset的值不会占用很大的空间；
     *          另外，.index文件还维护了数据的稀疏索引，这样的设计能够在降低索引维护成本的同时提高数据查询的效率
     *          每当.log文件添加4K数据，.index文件就会维护一条索引
     *
     *      相关参数：
     *          > log.segment.bytes             每个segment的大小，默认是1G
     *          > log.index.interval.bytes      .index维护索引的稀疏度，默认值为4K，即.log文件每添加4K数据，.index维护一条索引
     *
     *  5.4.2 文件清理机制
     *      segment中的.timeindex文件维护了数据的时间戳，时间戳分为两类，CreateTime和LogAppendTime
     *          > CreateTime表示producer创建这条消息的时间
     *          > logAppendTime表示broker接收到这条消息的时间(严格来说，是leader broker将这条消息写入到log的时间)
     *
     *      引入时间戳主要为了解决三个问题：
     *          > 日志保存(log retention)策略，Kafka默认间隔7天会删除过期日志。判断依据就是比较
     *              日志段文件(log segment file)的最新修改时间(last modification time)，
     *              如果最近一次修改发生于7天前，那么就会视该日志段文件为过期日志，执行清除操作
     *
     *          > 日志切分(log rolling)策略：与日志保存是一样的道理。当前日志段文件会根据规则对当前日志进行切分，
     *              即，创建一个新的日志段文件，并设置其为当前激活(active)日志段。
     *              其中有一条规则就是基于时间的(log.roll.hours，默认是7天)，即当前日志段文件的最新一次修改发生于7天前的话，
     *              就创建一个新的日志段文件，并设置为active日志段。
     *
     *          > 流式处理(Kafka streaming)：流式处理中需要用到消息的时间戳
     *
     *      数据清理策略有两种：
     *          > delete，删除数据，将所有需要清理的数据都删除
     *          > compact，数据压缩，保留相同key中最新的那个value，其余的value都删除
     *          配置参数：log.cleanup.policy  默认值为delete，表示默认删除需要清理的数据
     *
     *      数据清理单位有两种：
     *          > 以segment文件为单位，删除数据时，将整个segment文件删除
     *          > 以数据为单位，删除数据时，只删除满足清理条件的数据。使用场景为，一个segment文件中，一部分数据需要删除
     *
     *      数据清理条件有两种：基于segment删除
     *          > 基于时间：Kafka默认清理条件，以segment中，所有数据中，最大时间戳为该segment文件的时间戳
     *          > 基于文件大小：当所有的segment大小之后超过设置的大小，删除时间最早的segment
     *
     */

    /**
     *  5.5 broker高效数据读写
     *      1、Kafka本身是分布式集群，又采用了数据分区存储方式，数据并行读写，效率高
     *      2、Kafka采用顺序写磁盘的方式进行数据存盘，即写磁盘过程，数据一直向文件末尾追加，写数据效率高
     *      3、Kafka维护了数据的稀疏索引，在数据消费时，效率高
     *      4、页缓存技术和零拷贝技术，减少了数据IO次数，提升了数据传输效率
     *          > 页缓存技术：在操作系统向硬盘写入数据时，会先将数据保存在页缓存中，页缓存再向硬盘中写入数据
     *                      同样，操作系统读取数据时，先在页缓存中查找，如果找不到再去硬盘中查找。
     *                      实际上，页缓存是尽可能多的把空闲内存都当作磁盘缓存来使用
     *          > 零拷贝技术：在Kafka中，数据的处理操作都交给生产者和消费者，broker只进行数据存储，
     *                      因此，在消费者消费数据时，存储在硬盘中的数据不需要重新添加到broker中，直接从页缓存通过网络传输到消费者
     *
     */

    /**
     *  5.6 broker常用配置参数
     *      > replica.lag.time.max.ms           follower与leader通信或数据同步请求的时间间隔，超过时间间隔未发生通信，
     *                                          将follower踢出ISR列表。默认时间30s
     *
     *      > auto.leader.rebalance.enable      是否允许定期进行Leader选举，默认值为true。
     *                                          该参数表示，当满足一定条件时，重新选举，更换leader，就是以前有leader，
     *                                          换一个leader。
     *
     *      > leader.imbalance.per.broker.percentage    当broker集群的不平衡率达到10%时，重新选举leader，更换以前的leader。
     *                                                  默认值10%
     *
     *              需要说明的是，频繁地更换leader会产生很多不必要的性能开销，而不开启leader重选举可能造成一些问题
     *
     *      > leader.imbalance.check.interval.seconds   检查broker集群的不平衡率的时间间隔，默认值300s
     *
     *      > log.segment.bytes     segment文件大小配置项，默认值1G
     *
     *      > log.index.interval.bytes      .index文件中稀疏索引的稀疏度，默认值4K
     *
     *      > log.retention.hours       log数据保存时长，默认值7天
     *
     *      > log.retention.check.interval.ms   检查数据保存时间是否超时，默认值5min
     *
     *      > log.retention.bytes   当所有segment文件大小之和超过设置的值，删除最早的segment文件，默认值为-1，表示无穷大
     *              segment文件的时间以其中保存的最新的数据为其文件的时间，最新的数据即时间戳最大的数据
     *
     *      > log.cleanup.policy    数据清理的策略，默认值delete，表示直接删除数据
     *
     */

}
