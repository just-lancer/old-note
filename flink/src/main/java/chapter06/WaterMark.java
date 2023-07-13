package chapter06;

import Utils.Event;
import org.apache.flink.api.common.eventtime.*;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class WaterMark {
    public static void main(String[] args) {
        /**
         * 水位线
         *  1、三种时间语义
         *      事件时间：事件发生的时间
         *      处理事件：事件在Flink中被处理的时间
         *      摄取时间：事件被Flink Source获取的事件
         *
         *  2、水位线的定义：在事件时间的语义下，不依赖于系统时间，而是基于数据自带的时间戳，定义的一个时钟，
         *      用来表示事件进展的事件，该时钟就是水位线。
         *
         *  3、水位线的意义：水位线的实质是一个时间戳，表示当前数据流中事件事件已经到达该时间戳t，也就是说，时间戳t之前的数据都已经到达窗口，
         *      在时间戳t之后，不会再出现时间戳小于t的数据了
         *
         *  4、水位线的特点：
         *      -- 水位线不随系统时间发生改变，而是由数据的事件事件推动
         *      -- 水位线是单调递增的
         *      -- 每一个并行子任务都会有一个水位线
         *
         *  5、有序数据流的水位线：对于有序数据流而言，可以直接选取每条数据的事件时间作为水位线，这样选择的水位线满足单调递增的规律
         *      当有序数据非常稠密，也就是同一时刻有多条数据产生，那么就会产生许多重复的水位线，即使没有重复的水位线产生，但水位线间隔非常小也会降低
         *      Flink效率，所以，对于有序数据流，一般会周期性生成一个水位线，即每隔一段时间（系统时间），获取当前的最新数据的时间戳作为水位线。
         *
         *  6、无序数据流的水位线：对于无序数据流而言，由于后产生的数据可能会提前到来，所以为了保证在窗口时间范围内的数据都能到来，需要对水位线设置一个偏移量
         *
         */

        /**
         *  水位线相关API
         *
         */

        // 获取流处理环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 获取数据源
        DataStreamSource<Event> eventDataStreamSource = env.fromElements(
                new Event("lisi", "/home", -4832377117453491519L),
                new Event("zhangsan", "/payment", -8356692831163685318L),
                new Event("lisi", "/sku_info", -2046974886125370955L),
                new Event("zhangsan", "/sku_info", -5562017592536961239L),
                new Event("wangwu", "/sku_info", -145223812554166460L),
                new Event("zhaoliu", "/cart", -7017030832878705529L),
                new Event("wangwu", "/sku_info", -8571205379612016868L),
                new Event("lisi", "/cart", -7033650789022819438L)
        );
        // 设置水位线，水位线越早设置越好
        /**
         *  在DataStream的API中有一个专门用于为数据流中数据设置时间戳以及产生标志事件进度的水位线的方法：assignTimestampsAndWatermarks()，
         *      数据流中的每一条数据（事件）都将调用该方法
         *      public SingleOutputStreamOperator<T> assignTimestampsAndWatermarks(WatermarkStrategy<T> watermarkStrategy)
         *      参数：watermarkStrategy，基于数据时间戳产生水位线的策略
         *      返回值：返回值是一个具有时间戳和水位线的SingleOutputStreamOperator算子，或DataStream
         *
         */

        /**
         *  WatermarkStrategy：时间戳和水位线产生策略接口，有两个重要的方法，其中一个时抽象方法：
         *      -- WatermarkGenerator<T> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context)
         *         该方法用于基于时间戳按照指定的方式生成水位线，在这个接口中还有两个方法：
         *          -- void onEvent(T event, long eventTimestamp, WatermarkOutput output)
         *             该方法在每条数据到来时都会执行，其参数分别是数据本身event，数据自带的时间戳eventTimestamp，以及需要输出的水位线output
         *          -- void onPeriodicEmit(WatermarkOutput output)
         *              该方法会周期性的执行，该方法会周期性的产生一个水位线，周期性间隔，在其他地方，可以查
         *
         *      -- default TimestampAssigner<T> createTimestampAssigner(TimestampAssignerSupplier.Context context)
         *          该方法主要用于基于用户指定的逻辑获取一个时间戳，并实例化一个TimestampAssigner对象，该对象就是一个时间戳
         *
         */

        /**
         *  自定义时间戳以及水位线，需要实现时间戳和水位线生成策略接口：WatermarkStrategy，并实现抽象方法：createWatermarkGenerator()，
         *  该方法的实现，需要返回一个接口的实现类对象：WatermarkGenerator<T>，该接口中有两个方法需要实现，
         *  其一：void onPeriodicEmit(WatermarkOutput output)用于周期性产生水位线，它会周期性地被调用
         *  其二：void onEvent(T event, long eventTimestamp, WatermarkOutput output)用于根据指定策略间断性产生水位线
         *
         *  而时间戳分配（或生成）策略，直接重写方法
         *      default TimestampAssigner<T> createTimestampAssigner(TimestampAssignerSupplier.Context context)
         *  创建一个TimestampAssigner实现类对象，并重写接口中用于提取或获取时间戳的方法
         *      long extractTimestamp(T element, long recordTimestamp)
         *  即可
         */

        /**
         *  自定义水位线生成策略较为麻烦，Flink提供了WatermarkStrategy接口的四种实现类，用于按指定的策略产生水位线，
         *  实际使用时，不需要直接创建这实现类的对象，直接调用WatermakStrategy接口的静态方法，就可以设置水位线生成策略。
         *
         *  -- static <T> WatermarkStrategy<T> forMonotonousTimestamps()
         *      该方法是为有序数据流创建水位线策略的方法
         *  -- static <T> WatermarkStrategy<T> forBoundedOutOfOrderness(Duration maxOutOfOrderness)
         *      该方法是为无序数据流创建水位线策略的方法，其中有一个参数，表水位线延迟时间，单位毫秒
         *  需要说明的是：这两个方法创建的水位线生成策略都是周期性生成水位线，默认时间周期200毫秒
         *
         */

        /**
         *  创建完水位线生成策略后，还需要为数据指定时间戳，如果已经指定就无需再指定或分配，调用方法
         *  -- withTimestampAssigner(new TimestampAssignerSupplier<Event>()
         *  -- withTimestampAssigner(new SerializableTimestampAssigner<Event>()
         */

        /**
         *  水位线在算子中的传递
         *  -- 水位线在算子之间的传递是按照广播的方式进行的
         *  -- Flink中的每个算子都会保留上游算子各个并行子任务的水位线，并选择其中最小的水位线作为自己的水位线
         *
         */

        // 为有序数据流创建水位线
        eventDataStreamSource.assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forMonotonousTimestamps().
                        // 调用withTimestampAssigner(new TimestampAssignerSupplier<Event>()方法获取数据的时间戳
                                withTimestampAssigner(new TimestampAssignerSupplier<Event>() {
                            @Override
                            public TimestampAssigner<Event> createTimestampAssigner(Context context) {
                                return new TimestampAssigner<Event>() {
                                    @Override
                                    public long extractTimestamp(Event element, long recordTimestamp) {
                                        return element.time;
                                    }
                                };
                            }
                        })

                // 调用withTimestampAssigner(new SerializableTimestampAssigner<Event>()方法获取数据的时间戳
//                withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
//                    @Override
//                    public long extractTimestamp(Event element, long recordTimestamp) {
//                        return element.time;
//                    }
//                })

        );

        // 自定义水位线生成策略
        eventDataStreamSource.assignTimestampsAndWatermarks(new WatermarkStrategy<Event>() {
            @Override
            public WatermarkGenerator<Event> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context) {
                return new WatermarkGenerator<Event>() {
                    @Override
                    public void onEvent(Event event, long eventTimestamp, WatermarkOutput output) {
                        // 这是离散生成水位线，output用于将水位线发送出去
                    }

                    @Override
                    public void onPeriodicEmit(WatermarkOutput output) {
                        // 这是周期性生成水位线，output用于将水位线发送出去
                    }
                };
            }

            // 除了定义水位线生成器，产生水位线生成策略，还需要指定数据的时间戳，用来给上面的方法用
            // 调用方法createTimestampAssigner
            @Override
            public TimestampAssigner<Event> createTimestampAssigner(TimestampAssignerSupplier.Context context) {
                return new TimestampAssigner<Event>() {
                    @Override
                    public long extractTimestamp(Event element, long recordTimestamp) {
                        return element.time;
                    }
                };
            }
        });
        // 现在已经自定义好了水位线生成器

    }
}
