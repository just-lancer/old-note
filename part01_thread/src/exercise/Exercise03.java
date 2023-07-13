package exercise;
// 使用两个线程打印 1-100。线程1, 线程2 交替打印
public class Exercise03 {
    public static void main(String[] args) {
        MyThread m1 = new MyThread();
        MyThread m2 = new MyThread();

        m1.setName("线程1");
        m2.setName("线程2");

        m1.start();
       // m2.setJ(20);
        m2.start();
    }
}

class MyThread extends Thread{
    String s = "heh"; // 常量池
    Integer j = 128;
    int k[] = new int[1];
    public void run(){
        for (int i = 1; i <= 100; i++) {
            synchronized (j) {
                j.notifyAll();
                System.out.println(getName() + ":" + i);
                try {
                    if(i == 100){
                        break;
                    }
                    else{
                        j.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


