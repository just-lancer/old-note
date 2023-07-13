package knowledge;

// 三个窗口买票。用以示例线程安全问题
public class MyThreadCode02 {
    public static void main(String[] args) {
        Windows1 w1 = new Windows1();
        Windows1 w2 = new Windows1();
        Windows1 w3 = new Windows1();

        w1.setName("继承方式窗口1");
        w2.setName("继承方式窗口2");
        w3.setName("继承方式窗口3");

        Windows2 w4 = new Windows2();
        Windows2 w5 = new Windows2();
        Windows2 w6 = new Windows2();

        Thread t1 = new Thread(w4);
        Thread t2 = new Thread(w5);
        Thread t3 = new Thread(w6);

        t1.setName("实现方式窗口A");
        t2.setName("实现方式窗口B");
        t3.setName("实现方式窗口C");

        w1.start();
        w2.start();
        w3.start();

//        t1.start();
//        t2.start();
//        t3.start();
    }
}

class Windows1 extends Thread {
    public static int tickets = 100;

    public void run() {
        while (true) {
            if (tickets > 0) {
                System.out.println(getName() + ":卖票，票号为：" + tickets);
                tickets--;
            } else {
                break;
            }
        }
    }
}

class Windows2 implements Runnable {
    public static int tickets = 200;

    public void run() {
        while (true) {
            if (tickets > 100) {
                System.out.println(Thread.currentThread().getName() + ":卖票，票号为：" + tickets);
                tickets--;
            } else {
                break;
            }
        }
    }
}