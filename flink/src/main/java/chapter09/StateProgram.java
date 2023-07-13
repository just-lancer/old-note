package chapter09;

import Utils.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Duration;

public class StateProgram {
    // 状态编程
    public static void main(String[] args) throws Exception {
        /**
         *  状态：算子的并行子任务在进行数据计算时，为数据流保存的中间计算结果称为状态
         *
         *  状态需要关注的事项：
         *      -- 状态的读取和存储
         *      -- 状态的持久化
         *      -- 状态的故障恢复
         *      -- 任务并行度发生变化（主要指并行度增大）时，状态的调整
         *
         *  状态分类：
         *      根据状态在Flink中的管理方式，状态可分为托管状态和原始状态。托管状态：Flink框架帮我们管理状态的
         *          读取、存储和持久化等一系列事项。原始状态：Flink只为我们提供一块内存空间，状态的各个事项需要
         *          我们自己进行设计和管理
         *      一般情况下，直接使用Flink的托管状态。
         *
         *      对于托管状态，根据是否对数据进行按键分组，状态可分为算子状态和键控状态。简单来说，对于未分组的
         *      数据的状态称为算子状态，在TaskManager的每一个slot中，会为每条到来的数据共同维护一个状态，所有数据的
         *      计算都会对这个状态进行访问。对于分组数据的状态称为键控状态，在TaskManager的每一个slot中，会为每个
         *      key都维护一个状态，所有key的状态会用一个HashMap维护，每条数据到来时，只会访问它的key对应的状态，对于
         *      一个算子的每个并行子任务而言，它们之间的状态不会进行数据的通信。
         *
         */

        // 键控状态测试：值类型：ValueState
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);

        DataStreamSource<Event> dataSource = env.fromElements(new Event("zhangsan", "/home", System.currentTimeMillis()));

        SingleOutputStreamOperator<Event> eventSingleOutputStreamOperator = dataSource.assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ZERO).withTimestampAssigner(
                new SerializableTimestampAssigner<Event>() {
                    @Override
                    public long extractTimestamp(Event element, long recordTimestamp) {
                        return element.time;
                    }
                }
        ));

        KeyedStream<Event, String> keyedSource = eventSingleOutputStreamOperator.keyBy(data -> data.name);

        SingleOutputStreamOperator<String> map = keyedSource.map(new MyMap());

        map.print();

        env.execute();
    }

    public static class MyMap extends RichMapFunction<Event, String> {
        // 创建值状态
        ValueState<String> state;
        ValueStateDescriptor<String> my_state;

        @Override
        public void open(Configuration parameters) {
            // 初始化值状态
            // 首先创建值状态描述器，需要设置状态的名称，以及状态值的类型
            my_state = new ValueStateDescriptor<>("my_state", String.class);

            // 再调用运行时状态对值状态进行赋值
            state = getRuntimeContext().getState(my_state);

        }

        @Override
        public void close() throws Exception {
            super.close();
        }

        @Override
        public String map(Event value) throws Exception {
            // 不管别的，先打印值状态的值
            String value1 = state.value();
            System.out.println("值状态的值第一次打印：" + value1);

            // 给状态赋另一个值
            state.update("hahah");

            System.out.println("值状态的值第二次打印：" + state.value());

            System.out.println("值状态的名称：" + my_state.getName());
            System.out.println("值状态的Type：" + my_state.getType());

            return "heheh";
        }
    }
}
