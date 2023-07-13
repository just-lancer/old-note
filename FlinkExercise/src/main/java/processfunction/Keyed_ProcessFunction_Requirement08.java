package processfunction;

import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Keyed_ProcessFunction_Requirement08 {
    public static void main(String[] args) throws Exception {
        // 每隔半小时，统计过去1小时内，各商品的访问热度，输出热度前三的商品信息
        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 获取数据源
        DataStreamSource<String> streamSource = env.readTextFile("E:\\IntelliJ_IDEA_workplace\\FlinkExercise\\src\\main\\resources\\test.txt");

        // 设置水位线策略和时间戳
        SingleOutputStreamOperator<ProductVisitInfo> dataSource = streamSource.map(
                new MapFunction<String, ProductVisitInfo>() {
                    @Override
                    public ProductVisitInfo map(String value) throws Exception {
                        String[] split = value.split(",");
                        return new ProductVisitInfo(split[0], split[1], split[2], split[3], Long.valueOf(split[4]) * 1000);
                    }
                }
        ).assignTimestampsAndWatermarks(
                WatermarkStrategy.<ProductVisitInfo>forBoundedOutOfOrderness(Duration.ZERO)
                        .withTimestampAssigner(
                                new SerializableTimestampAssigner<ProductVisitInfo>() {
                                    @Override
                                    public long extractTimestamp(ProductVisitInfo element, long recordTimestamp) {
                                        return element.ts;
                                    }
                                }
                        ));

        // 业务逻辑执行
        dataSource
                .keyBy(ele -> ele.skuID)
                .window(SlidingEventTimeWindows.of(Time.hours(1), Time.minutes(5)))
                .aggregate(new MyAgg1(), new MyProcessWin1())
                .keyBy(ele -> ele.startWin)
                .process(new MyKeyedProcFunc1(3))
                .print();


        // 执行流数据处理
        env.execute();
    }
}

class MyAgg1 implements AggregateFunction<ProductVisitInfo, Long, Long> {
    // 不用当前的Key是什么，后面还有一个ProcessWindowFunction的处理，能够获得当前窗口信息和当前key信息

    @Override
    public Long createAccumulator() {
        return 0L;
    }

    @Override
    public Long add(ProductVisitInfo value, Long accumulator) {
        System.out.println(value);
        return accumulator + 1;
    }

    @Override
    public Long getResult(Long accumulator) {
        // System.out.println(accumulator); // 1,2,3,4之类的
        return accumulator;
    }

    @Override
    public Long merge(Long a, Long b) {
        return null;
    }
}

class MyProcessWin1 extends ProcessWindowFunction<Long, TransferInfo, String, TimeWindow> {

    @Override
    public void process(String key, Context context, Iterable<Long> elements, Collector<TransferInfo> out) throws Exception {
        String startWin = context.window().getStart() + "";
        String endWin = context.window().getEnd() + "";

        out.collect(new TransferInfo(key, elements.iterator().next(), startWin, endWin));

        // System.out.println(elements.iterator().next());
    }
}

// 封装一个POJO类，用于封装传入下一个算子的数据
// 需要包含skuID，skuNum，startWin，endWin
class TransferInfo {
    public String skuId;
    public Long skuNum;
    public String startWin;
    public String endWin;

    public TransferInfo() {

    }

    public TransferInfo(String skuId, Long skuNum, String startWin, String endWin) {
        this.skuId = skuId;
        this.skuNum = skuNum;
        this.startWin = startWin;
        this.endWin = endWin;
    }

    @Override
    public String toString() {
        return "TransferInfo{" +
                "skuId='" + skuId + '\'' +
                ", skuNum=" + skuNum +
                ", startWin='" + startWin + '\'' +
                ", endWin='" + endWin + '\'' +
                '}';
    }
}

class MyKeyedProcFunc1 extends KeyedProcessFunction<String, TransferInfo, String> {
    // 现在要求topN了
    // 这个topN，依旧是每个窗口中的数据的topN，所以，依然可以设置定时器，当然还需要状态
    private int topN;

    public MyKeyedProcFunc1() {
    }

    public MyKeyedProcFunc1(int topN) {
        this.topN = topN;
    }

    // 申明一个List状态，用于收集当前窗口的每一条数据
    ListState<TransferInfo> pv_state;

    @Override
    public void open(Configuration parameters) throws Exception {
        ListStateDescriptor<TransferInfo> pv_desc = new ListStateDescriptor<>("pv_desc", TransferInfo.class);
        pv_state = getRuntimeContext().getListState(pv_desc);
    }

    @Override
    public void processElement(TransferInfo value, Context ctx, Collector<String> out) throws Exception {
        // 每来一条数据，就将其商品ID和pv存入状态中
        // 每个商品只有一条数据
        pv_state.add(value);

        // 为窗口创建定时器
        // 同一个时间戳的定时器不会重复创建
        ctx.timerService().registerEventTimeTimer(Long.valueOf(value.endWin) - 1);
    }

    @Override
    public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
        // 当定时器时间到达时，说明该窗口所有数据达到
        // 现在开始进行排序
        Iterable<TransferInfo> transferInfos = pv_state.get();
        ArrayList<TransferInfo> arr = new ArrayList<>();

        for (TransferInfo t : transferInfos) {
            arr.add(t);
        }

        // 排序
        Collections.sort(arr, new Comparator<TransferInfo>() {
            @Override
            public int compare(TransferInfo o1, TransferInfo o2) {
                return (int) (o1.skuNum - o2.skuNum);
            }
        });

        // 取出前三名
        String startwin = arr.get(0).startWin;
        String endWin = arr.get(0).endWin;
        String skuID;
        Long pv;

        System.out.println("========================================\n");
        System.out.println(new Timestamp(Long.valueOf(startwin)) + "~" + new Timestamp(Long.valueOf(endWin)) + "\n");
        for (int i = 0; i < 3; i++) {
            skuID = arr.get(i).skuId;
            pv = arr.get(i).skuNum;

            System.out.println("商品：" + skuID + "的热度为：" + pv + "，排第" + i + "名");
        }
        System.out.println("========================================\n");
    }
}