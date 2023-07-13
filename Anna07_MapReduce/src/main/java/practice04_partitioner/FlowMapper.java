package practice04_partitioner;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
    // 创建输出的value
    FlowBean flowBean = new FlowBean();
    // 创建输出的key
    Text outKey = new Text();


    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String strValue = value.toString();

        String[] listValue = strValue.split("\t");

        int len = listValue.length;
        long up;
        long down;

        up = Long.parseLong(listValue[len - 3]);
        down = Long.parseLong(listValue[len - 2]);

        flowBean.setUpFlow(up);
        flowBean.setDownFlow(down);
        flowBean.setSumFlow(up + down);

        outKey.set(listValue[0]);
        context.write(outKey, flowBean);
    }
}
