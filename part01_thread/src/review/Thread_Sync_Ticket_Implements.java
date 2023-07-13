package review;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Author: shaco
 * Date: 2022/10/16
 * Desc: 线程安全问题举例：卖票问题。实现Runnable接口
 */
public class Thread_Sync_Ticket_Implements implements Runnable {
    private int tickets = 100;
    private boolean flag = true;
    ReentrantLock reentrantLock = new ReentrantLock();

    @Override
    public void run() {
        while (flag) {
            reentrantLock.lock();
            try {
                if (tickets > 0) {
                    System.out.println(Thread.currentThread().getName() + "：卖第" + tickets + "票");
                    tickets--;
                } else {
                    flag = false;
                }
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Thread_Sync_Ticket_Implements thread_sync_ticket_implements = new Thread_Sync_Ticket_Implements();

        Thread t1 = new Thread(thread_sync_ticket_implements, "窗口1");
        Thread t2 = new Thread(thread_sync_ticket_implements, "窗口2");
        Thread t3 = new Thread(thread_sync_ticket_implements, "窗口3");

        t1.start();
        t2.start();
        t3.start();
    }
}
