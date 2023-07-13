package knowledge;

public class ThreadClass {
    /**
     *  多线程学习
     *
     *  1、程序（program）：是为完成特定任务、用某种语言编写的一组指令的集合。也就是一段静态的代码
     *
     *     进程（process）：是程序的一次执行过程，或是正在运行的一个程序。是一个动态的过程，具有生命周期
     *          进程作为资源分配的单位，系统会为每个进程分配不同的内存区域，即方法区和堆空间
     *
     *     线程（thread）：进程可以进一步细化为线程，进程是一个程序内部的一条执行路线
     *          线程作为调度和执行的单位，每个线程拥有独立的运行栈和程序计数器
     *
     *  2、并行和并发
     *      并行：多个CPU同时处理执行多个进程
     *      并发：单个CPU同时处理多个进程（任务）
     *
     *  3、多线程的优点
     *      ① 提高应用程序的响应，对图形化界面更有意义，可以增强用户和的体验
     *      ② 提高计算机系统CPU的利用率
     *      ③ 改善程序的结构，将既长又复杂的进程分为多个线程，独立运行，便于理解和维护
     *
     *  4、多线程的使用场景
     *      > 程序需要同时执行多个任务
     *      > 程序需要实现一个等待任务时，如用户输入，文件读写
     *      > 需要一些后台运行程序时
     *
     *  5、创建多线程的两种方式（总共有四种方式）
     *
     *    > Java的JVM允许程序运行多个线程，多线程通过java.lang.Thread类来实现
     *      Thread类：
     *      构造器：
     *      Thread()：创建新的Thread对象
     *      Thread(String threadname)：创建线程并指明线程实例名
     *      Thread(Runnable target)：指定创建线程的目标对象，它实现了Runnable接口中的run()方法
     *      Thread(Runnable target, String name)：创建新的Thread对象
     *
     *    > 多线程的创建:
     *      方式一：继承Thread类
     *      > 创建一个继承于Thread的子类
     *      > 子类重写Thread中的run()方法，其方法体即为线程需要执行的代码
     *      > 创建子类对象
     *      > 利用子类对象调用start()方法
     *
     *      方式二：实现Runnable接口
     *      > 创建一个实现类去实现Runnable接口
     *      > 实现Thread中的run()方法，其方法体即为线程需要执行的代码
     *      > 创建实现类对象
     *      > 将实现类对象作为Thread类构造器的参数，创建Thread类的对象
     *      > 利用Thread的对象调用start()方法
     *
     *      两种创建多线程方法的比较
     *          开发中，优先选择实现Runnable接口的方法创建线程
     *          原因是：实现的方式没有类的单继承的局限性；实现的方式更适合处理多个线程贡献数据的情况
     *          联系：无论是继承Thread还是实现Runnable接口，二者都需要重写run()方法，将线程逻辑写入方法体中
     *          说明：Thread类也是继承于Runnable接口
     *
     *  6、Thread中常用的方法：
     *      > start()：启动当前线程；调用run()方法
     *      > run()：重写Thread类中的方法，将线程所需要执行的代码结构（代码逻辑）写入其中
     *      > currentThread()：静态方法，返回当前线程（一个对象）
     *      > set/getName()：设置/获取当前线程的名称
     *      > yield()：释放当前CPU的执行权。哪个线程调用此方法哪个线程就释放CPU资源，将资源让给优先级相同或者更高的线程，若队列中没有同优先级或者高优先级的线程，则忽略此方法
     *      > join()（会抛异常）：在线程a中，线程b调用join()方法，会使得线程a进入阻塞状态，
     *          进而线程b获取CPU执行权，待进程b执行完后，再执行进程a
     *      > sleep(long millis)（会抛异常）：当前线程进入睡眠，时常为millis毫秒
     *      > stop()：强制结束当前线程，不建议使用
     *      > isAlive()：判断当前线程是否存活
     *
     *  7、线程的优先级
     *      Thread类中的属性、方法及常量
     *      属性：priority --> 优先级
     *      常量：MAX_PRIORITY；NORM_PRIORITY；MIN_PRIORITY
     *      方法：set/getPriority()：设置/获取当前线程的优先级
     *      说明：线程创建时，会继承父线程的优先级；优先级不代表执行顺序，仅表示CPU执行线程的概率，优先级高，概率大，优先级小，概率小
     *
     *  8、线程的生命周期
     *      在Thread内部类state中，定义了线程的几种状态
     *      > 新建：当一个Thread类或者其子类对象被声明并创建是，新生的线程对象就处于新建状态
     *      > 就绪：处于新建状态的线程调用start()后，将进入线程队列等待CPU的时间片，此时该线程已具备运行条件，只是没有分配到CPU资源
     *      > 运行：当就绪的线程被调度并获得CPU资源时，便进入运行状态，run()方法定义的线程操作和功能开始执行
     *      > 阻塞：在某种特殊的情况下，被认为挂起活执行输入输出操作时，让出CPU并临时中止自己的执行，进入阻塞状态
     *      > 死亡：线程完成了它的全部工作或线程被提前强制性中止或出现异常导致结束
     *
     *  9、线程的同步：解决线程的安全问题
     *      线程的安全问题：多个线程操作共享数据时，当一个线程正在操作数据时，另一个线程也参与操作共享数据，此时会出现线程安全问题
     *      解决方案：当一个线程操作贡献数据时，禁止其他线程参与操作这些贡献数据，直到操作共享数据的线程运行结束，才允许其他线程操作
     *
     *      线程安全问题的解决方式：同步机制
     *      关键字：synchronized，adj 同步的
     *      ①方式一 同步代码块
     *          synchronized(同步监视器){
     *              需要被同步的代码
     *          }
     *
     *      说明：1、操作共享数据的代码，即为需要被同步的代码。-->不能包含代码多了，也不能包含代码少了。
     *           2、共享数据：多个线程共同操作的变量。
     *           3、同步监视器，俗称：锁。任何一个类的对象，都可以充当锁。所有对象都自动含有一把锁
     *          要求：多个线程必须要共用同一把锁。
     *
     *      补充：在实现Runnable接口创建多线程的方式中，我们可以考虑使用this充当同步监视器。
     *
     *      ②方式二 同步方法
     *          如果操作共享数据的代码完整的声明在一个方法中，我们不妨将此方法声明同步的。
     *          同步方法仍然涉及到同步监视器，只是不显式的声明
     *          对于非静态同步方法，其同步监视器是this，当前对象
     *          对于静态同步方法，其同步监视器是当前类本身
     *
     *      同步机制虽然解决了线程的安全问题，但是操作同步代码时，只能有一个线程参与，其他线程需要等待，相当于是单线程过程，所以效率会有所下降
     *
     *      ③方式三 手动添加锁lock
     *          JDK5.0开始，Java提供了一种通过显式定义同步锁对象来实现同步
     *          java.util.concurrent.locks.Look接口是控制多个线程对共享资源进行访问的工具
     *          java.util.concurrent.locks.Look接口有一个实现类ReentrantLock，它具有与synchronized相同的并发性和内存语义，
     *          在实现线程安全的控制中，可以显式加锁和释放锁
     *
     *          手动添加、释放锁的步骤
     *          class A extend Thread{
     *          // 1、创建实现类Reentrantlock的对象
     *          ReentrantLocks r = new ReentarntLocks();
     *          // 2、将需要同步的代码放入try{}finally{}代码块中，并在首行手动添加锁，即利用实现类ReentrantLocks的对象调用lock()方法
     *          public void run(){
     *              r.lock();
     *              try{
     *                  //需要同步的代码块
     *              }
     *          // 3、在finally代码块中释放锁，即利用实现类ReentrantLock的对象调用unlock()方法
     *              finally{
     *                  r.unlock();
     *              }
     *
     *      synchronized 与 Lock的对比
     *      相同点：都能处理线程安全问题
     *      不同点：
     *          > Lock是显式锁，synchronized是隐式锁，出了作用域，自动释放
     *          > Lock只有代码块锁，synchronized有代码块锁和方法锁
     *          > 使用Lock锁，JVM将花费更少时间来调度线程，性能更好。
     *
     *
     *  10、关于锁：
     *      释放锁的操作：
     *      > 当前线程的同步方法、同步代码块执行结束
     *      > 当前线程的同步方法、同步代码块遇到break、return等关键字，终止了该代码块、同步方法的执行
     *      > 当前线程的同步方法、同步代码块遇到未处理的异常或错误而结束
     *      > 当前线程的同步方法、同步代码块中执行了当前线程的wait()方法，当前线程进入阻塞状态，并释放锁
     *
     *      不会释放锁的操作：
     *      > 当前线程的同步方法、同步代码块中调用Thread.sleep()、Thread.yeild()方法
     *      > 线程执行同步代码块时，其他线程调用该线程的suspend()方法将该线程挂起，该线程不会释放锁
     *      注意：应该尽量避免使用suspend()和resume()来控制线程
     *
     *      死锁：
     *      产生原因：不同的线程分别占用对方所需的同步资源不放弃，都在等待对方放弃自己所需要的同步资源，就形成了线程的死锁
     *      出现死锁后，不会出现异常，也不会出现提示，只是所有的线程都处于阻塞状态，无法继续
     *
     *      解决方法：专门的算法、原则；尽量减少同步资源的定义；尽量避免嵌套同步
     *
     *  11、线程的通信
     *      以：使用两个线程打印 1-100。线程1, 线程2 交替打印，为例
     *
     *      涉及到的三个方法：
     *      wait():一旦执行此方法，当前线程就进入阻塞状态，并释放同步监视器。
     *      notify():一旦执行此方法，就会唤醒被wait的一个线程。如果有多个线程被wait，就唤醒优先级高的那个。
     *      notifyAll():一旦执行此方法，就会唤醒所有被wait的线程。
     *
     *      说明：
     *      1.wait()，notify()，notifyAll()三个方法必须使用在同步代码块或同步方法中。
     *      2.wait()，notify()，notifyAll()三个方法的调用者必须是同步代码块或同步方法中的同步监视器。
     *      否则，会出现IllegalMonitorStateException异常
     *      3.wait()，notify()，notifyAll()三个方法是定义在java.lang.Object类中。
     *
     *      面试题：sleep() 和 wait()的异同？
     *      1.相同点：一旦执行方法，都可以使得当前的线程进入阻塞状态。
     *      2.不同点：1）两个方法声明的位置不同：Thread类中声明sleep() , Object类中声明wait()
     *      2）调用的要求不同：sleep()可以在任何需要的场景下调用。 wait()必须使用在同步代码块或同步方法中
     *      3）关于是否释放同步监视器：如果两个方法都使用在同步代码块或同步方法中，sleep()不会释放锁，wait()会释放锁。
     *
     *  12、JDK5.0新增的两种创建多线程的方式
     *      第三种方式：实现Callable接口
     *      第四种方式：线程池
     *
     *  13、实现Callable接口创建多线程
     *      与实现Runnable接口对比，Callable更强一些：
     *         说明：Runnable的多线程代码写在run()方法中，Callable的多线程写在call()方法中
     *         相比run()方法，call()方法能够有返回值、支持泛型、能抛出异常
     *
     *         Callable接口实现多线程需要借助Future接口
     *          > Future接口可以对具体Runnable、Callable任务的执行结果进行取消，或者查询任务是否执行完成，获取结果等
     *          > FutereTask是Future接口的唯一实现类
     *          > FutureTask同时实现了Runnable、Callable接口
     *
     *         Callable接口实现多线程步骤：
     *         > 1、创建Callable接口的实现类，并在实现类中重写call()方法，写入所要执行的代码
     *         > 2、创建Callable接口实现类的对象
     *         > 3、将实现类的对象作为FutureTask构造器的参数，创建FutureTask的对象
     *         > 4、将FutureTask对象作为Thread类构造器的参数，创建Thread类的对象，
     *         > 5、利用Thread对象调用start()方法，开始多线程
     *         > 6、如果需要call()方法的返回值，那么利用FutureTask对象调用get()方法，即可获得返回值
     *
     *
     *  14、利用线程池创建对象
     *      概述：经常性地创建、销毁线程会消耗非常大的资源
     *          为了解决这个问题，我们可以提前创建好多个线程，放入线程池中，使用时直接获取，使用完再放回池子中，这样就可以减少线程创建和销毁的次数
     *
     *      线程池的优点：
     *          > 提高响应速度（降低了创建线程的时间）
     *          > 降低了资源的消耗（重复利用已有的线程，不用每次都创建）
     *          > 便于线程的管理：
     *              corePoolSize：线程池的大小
     *              maxmumPoolSize：最大线程数
     *              keepAliveTime：线程没有任务时，最多保持多长时间
     *
     *      JDK 5提供了线程池相关的API：ExecutorService和Executors
     *          executor：名词，执行者
     *
     */
}
