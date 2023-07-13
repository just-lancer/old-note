package processfunction;

import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
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
import java.util.HashMap;

public class Keyed_ProcessFunction_Requirement07 {
    // 需求：每隔5分钟，统计过去1小时，各用户访问的商品热度的前三名商品
    public static void main(String[] args) throws Exception {
        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 获取数据源
        DataStreamSource<String> stream = env.readTextFile("E:\\IntelliJ_IDEA_workplace\\FlinkExercise\\src\\main\\resources\\UserBehavior.csv");

        // 将数据封装成一个POJO类
        SingleOutputStreamOperator<ProductVisitInfo> mapSource = stream.map(
                new MapFunction<String, ProductVisitInfo>() {
                    @Override
                    public ProductVisitInfo map(String value) throws Exception {
                        String[] split = value.split(",");
                        return new ProductVisitInfo(
                                split[0],
                                split[1],
                                split[2],
                                split[3],
                                Long.valueOf(split[4]) * 1000
                        );
                    }
                }
        );

        // 设置事件时间和水位线策略
        SingleOutputStreamOperator<ProductVisitInfo> outSource = mapSource.assignTimestampsAndWatermarks(
                WatermarkStrategy.<ProductVisitInfo>forBoundedOutOfOrderness(Duration.ofMillis(0))
                        .withTimestampAssigner(
                                new SerializableTimestampAssigner<ProductVisitInfo>() {
                                    @Override
                                    public long extractTimestamp(ProductVisitInfo element, long recordTimestamp) {
                                        return element.ts;
                                    }
                                }
                        )
        );

        // 业务逻辑
        outSource
                .keyBy(ele -> ele.userID)
                .window(SlidingEventTimeWindows.of(Time.hours(1), Time.minutes(30)))
                .aggregate(new MyAgg(), new MyProcessWin())
                .print();

        // 执行流数据处理
        env.execute();
    }
}

class MyAgg implements AggregateFunction<ProductVisitInfo, HashMap<String, Long>, HashMap<String, Long>> {
    @Override
    public HashMap<String, Long> createAccumulator() {
        return new HashMap<String, Long>();
    }

    @Override
    public HashMap<String, Long> add(ProductVisitInfo value, HashMap<String, Long> accumulator) {
        // 该用户第一次访问该商品，添加该商品及其对应的次数
        if (!accumulator.containsKey(value.skuID)) {
            accumulator.put(value.skuID, 1L);
        } else {
            Long newSum = accumulator.get(value.skuID) + 1;
            accumulator.put(value.skuID, newSum);
        }
        return accumulator;
    }

    @Override
    public HashMap<String, Long> getResult(HashMap<String, Long> accumulator) {
        return accumulator;
    }

    @Override
    public HashMap<String, Long> merge(HashMap<String, Long> a, HashMap<String, Long> b) {
        return null;
    }
}

class MyProcessWin extends ProcessWindowFunction<HashMap<String, Long>, String, String, TimeWindow> {
    @Override
    public void process(String s, Context context, Iterable<HashMap<String, Long>> elements, Collector<String> out) throws Exception {
        ArrayList<ProductResult> productResults = new ArrayList<>();

        HashMap<String, Long> hashMap = elements.iterator().next();
        for (String key : hashMap.keySet()) {
            Long pv = hashMap.get(key);
            productResults.add(new ProductResult(s, key, pv));
        }

        // 现在对数组排序
        Collections.sort(productResults, new Comparator<ProductResult>() {
            @Override
            public int compare(ProductResult o1, ProductResult o2) {
                return (int) (o1.num - o2.num);
            }
        });

        // 获取前三的结果，及窗口信息
        // 窗口信息：
        long start = context.window().getStart();
        long end = context.window().getEnd();

        String userID;
        String skuID;
        Long pv;

        // 打印前三的结果
        System.out.println("============================================================\n");
        System.out.println(new Timestamp(start) + "~" + new Timestamp(end) + "\n");
//        for (int i = 0; i < 3; i++) {
//            userID = productResults.get(i).userID;
//            skuID = productResults.get(i).skuID;
//            pv = productResults.get(i).num;
//            System.out.println("第" + userID + "名用户的第" + skuID + "号商品，浏览次数为：" + pv + "，排第：" + i + "名");
//        }
        System.out.println(productResults);
        System.out.println("============================================================\n");

    }
}

class ProductVisitInfo {
    public String userID;
    public String skuID;
    public String spuID;
    public String type;
    public Long ts;

    public ProductVisitInfo() {
    }

    public ProductVisitInfo(String userID, String skuID, String spuID, String type, Long ts) {
        this.userID = userID;
        this.skuID = skuID;
        this.spuID = spuID;
        this.type = type;
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "ProductVisitInfo{" +
                "userID='" + userID + '\'' +
                ", skuID='" + skuID + '\'' +
                ", spuID='" + spuID + '\'' +
                ", type='" + type + '\'' +
                ", ts='" + ts + '\'' +
                '}';
    }
}

// 定义一个结果POJO类
class ProductResult {
    public String userID;
    public String skuID;
    public Long num;

    public ProductResult() {
    }

    public ProductResult(String userID, String skuID, Long num) {
        this.userID = userID;
        this.skuID = skuID;
        this.num = num;
    }

    @Override
    public String toString() {
        return "ProductResult{" +
                "userID='" + userID + '\'' +
                ", skuID='" + skuID + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
