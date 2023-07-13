package Utils;

import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;

import java.util.Random;

public class ParallelClicks implements ParallelSourceFunction<Event> {
    Random random = new Random();
    Boolean flag = true;
    String[] names = new String[]{"zhangsan", "lisi", "wangwu", "zhaoliu"};
    String[] pages = new String[]{"/home", "/cart", "/sku_info", "/payment"};

    @Override
    public void run(SourceContext ctx) throws Exception {
        while (flag) {
            String name = names[random.nextInt(names.length)];
            String page = pages[random.nextInt(pages.length)];

            long l = random.nextLong();
            ctx.collect(new Event(name, page, l));

            Thread.sleep(1000L);
        }
    }

    @Override
    public void cancel() {
        flag = false;
    }
}
