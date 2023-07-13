package practice06_combiner;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

    // 定义输出的value值
    LongWritable outValue = new LongWritable(1);
    // 定义输出的key值
    Text outKey = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String strValue = value.toString();

        String[] listValue = strValue.split(" ");

        for (String str : listValue
        ) {
            outKey.set(str);
            context.write(outKey, outValue);
        }

    }
}
