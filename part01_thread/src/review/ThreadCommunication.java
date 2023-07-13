package review;

/**
 * Author: shaco
 * Date: 2022/10/22
 * Desc: 线程间通信，消费者与生产者问题
 */
public class ThreadCommunication {
    public static void main(String[] args) {
        Clerk1 clerk1 = new Clerk1();

        // 创建生产线程
        Productor1 productor1 = new Productor1(clerk1);
        productor1.start();

        // 创建消费线程
        Consumer1 consumer1 = new Consumer1(clerk1);
        new Thread(consumer1).start();
    }
}

class Clerk1 {
    private int amount = 10;

    public synchronized void produce() { // 锁是Clerk类的对象
        if (amount < 20) {
            amount++;
            System.out.println("生产第" + amount + "个产品");
            // 当生产过一次产品就可以唤醒消费线程
            notify();
        } else {
            // 此时需要等待消费线程消费至少一个产品
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void consumer() { // 锁是Clerk类的对象
        if (amount > 0) {
            System.out.println("消费第" + amount + "个产品");
            amount--;
            // 当消费了至少一个产品后就可以唤醒生产线程
            notify();
        } else {
            // 此时需要等待生产线程生产产品
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Productor1 extends Thread {
    private Clerk1 clerk1;

    Productor1(Clerk1 clerk1) {
        this.clerk1 = clerk1;
    }

    @Override
    public void run() {
        while (true) {
            clerk1.produce();
        }
    }
}

class Consumer1 implements Runnable {
    public Clerk1 clerk1;

    Consumer1(Clerk1 clerk1) {
        this.clerk1 = clerk1;
    }

    @Override
    public void run() {
        while (true) {
            clerk1.consumer();
        }
    }
}