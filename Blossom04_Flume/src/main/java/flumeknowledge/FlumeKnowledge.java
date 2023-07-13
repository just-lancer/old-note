package flumeknowledge;

public class FlumeKnowledge {
    /**
     *  Flume学习
     *  1、概述
     *  2、Flume基础架构
     *  3、Flume事务及内部原理
     *
     */

    /**
     *  1、概述
     *      Flume是Cloudera提供的一个高可用的，高可靠的，分布式的海量日志采集、聚合和传输的系统。
     *      Flume基于流式架构，灵活简单。
     *      Flume最主要的作用是，实时地读取服务器本地磁盘数据，并将其写入到HDFS中
     *
     */

    /**
     *  2、Flume基础架构
     *      Flume框架包含Agent核心组件，Agent核心组件包含三个组件：source、channel、sink
     *      Agent：是一个JVM进程，它将数据以事件的形式从源头传送到目的地
     *      source：负责将数据捕获后进行特殊的格式化，然后封装在Event中，再将数据推入到Channel中。
     *              基于数据有着不同的来源，source组件有着许多不同的实现类
     *      channel：连接source和sink的组件，是一个数据缓冲区。
     *              特点：能够允许source和sink运行在不同的速率上；线程安全，允许多个source和多个sink同时访问
     *              类型：Flume默认自带两种类型的channel，内存(memory)和磁盘(file)，也可以使用Kafka当作通道
     *      sink：从channel中取出后数据，然后分发到指定的地方，如HDFS、Kafka、HBase、其他Flume组件等
     *
     *      数据流：datasource ----> source ----> channel ----> sink ----> aimdir
     *
     *      事件(Event)：Flume中数据传输的最小单元，一条数据在Flume中会被封装成为一个Event进行传输。
     *                  一个Event包含数据头Header和数据体Body，Header以k-v键值对呈现，主要描述了该Event的属性；
     *                  Body以字节数组Array<byte>呈现，是数据的值。
     *
     *                  说明：Flume提供的不同source实现类会使得Event的Header出现不同的信息
     *
     *     事务(transaction)：Flume中，数据从source推入到channel，或者sink从channel中获取数据都有事务的存在。
     *                  事务可以理解成一个内存空间，存放着许多Event，当空间被Event存满时，事务会将空间中的所有Event一次性
     *                  推送给channel；同理，当sink从channel中将Event都取出来，并成功将数据写出到指定位置，那么channel中
     *                  一个事务的所有Event会被一次清空，而当数据写出失败时，会进行事务回滚。
     *
     */

    /**
     *  3、Flume事务及内部原理
     *  Flume事务：
     *      Flume使用两个独立的事务分别将数据从source推向channel以及sink从channel拉取数据
     *      put事务：source推送数据到channel
     *      take事务：sink在channel中拉取数据
     *
     *      在Flume中，数据被封装在一个事件中，一个数据对应一个Event
     *
     *      put事务：
     *          > source接受并处理数据，将数据封装为一个Event，并进行事件缓冲batch data，缓冲容量batchsize可以通过参数进行配置
     *              说明：有的source实现类支持配置batchsize参数配置，如Exec Source；有的实现类不支持batchsize参数配置，如NetCat TCP Source；
     *
     *          > 当batchsize被填满时，source执行doPut，将批事件写入事务的临时缓冲区putList中，事务临时缓冲区的大小可以通过transactioncapacity进行设置
     *          > 当事务临时缓冲区被填满，source执行doCommit，检查channel的剩余容量是否足够接收事务临时缓冲区的事件
     *              -- 当channel容量足够，source向channel推送数据；
     *              -- 当channel容量不够，source执行doRollback，回滚数据
     *
     *      take事务：与put事务一样，take事务从channel中获取的数据也是以Event为单位进行的，一次从channel中获取的数据量也为batchsize，较为特别的是，
     *          take事务每提取一次batchsize数据后，channel中相应的数据就会被删除，当take事务将一个transactioncapacity的数据全部取走，并成功写出到
     *          目的地后，channel中相应的数据彻底删除；当写出数据出现失败，那么take事务回滚，channel中的transactioncapacity数据恢复。
     *
     *          目的地由于数据已经部分写入，因此会出现数据重复的问题
     *
     *  Flume内部原理
     *      > source收集数据后，将其封装为Event，然后将数据提交给channel processor
     *      > channel processor将再Event事件传递给拦截器链interceptors，拦截器将数据处理后再返回给channel processor
     *      > channel processor将拦截过滤之后的Event事件传递给channel选择器(channel selector)处理，channel selector会返回Event事件的Channel列表
     *          -- channel selectors有两种类型：
     *              -- replicating channel selector(默认) : 将source过来的Events发往所有的channel（相当于复制多份）
     *              -- multiplexing channel selector：可以指定source发过来的Events发往的channel
     *      > channel processor根据channel选择器的选择结果，将Event事件写入相应的channel
     *
     *      > SinkProcess
     *              -- DefaultSinkProcessor(默认)，sink根据配置文件中的绑定信息，去指定的channal中获取数据
     *              -- LoadBalsor启动sink，sink在channel中去轮询，取出channel中的Event事件
     *                 -- SinkProcessor有三种类型：ancingSinkProcessor(负载均衡)、当多个sink对接一个channel时，多个sink会轮询到channel中获取数据
     *              -- FaioverSinkProcessor(容灾恢复)、故障转移，当多个sink中的一个sink出现故障时，多个sink中优先级最高的sink
     *                      马上接替上一个sink开始工作，优先级以一个int类型的整数定义。
     *
     *              规避原则：出现故障的sink恢复后不会马上投入使用，而是会被关“小黑屋”，静待一段times，期间，如果没有出现故障，
     *                      那么可以投入工作。当sink再次出现故障，恢复后，关“小黑屋”的时间会翻倍，即2*times，每次出现故障，
     *                      小黑屋时间都会翻倍。最多30s。
     *
     */
}
