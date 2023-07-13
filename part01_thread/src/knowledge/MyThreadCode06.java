package knowledge;

import static java.lang.Thread.sleep;

// 利用同步方法解决实现Runnable创建多线程产生的线程安全问题
public class MyThreadCode06 {
    public static void main(String[] args) {
        Windows6 w4 = new Windows6();

        Thread t1 = new Thread(w4);
        Thread t2 = new Thread(w4);
        Thread t3 = new Thread(w4);

        t1.setName("实现方式窗口A");
        t2.setName("实现方式窗口B");
        t3.setName("实现方式窗口C");

        t1.start();
        t2.start();
        t3.start();
    }
}

class Windows6 implements Runnable{
    public static int tickets = 200;
    public void run(){
        while (true) {
            try {
                show();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void show() throws InterruptedException {
        if (tickets > 100){
            sleep(100);
            System.out.println(Thread.currentThread().getName() + ":卖票，票号为：" + tickets);
            tickets--;
        }
    }
}