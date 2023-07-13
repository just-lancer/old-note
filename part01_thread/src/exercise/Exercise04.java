package exercise;
// 生产者和消费者问题
// 生产者(Productor)将产品交给店员(Clerk)，而消费者(Customer)从店员处取走产品
// 店员一次只能持有固定数量的产品（比如：20）,如果生产者试图生产更多的产品，店员会叫停一下
// 如果店中有空位放产品了再通知生产者继续生产产品。如果殿中没有产品了，店员会告诉消费者等一下，如果店中有了产品再通知消费者来取走产品
public class Exercise04 {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Productor p1 = new Productor(clerk);
        Customer c1 = new Customer(clerk);

        p1.setName("生产者");
        c1.setName("消费者");

        p1.start();
        c1.start();
    }
}

class Clerk{
    int productQuantity = 10;

    public int getProductQuantity() {
        return productQuantity;
    }

    // 增加产品数量
    public synchronized void addQuantity(){
        if (productQuantity < 20){
            productQuantity++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("生产者生产了一个产品，当前有" + productQuantity +"个产品");
            notify();
        }
        else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void expendProductQuantity(){
        if (productQuantity > 0){
            productQuantity--;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("消费者消费了一个产品，当前有" + productQuantity +"个产品");
            notify();
        }
        else{
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Productor extends Thread{
    Clerk clerk;
    public Productor(Clerk clerk){
        this.clerk = clerk;
    }
    public void run(){
        while(true){
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.addQuantity();
        }
    }
}

class Customer extends Thread{
    Clerk clerk;
    public Customer(Clerk clerk){
        this.clerk = clerk;
    }
    public void run(){
        while(true){
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.expendProductQuantity();
        }
    }
}
