package review;

/**
 * Author: shaco
 * Date: 2022/10/16
 * Desc: 创建多线程方式二：实现Runnable接口
 */
// TODO 1、创建子类实现Runnable接口
public class Create_Thread_Way_Second implements Runnable {
    // TODO 2、重写接口中run()方法
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        // TODO 3、创建Runnable接口实现类的对象
        Create_Thread_Way_Second create_thread_way_second = new Create_Thread_Way_Second();

        // TODO 4、创建Thread类对象，并将Runnable实现类对象作为构造器的参数
        Thread thread = new Thread(create_thread_way_second);

        // TODO 5、调用start()方法启动多线程
        thread.start();

    }
}
