package review;

/**
 * Author: shaco
 * Date: 2022/10/16
 * Desc: 创建多线程第一种方式：继承Thread类
 */
// TODO 1、创建子类继承Thread类
public class Create_Thread_Way_First extends Thread {
    // TODO 2、重写Thread类中的run()方法
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        // TODO 3、创建Thread子类对象
        Create_Thread_Way_First create_thread_way_first = new Create_Thread_Way_First();
        // TODO 4、利用Thread子类对象调用start()方法，启动多线程
        create_thread_way_first.start();
    }
}
