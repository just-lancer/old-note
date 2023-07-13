package knowledge;
// 利用同步代码块解决实现Runnable接口实现多线程出现的线程安全问题
public class MyThreadCode04 {
    public static void main(String[] args) {
        Windows4 w4 = new Windows4();

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

class Windows4 implements Runnable{
    public static int tickets = 2000;
    public void run(){
        while (true) {
            synchronized (this) {
                if (tickets > 100){
                    System.out.println(Thread.currentThread().getName() + ":卖票，票号为：" + tickets);
                    tickets--;
                }
                else{
                    break;
                }
            }
        }
    }
}