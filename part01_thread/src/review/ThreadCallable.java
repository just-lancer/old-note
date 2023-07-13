package review;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Author: shaco
 * Date: 2022/10/22
 * Desc: 实现Callable创建多线程
 */
// TODO 1、创建线程类实现Callable接口
public class ThreadCallable implements Callable {
    // TODO 2、重写call方法，将线程需要运行的代码写入其中
    @Override
    public Object call() throws Exception {
        int sum = 0;
        // 1 ~ 100以内的偶数求和
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                System.out.println(i);
                sum = sum + i;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        // TODO 3、创建线程类的对象
        ThreadCallable threadCallable = new ThreadCallable();
        // TODO 4、创建FutureTask类的对象，并将线程类对象作为构造器参数传入
        FutureTask futureTask = new FutureTask(threadCallable);
        //TODO 5、创建多线程，并将FutureTask类的对象作为沟槽其参数传入
        Thread thread = new Thread(futureTask);
        // TODO 6、启动线程
        thread.start();

        // TODO 额外的，通过FutureTask类的对象，我们可以通过调用get()方法获取线程运行结果，也就是call()方法的返回值
        Object o = null;
        try {
            o = futureTask.get();
            System.out.println("总和为" + o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}


