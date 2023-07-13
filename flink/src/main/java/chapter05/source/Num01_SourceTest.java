package chapter05.source;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.Serializable;
import java.util.ArrayList;

public class Num01_SourceTest {
    // Flink源数据获取方式
    // 用于测试的数据源获取
    public static void main(String[] args) throws Exception {
        // 获取流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 1、从文本文件中获取数据，有界数据流
        // 文件路径可以是一个本地文件路径，也可以是一个文件系统的路径
        DataStreamSource<String> textFileSource = env.readTextFile("flink\\data\\input01.txt");

        textFileSource.print();

        // 2、从集合或迭代器中获取数据，有界数据流
        ArrayList<String> arr = new ArrayList<>();
        arr.add("zhangsan");
        arr.add("lisi");
        arr.add("wangwu");
        DataStreamSource<String> collSource = env.fromCollection(arr);

        collSource.print();

        // 3、从元素中获取数据，元素类型可以不统一，有界数据流
        DataStreamSource<? extends Serializable> eleSource = env.fromElements("zhangsan", 4, false);
        eleSource.print();

        // 4、从socket中获取数据
        DataStreamSource<String> socketSource = env.socketTextStream("hadoop132", 9999);
        socketSource.print();

        // Flink为了方便对数据进行解析和序列化，一般会将数据封装倒一个POJO类对象中
        // 该类的访问权限是public
        // 必须有一个共有的无参构造器，可以有其他的构造器
        // 所有属性都是public

        // 执行流处理
        env.execute();
    }

    // 创建一个POJO类
    public class Person {
        public String name;
        public int age;
        public Boolean gender;

        public Person() {
        }

        public Person(String name, int age, Boolean gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    '}';
        }
    }
}
