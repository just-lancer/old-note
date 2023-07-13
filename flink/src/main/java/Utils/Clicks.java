package Utils;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Random;

public class Clicks implements SourceFunction<Event> {
    Random random = new Random();
    Boolean flag = true;
    String[] names = new String[]{"zhangsan", "lisi", "wangwu", "zhaoliu"};
    String[] pages = new String[]{"/home", "/cart", "/sku_info", "/payment"};


    @Override
    public void run(SourceContext ctx) throws Exception {

        while (flag) {
            String name = names[random.nextInt(names.length)];
            String page = pages[random.nextInt(pages.length)];

//            long l = random.nextInt(30000);
            ctx.collect(new Event(name, page, System.currentTimeMillis()));

            Thread.sleep(1000L);
        }

    }

    @Override
    public void cancel() {
        flag = false;

    }
}
