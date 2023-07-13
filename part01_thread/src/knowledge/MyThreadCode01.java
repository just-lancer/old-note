package knowledge;
// 两种方式创建多线程
public class MyThreadCode01 {
    public static void main(String[] args) {
        MyThread1 m1 = new MyThread1();

        MyThread2 m2 = new MyThread2();
        Thread t1 = new Thread(m2);

        m1.setName("偶数线程");
        t1.setName("奇数线程");

        m1.start();
        t1.start();
    }
}

class MyThread1 extends Thread {
    // 线程1：遍历1~100以内的偶数
    public void run(){
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0){
                System.out.println(getName() + ":" + i);
            }
        }
    }
}

class MyThread2 implements Runnable{
    public void run(){
        for (int i = 0; i < 100; i++) {
            if (i % 2 != 0){
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }
}
