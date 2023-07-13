package review;

/**
 * Author: shaco
 * Date: 2022/10/16
 * Desc: 线程安全问题举例：卖票问题
 */
// 继承Thread类举例
public class Thread_Sync_Ticket_Extends extends Thread {
    private static int tickets = 100;
    private static boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            show();
        }
    }

    public static synchronized void show() {
        if (tickets > 0) {
            System.out.println(Thread.currentThread().getName() + "：卖第" + tickets + "票");
            tickets--;
        } else {
            flag = false;
        }
    }

    public static void main(String[] args) {
        // 创建三个窗口
        Thread_Sync_Ticket_Extends t1 = new Thread_Sync_Ticket_Extends();
        Thread_Sync_Ticket_Extends t2 = new Thread_Sync_Ticket_Extends();
        Thread_Sync_Ticket_Extends t3 = new Thread_Sync_Ticket_Extends();

        // 给窗口命名
        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");

        // 执行卖票过程
        t1.start();
        t2.start();
        t3.start();
    }
}
