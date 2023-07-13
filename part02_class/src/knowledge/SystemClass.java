package knowledge;

public class SystemClass {
    /**
     * System类代表系统，包含很多系统及的属性和方法，位于java.lang包
     *  Syetem类构造器私有化，无法创建对象；属性及方法都时static，可以直接调用
     *
     *  属性：
     *      System类包含in、out、err三个属性，分别代表标准输入流，标准输出流和标准错误输出流
     *
     *  方法:
     *      nativa long currentTimeMillis():返回当前系统时间。即格林威治1970年1月1日0时0分0秒至今所间隔的毫秒数
     *      void exit(int status):调用此方法时，退出程序（虚拟机），status为0时，表示正常退出，非零表示异常退出
     *          该方法可以在图形化界面编程中实现程序的退出功能
     *      void gc():调用此方法的目的是请求系统进行垃圾回收。至于是否立刻回收，取决于系统的垃圾回收算法以及系统执行时的情况
     *      String getProperty(String key):该方法可以根据传入的key值获取系统属性
     *          java.version    -->     Java运行时环境版本
     *          java.home       -->     java安装目录
     *          os.name         -->     操作系统名称
     *          os.version      -->     操作系统版本
     *          user.name       -->     用户的账户名称
     *          user.home       -->     用户的主目录
     *          user.dir        -->     用户的当前工作目录
     */
}
