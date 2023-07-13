package kafkaknowledge;

public class Consumer {
    /**
     *  6、消费者
     *      6.1 消息队列数据消费方式
     *      6.2 消费者组数据消费
     *          6.2.1 消费者组数据消费原则
     *          6.2.2 消费者组数据消费策略
     *          6.2.3 消费者组初始化流程
     *          6.2.4 消费者数据消费流程
     *      6.3 数据消费offset
     *          6.3.1 offset自动提交
     *          6.3.2 offset手动提交
     *          6.3.3 指定offset进行数据消费
     *          6.3.4 消息的重复消费和漏消费
     *      6.4 消费者常用配置参数
     *      6.5 消费者优化
     *
     */

    /**
     *  6.1 消息队列数据消费方式
     *      以Kafka为例，数据消费分两种方式：
     *          > pull模式：即消费者主动去消息队列中拉取数据，这是Kafka采用的数据消费方式
     *                      模式优点：能够让所有的消费者都能按自己的消费速度消费数据
     *                      模式缺点：当消息队列中没有数据时，消费者会一直拉取空数据
     *          > poll模式：即消息队列主动向消费者推送消息
     *                      模式优点：当消息队列没有数据时，消费者不会获取空数据
     *                      模式缺点：无法适应所有消费者消费速度，会造成线程阻塞
     *
     */

    /**
     *  6.2 消费者组初始化流程
     *      消费者组初始化需要确定哪些消费者构成一个消费者组，以及消费者组中消费者消费分区数据的计划
     *      这两项工作，需要一个协调者(coordinator)来协助进行，此时的coordinator的地位相当于zookeeper
     *
     *      Coordinator一般指的是运行在broker上的group Coordinator，用于管理Consumer Group中各个成员，
     *      每个KafkaServer都有一个GroupCoordinator实例，管理多个消费者组，主要用于offset位移管理和Consumer Rebalance。
     *
     *      1、coordinator的确定：消费者组组ID的hashcode值对50（_consumer_offsets主题有50个分区）取余，所得到的值对应的
     *          _consumer_offsets主题的分区在哪个Kafka节点上，该节点上的coordinator则用于管理消费者组
     *
     *      2、消费者组leader确定：具有相同消费者组ID的消费者会在coordinator确定后，将消费请求发送给coordinator，
     *          coordinator在所有发送请求的消费者中选择一个消费者作为消费者leader，并将所要消费的主题信息发送给leader，
     *          随后，leader指定主题的消费方案，并发送给coordinator，coordinator将消费方案发送给消费者组中的每一个消费者，
     *          消费者接收到消费方案，就会开始数据消费
     *
     *      3、正常情况下，消费者会与coordinator保持心跳，每隔3 s产生一次通信。当消费者出现45 s内没有保持心跳时，
     *          coordinator会认为该消费者挂了，会将其移除当前消费者组。此时，该消费者所对应的分区数据，会由其他消费者进行
     *          消费，即出现再平衡，这个工作也是coordinator来处理。除此之外，当消费者消费数据过长（默认5分钟），也会出现再平衡。
     *
     */

