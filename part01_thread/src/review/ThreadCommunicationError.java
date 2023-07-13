package review;

/**
 * Author: shaco
 * Date: 2022/10/16
 * Desc: 线程间的通信，生产者与消费者问题
 * declare: 一下代码出现了典型的错误，原因在于两个线程并没有操作共享数据，操作的都是各自类中的属性，只是这两个属性初始化之后的值相同
 */
public class ThreadCommunicationError {
    public int productAmount = 10;

    public static void main(String[] args) {
        ThreadCommunicationError pc = new ThreadCommunicationError();

        // 创建生产线程
        Productor productor = new Productor(pc.productAmount);
        productor.start();

        // 创建消费线程
        Consumer consumer = new Consumer(pc.productAmount);
        new Thread(consumer).start();
    }
}

class Productor extends Thread {
    public int amount;

    Productor() {
    }

    Productor(int amount) {
        this.amount = amount;
    }

    public synchronized void product() {
        if (amount < 20) {
            amount++;
            System.out.println("生产第" + amount + "个产品");
            // 当至少有一个产品之后就可以唤醒消费线程
            notify();
        } else {
            // 产品数已经到达20（上限），要等待消费线程进行消费
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            product();
        }
    }
}

class Consumer implements Runnable {
    public int amount;

    Consumer() {
    }

    Consumer(int amount) {
        this.amount = amount;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (amount > 0) {
                    System.out.println("消费第" + amount + "个产品");
                    amount--;
                    // 当消费了至少一个产品之后，就可唤醒生产者，进行生产
                    notify();
                } else {
                    // 产品已经全部消耗完了，需要等待生产线程进行产品生产
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}


