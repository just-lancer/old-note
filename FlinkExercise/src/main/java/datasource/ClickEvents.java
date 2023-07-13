package datasource;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Random;

public class ClickEvents implements SourceFunction<Event> {
    // 为自定义数据源，需要实现SourceFunction接口，该接口是flink所有数据源的基本接口，
    // 具有一个泛型，表示数据源中事件的类型
    // flink中，一个事件(event)就是一条数据

    boolean flag = true;

    // 每当当前类实例化时，总是会调用该方法
    @Override
    public void run(SourceContext<Event> ctx) throws Exception {
        String[] names = {"zhangsan", "lisi", "wangwu", "zhaoliu"};
        String[] urls = {"./home", "./cart", "./spu_sku", "./payment"};

        while (flag) {
            // 每隔1秒，发送一个对象
            Thread.sleep(1000);

            Random random = new Random();
            Event event = new Event(names[random.nextInt(4)], urls[random.nextInt(4)], System.currentTimeMillis());

            // 发送对象
            ctx.collect(event);
        }
    }

    @Override
    public void cancel() {
        // 每当结束事件发生时，会触发该函数执行
        flag = false;
    }
}