    /**
     *  6.2 消费者组数据消费
     *      基本流程：生产者 --[发布]--> broker <--[拉取]-- 消费者
     *
     *      每个消费者都有一个offset用于记录数据消费位置，offset由Kafka的系统主题_consumer_offsetss进行维护，该主题有50个分区
     *
     *  6.2.1 消费者组数据消费原则：
     *      > 任何一个消费者都属于一个消费者组，有的消费者组有多个消费者，有的消费者组只有一个消费者
     *      > 消费者组内，不同消费者不能消费同一个分区的数据，一个消费者能够消费不同分区的数据
     *      > 消费者组与消费者组之间的数据消费不冲突，即消费者组A中的消费者能够和消费者组B中的消费者同时消费同一个分区的数据
     *
     *  6.2.2 消费者分区数据消费策略：
     *      Kafka有四种主流的分区分配策略：Range、RoundRobin、Sticky、CooperativeStick
     *
     *      1、Range分区分配策略：针对一个主题进行分区分配
     *          Range分区策略在制定分区分配计划前，会对主题分区和消费者组的消费者进行编号，如7个分区，3个消费者
     *          partition0      partition1      partition2      partition3      partition4      partition5      partition6
     *          consumer0       consumer1       consumer2
     *
     *          首先计算每个消费者应该消费几个分区，如果除不尽，那么序号靠前的消费者会多消费一个分区，即：
     *          consumer0 : [partition0, partition1, partition2]
     *          consumer1 : [partition3, partition4]
     *          consumer2 : [partition5, partition6]
     *
     *          在消费者挂掉的45s内，在这45s内，本该是该消费者的消费任务会整体随机分配给另一个存活的消费者
     *          在消费者挂掉的45s后，说明消费者退出该消费者组，生产者发送的消息会以消费者组现存的消费者进行Range分配
     *
     *          Range分配策略的缺点是：当Kafka集群中只有一个主题，那顺序靠前的消费者负担还好，随着主题的数量增加，排序靠前的
     *          消费者的负担会越来越重，形成“数据倾斜”的问题
     *
     *      2、RoundRobin分区分配策略：针对所有主题而言
     *          RoundRobin采用轮询的方式进行分区分配，将所有主题的所有分区和所有的消费者都列出来，然后进行排序，最后，通过轮询进行分区分配
     *          主题T0，有三个分区P0-0, P0-1, P0-2
     *          主题T1，有两个分区P1-0, P1-1
     *          主题T2，有四个分区P2-0, P2-1, P2-2, P2-3
     *          消费者C0，订阅主题T0，T1
     *          消费者C1，订阅主题T1，T2
     *          消费者C2，订阅主题T2，T0
     *
     *          P0-0 --> P0-1 --> P0-2 --> P1-0 --> P1-1 --> P2-0 --> P2-1 --> P2-2 --> P2-3
     *          C0 --> C1 --> C2
     *          分配方式：
     *          P0-0分配给C0
     *          P0-1分配给C1，但是C1没有订阅T0，所以将P0-1分配给C2
     *          P0-2分配给C0
     *          P1-0分配给C1
     *          P1-1分配给C2，C2没有订阅T1，所以P1-1分配给C0
     *          P2-0分配给C1
     *          P2-1分配给C2
     *          P2-2分配给C0，C0没有订阅T2，所以P2-2分配给C1
     *          P2-3分配给C2
     *          所以有：
     *          C0 : P0-0, P0-2, P1-1
     *          C1 : P1-0, P2-0, P2-2
     *          C2 : P0-1, P2-1, P2-3
     *
     *          当消费者挂掉，在45s内，挂掉的消费者的分区任务会按照轮询的方式继续分配给其他的消费者
     *          而当消费者挂掉45s后，coordinator会进行分区的再平衡
     *
     *      3、Sticky分区分配策略：
     *          首先会尽量均衡的放置分区到消费者上面，在出现同一消费者组内消费者出现问题的时候，会尽量保持原有分配的分区不变化。
     *          partition0      partition1      partition2      partition3      partition4      partition5      partition6
     *          consumer0       consumer1       consumer2
     *
     *          三个消费者会尽可能的均匀的获取分区任务，区别于Range的顺序分配，Sticky是随机进行分配的，例如
     *          consumer0 : partition0  partition1
     *          consumer1 : partition4  partition5  partition6
     *          consumer2 : partition2  partition3
     *
     *          当消费者挂掉，在45s内，挂掉的消费者的分区任务也会随机的均匀的分配到其他消费者中
     *          而在消费者挂掉45s后，会进行再平衡：保持活着的消费者的分区任务不变，挂掉的消费者的分区任务均匀的随机的分配给其他消费者
     *
     *      4、
     *
     *  6.2.3 消费者组初始化流程
     *      消费者组初始化需要确定哪些消费者构成一个消费者组，以及消费者组中消费者消费分区数据的计划
     *      这两项工作，需要一个协调者(coordinator)来协助进行，此时的coordinator的地位相当于zookeeper
     *
     *      Coordinator一般指的是运行在broker上的group Coordinator，用于管理Consumer Group中各个成员，
     *      每个KafkaServer都有一个GroupCoordinator实例，管理多个消费者组，主要用于offset位移管理和Consumer Rebalance。
     *
     *      1、coordinator的确定：消费者组组ID的hashcode值对50（_consumer_offsets主题有50个分区）取余，所得到的值对应的
     *            _consumer_offsets主题的分区在哪个Kafka节点上，该节点上的coordinator则用于管理消费者组
     *
     *      2、消费者组leader确定：具有相同消费者组ID的消费者会在coordinator确定后，将消费请求发送给coordinator，
     *           coordinator在所有发送请求的消费者中选择一个消费者作为消费者leader，并将所要消费的主题信息发送给leader，
     *           随后，leader指定主题的消费方案，并发送给coordinator，coordinator将消费方案发送给消费者组中的每一个消费者，
     *           消费者接收到消费方案，就会开始数据消费
     *
     *      3、正常情况下，消费者会与coordinator保持心跳，每隔3 s产生一次通信。当消费者出现45 s内没有保持心跳时，
     *           coordinator会认为该消费者挂了，会将其移除当前消费者组。此时，该消费者所对应的分区数据，会由其他消费者进行
     *           消费，即出现再平衡，这个工作也是coordinator来处理。除此之外，当消费者消费数据过长（默认5分钟），也会出现再平衡。
     *
     *  6.2.4 消费者数据消费流程
     *      在消费者组初始化流程结束后，消费者开始消费数据
     *
     *      1、消费者组首先创建一个消费者网络连接客户端，用于连接Kafka集群。同时将消费者 数据拉取 配置信息添加其中，包括：
     *          > fetch.min.byte：每批次最小抓取大小，默认1字节
     *          > fetch.max.wait.ms：消费者抓取数据的间隔时间，默认500ms
     *          > fetch.max.byte：每批次最大抓取大小，默认50M
     *
     *      2、建立连接后，消费者发送数据拉取请求到Kafka集群的leader中，leader通过回调函数将数据发送给消费者，数据在消费者中
     *          保存在消息队列中，随后对数据进行处理：
     *              > 首先，进行数据的反序列化
     *              > 随后，经过拦截器处理
     *              > 最后就形成了正常的数据
     *
     */

