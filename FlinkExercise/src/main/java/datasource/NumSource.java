package datasource;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Random;

public class NumSource implements SourceFunction<Integer> {
    // 创建数值数据源
    boolean flag = true;

    @Override
    public void run(SourceContext<Integer> ctx) throws Exception {
        while (flag) {
            // 每隔1秒发送一条数据
            Thread.sleep(1000);

            // 数据的取值范围：0~99
            Random random = new Random();
            ctx.collect(random.nextInt(100));
        }
    }

    @Override
    public void cancel() {
        flag = false;
    }
}
