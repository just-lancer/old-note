package Utils;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Random;

public class ClicksUserInfo implements SourceFunction<Event> {
    Random random = new Random();
    Boolean flag = true;
    String[] names = new String[]{"zhangsan", "lisi", "wangwu", "zhaoliu"};
    Integer[] ages = new Integer[]{18, 20, 25, 30};
    String[] addresses = new String[]{"beijing", "shanghai", "guangzhou", "shenzhen"};

    @Override
    public void run(SourceContext ctx) throws Exception {

        while (flag) {
            int index = random.nextInt(names.length);
            String name = names[index];
            String address = addresses[random.nextInt(addresses.length)];
            Integer age = ages[index];

            ctx.collect(new UserInfo(name, age, address, System.currentTimeMillis()));

            Thread.sleep(1000L);
        }

    }

    @Override
    public void cancel() {
        flag = false;

    }
}
