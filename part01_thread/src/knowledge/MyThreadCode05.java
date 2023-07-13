package knowledge;
// 利用同步方法解决继承Thread类创建多线程的线程安全问题
public class MyThreadCode05 {
    public static void main(String[] args) {
        Windows5 w1 = new Windows5();
        Windows5 w2 = new Windows5();
        Windows5 w3 = new Windows5();

        w1.setName("继承方式窗口1");
        w2.setName("继承方式窗口2");
        w3.setName("继承方式窗口3");

        w1.start();
        w2.start();
        w3.start();
    }
}

class Windows5 extends Thread{
    public static int tickets = 20;
    //    private static Object  obj = new Object();
    public void run(){
        while (true) {
            show();
        }
    }

    private static synchronized void show(){
        if (tickets > 0) {
            System.out.println(Thread.currentThread().getName() + ":卖票，票号为：" + tickets);
            tickets--;
        }
    }
}
