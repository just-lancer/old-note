/**
 * Author: shaco
 * Date: 2022/7/13
 * Desc: spark 内核源码
 */
public class sparkcore {
    /*
     * Driver进程的作用：初始化作业，解析程序，初始化两个调度器：DAGScheduler，TaskScheduler .
     *          ① 初始化作业： 判断路径是否存在，权限校验等
     *          ② DAGScheduler将程序的执行流程解析成DAG图，并划分阶段，根据阶段内的分区初始化Task
     *          ③ TaskScheduler接收Task，等待分配Task给executor
     *
     */

    /**
     *  一、spark-yarn-cluster任务提交流程
     *  1.1 脚本执行
     *  1.2 SparkSubmit进行参数解析，创建client客户端，并封装提交的参数和命令
     *  1.3 客户端向ResourceManager提交任务信息，并请求ApplicationMaster
     *  2、ResourceManager分配一个NodeManager节点，并运行ApplicationMaster，
     *      同时，在ApplicationMaster所在节点启动Drive进程，并开启一个YarnRMClient，用于和ResourceManager通信
     *
     *  3、Driver进程执行程序，并初始化sparkContext，根据提交参数，能够确定任务执行所需要的资源。
     *      随后由yarnRMClient向ResourceManager申请程序运行所需资源，并准备运行Executor
     *
     *  4、ResourceManager接收到请求后，会返回资源列表，随后ApplicationMaster会启动一个线程池，依据资源列表，寻找资源充足的NodeManager
     *
     *  5、在资源充足的NodeManager中，开启ExecutorBackend，准备资源，并启动一个RPC通信模块，该RPC通信模块会与Driver端的RPC通信模块进行通信，
     *      当收到Driver端通信模块的应答后，会真正的启动Executor进程，当Executor进程启动后，会通过RPC通信模块向Driver端进行注册，至此，
     *      计算资源全部准备完毕。
     *
     *  6、计算资源准备完毕后，Driver进程会初始化两个调度器：DAGScheduler和TaskScheduler
     *      DAGScheduler会将程序执行流程解析成DAG，并划分阶段和依据分区信息初始化Task
     *      TaskScheduler会将Task分配到不同的Executor，并实时监控Executor的运行状态
     *
     *  7、当Executor的任务运行完毕后，TaskScheduler会获得信息。当所有的Executor运行完成后，表示程序执行完成，向ResourceManager申请注销，释放资源
     *
     */

    /**
     * 二、spark通信架构：RPC通信
     *  >> RPC相关通信组件：
     *      -- RpcEndpoint：RPC端点 ，Spark针对于每个节点（Client/Master/Worker）都称之一个Rpc端点 ,且都实现RpcEndpoint接口，
     *          内部根据不同端点的需求，设计不同的消息和不同的业务处理，如果需要发送（询问）则调用Dispatcher
     *
     *      --  RpcEnv：RPC上下文环境，每个Rpc端点运行时依赖的上下文环境称之为RpcEnv
     *
     *      -- Dispatcher：消息分发器，针对于RPC端点需要发送消息或者从远程RPC接收到的消息，分发至对应的指令收件箱/发件箱。
     *          如果指令接收方是自己存入收件箱，如果指令接收方为非自身端点，则放入发件箱
     *
     *      -- InBox：指令消息收件箱，一个本地端点对应一个收件箱，Dispatcher在每次向Inbox存入消息时，
     *          都将对应EndpointData加入内部待Receiver Queue中，另外Dispatcher创建时会启动一个单独线程进行轮询Receiver Queue，
     *          进行收件箱消息消费
     *
     *      --  OutBox：指令消息发件箱，一个远程端点对应一个发件箱，当消息放入Outbox后，紧接着将消息通过TransportClient发送出去。
     *          消息放入发件箱以及发送过程是在同一个线程中进行，这样做的主要原因是远程消息分为RpcOutboxMessage,
     *          OneWayOutboxMessage两种消息，而针对于需要应答的消息直接发送且需要得到结果进行处理
     *
     *      -- TransportClient：Netty通信客户端，根据OutBox消息的receiver信息，请求对应远程TransportServer
     *
     *      -- TransportServer：Netty通信服务端，一个RPC端点一个TransportServer,接受远程消息后调用Dispatcher分发消息至对应收发件箱
     *
     *  >> RPC通信过程：
     *      -- spark每一个节点都是一个RPC通信节点，每隔RPC通信节点都有消息分发器（Dispatcher），都有指令消息收件箱（Inbox）、
     *          指令消息发件箱（OutBox）以及通信客户端和通信服务端
     *
     *
     */
}
