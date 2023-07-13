package datasource;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Random;

public class TempMonitor implements SourceFunction<Temp> {
    boolean flag = true;

    @Override
    public void run(SourceContext<Temp> ctx) throws Exception {
        while (flag) {
            Thread.sleep(1000);

            Random random = new Random();
            for (int i = 1; i <= 4; i++) {
                String monitor = "monitor_" + i;
                Double tem = random.nextGaussian();
                ctx.collect(new Temp(monitor, tem));
            }
        }

    }

    @Override
    public void cancel() {
        flag = false;
    }
}
