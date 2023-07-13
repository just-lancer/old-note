package knowledge;
// 利用同步代码块解决继承Thread类产生的线程安全问题
public class MyThreadCode03 {
    public static void main(String[] args) {
        Windows3 w1 = new Windows3();
        Windows3 w2 = new Windows3();
        Windows3 w3 = new Windows3();

        w1.setName("继承方式窗口1");
        w2.setName("继承方式窗口2");
        w3.setName("继承方式窗口3");

        w1.start();
        w2.start();
        w3.start();
    }
}

class Windows3 extends Thread{
    public static int tickets = 1000;
//    private static Object  obj = new Object();
    public void run(){
        while (true) {
            synchronized ("obj"){
                if (tickets > 0){
                    try {
                        sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(getName() + ":卖票，票号为：" + tickets);
                    tickets--;
                }
                else{
                    break;
                }
            }
        }
    }
}
