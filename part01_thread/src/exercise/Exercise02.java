package exercise;
// 银行有一个账户，有两个储户分别想同一个账户存3000元，每次存1000，存3此。
// 每次存完打印账户余额
// 该程序是否有线程安全问题，如果有，如何解决？
public class Exercise02 {
    public static void main(String args[]){
        Account a = new Account();
        User u1 = new User(a);
        User u2 = new User(a);
        u1.start();
        u2.start();
    }
}

class Account{
    long balance = 0;

    // 存钱
    public synchronized void addBalance(long money){
        balance += money;
//        System.out.println(Thread.currentThread().getName() + "存钱完毕，现有" + balance + "元");
    }
}

class User extends Thread{
    Account acc;

    public User(Account acc){
        this.acc = acc;
    }

    public void run(){
        for (int i = 0; i < 3; i++) {
            // synchronized (this) { 这里用this当锁，不是唯一的，因为调用run()方法的对象分别是u1和u2
                acc.addBalance(1000);
                System.out.println(getName() + "存钱完毕，现有" + acc.balance + "元");
            // }
        }
    }
}
