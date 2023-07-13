package knowledge;
// 演示线程死锁的代码
public class DeadLock {
    public static void main(String[] args) {
        String s1 = "线程1：";
        String s2 = "线程2：";
        // 以下并不是真的锁死了
        if (false) {
            // 匿名子类对象
            new Thread() {
                public void run() {
                    synchronized (s1) {
                        System.out.print("我是");
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    synchronized (s2) {
                        System.out.println("阿高");
                    }
                }
            }.start();

            new Thread() {
                public void run() {
                    synchronized (s2) {
                        System.out.print("哈哈哈");
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    synchronized (s1) {
                        System.out.println("呵呵呵");
                    }
                }
            }.start();
        }

        // 以下是真正的锁死
        else{
            new Thread(){
                public void run(){
                    synchronized(s1){
                        System.out.println("是的，是的+++");
//                    System.out.print();
                        synchronized(s2){
                            System.out.println("犀牛，打死他");
//                        System.out.print();
                        }
                    }
                }
            }.start();

            new Thread(){
                public void run(){
                    synchronized(s2){
                        System.out.println("星星队长，变身。。。。");
    //                    System.out.print();
                        synchronized(s1) {
                            System.out.println("霸王龙，变身");
    //                        System.out.print();
                        }
                    }
                }
            }.start();
        }
    }
}