    /**
     *  6.3 数据消费offset
     *      offset用于标记消费者消费数据的偏置，在Kafka 0.9版本之前，该数据维护在zookeeper中，在Kafka 0.9版本之后，该数据维护在
     *      Kafka的系统主题_consumer_offsets中。
     *
     *      __consumer_offsets主题里面采用key和value的方式存储数据。key是group.id+topic+分区号，value就是当前offset的值。
     *      每隔一段时间，kafka内部会对这个topic进行compact，也就是每个group.id+topic+分区号就保留最新数据。
     *
     *  6.3.1 offset自动提交
     *      配置参数：
     *          > enable.auto.commit    默认值为true，表示默认打开自动提交
     *          > auto.commit.interval.ms   表示Kafka提交offset的时间间隔，默认值5s，表示每隔5s提交一次offset
     *
     *  6.3.2 offset手动提交
     *      手动提交offset分为同步提交和异步提交，两者的相同点是，都会将本次提交的一批数据最高的偏移量提交；
     *      不同点是，同步提交阻塞当前线程，一直到提交成功，并且会自动失败重试（由不可控因素导致，也会出现提交失败）；
     *      而异步提交则没有失败重试机制，故有可能提交失败。
     *
     *  6.3.3 指定offset进行数据消费
     *      当Kafka中没有初始偏移量（消费者组第一次消费）或服务器上不再存在当前偏移量时（例如该数据已被删除），消费者如何开始消费？
     *      配置参数：auto.offset.reset = earliest | latest | none
     *      > earliest：自动将偏移量重置为最早的偏移量，即从头开始消费
     *      > latest（默认值）：自动将偏移量重置为最新偏移量
     *      > none：如果未找到消费者组的先前偏移量，则向消费者抛出异常
     *
     *  6.3.4 消息的重复消费和漏消费
     *      消息的重复消费：
     *          出现的原因是offset的自动提交。当消费者消费一条消息，并且未达到offset提交的条件时，消费者挂了，再次重启，
     *          消费者会从上一次offset开始消费，那么会出现重复消费的问题
     *
     *      消息的漏消费：
     *          出现的原因是offset的手动提交。当消费者消费一条消息，并提交offset时，在消费者处理消息的过程中，消费者挂了，
     *          消息未消费完成，再次启动消费者，会出现消息漏消费。
     *
     *      解决消息的重复消费和漏消费，需要将消息的消费过程和offset的提交过程整合成一个事务。
     *
     */

    /**
     *  6.4 消费者常用配置参数
     *      > bootstrap.servers         向Kafka集群建立初始连接用到的host/port列表。
     *      > key.serializer            指定发送消息的key的序列化类型。一定要写全类名
     *      > value.serializer          指定发送消息的value的序列化类型。一定要写全类名
     *      > group.id                  标记消费者所属的消费者组
     *
     *      > enable.auto.commit        是否开启offset自动提交，默认值true
     *      > auto.commit.interval.ms   自动提交offset的时间间隔，默认5s
     *      > auto.offset.reset         当消费者没有初始offset或者offset丢失，offset的处理方式
     *                                  > earliest：自动将偏移量重置为最早的偏移量，即从头开始消费
     *                                  > latest（默认值）：自动将偏移量重置为最新偏移量
     *                                  > none：如果未找到消费者组的先前偏移量，则向消费者抛出异常
     *      > offsets.topic.num.partitions      _consumer_offsets主题的默认分区数
     *      > heartbeat.interval.ms     消费者与coordinator的心跳频率，默认3s
     *      > session.timeout.ms        消费者与coordinator连接的超时时间，默认45s
     *      > max.poll.interval.ms      消费者消费单批次数据的最长时间，默认值5min
     *      > fetch.min.bytes           消费者抓取一个批次数据的最小容量，默认1字节
     *      > fetch.max.bytes           消费者抓取一个批次数据的最大容量，默认1字节
     *      > fetch.max.wait.ms         消费者抓取数据的等待时间，默认500ms
     *      > max.poll.records          消费者从网络连接客户端的队列中，抓取一次数据所包含消息的条数，默认值500
     */

    /**
     *  6.5 消费者优化
     *      消费者端的主要问题是消息积压问题
     *      一方面，生产者生产速度太快，会导致Kafka broker出现消息积压
     *      另一方面，消费者消费消息的速度太慢，也会导致Kafka broker出现消息积压
     *
     *      解决方案：
     *          解决消息生产速度过快：
     *          > 增加主题的分区数量，同时增加消费者的数量，以提升消息的并行度
     *
     *          解决消息消费速度过慢：
     *          > 合理设置消费者配置参数，如，一批次数据量大小，数据条数等
     *
     */

}
