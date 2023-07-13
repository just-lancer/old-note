package exercise;

// 创建两个分线程，其中一个线程输出1-100之间的偶数，另一个线程输出1-100之间的奇数
public class Exercise01 {
    public static void main(String[] args) {
        MyThread1 m1 = new MyThread1();
        m1.setName("偶数线程");

        MyThread2 m2 = new MyThread2();
        Thread t1 = new Thread(m2);
        t1.setName("奇数线程");

        m1.start();
        t1.start(); // 记住：谁调用start()方法，谁就是线程
    }
}

// 第一个线程，利用继承Thread类的方式创建线程
class MyThread1 extends Thread {
    public void run() {
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0) {
                System.out.println(getName() + ":" + i);
            }
        }
    }

}

class MyThread2 implements Runnable {
    public void run() {
        for (int i = 1; i <= 100; i++) {
            if (i % 2 != 0) {
                // 在这个地方只能使用Thread.currentThread().getName()的方式调用getName()这个方法
                // 原因是，用MyThread2这个类只是实现了Runnable接口，而它继承于Object这个类，在Object这个类中，没有getName()这个方法
                // 所以MyThread2这个类创建的对象不能直接调用getName()这个方法
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }

}
