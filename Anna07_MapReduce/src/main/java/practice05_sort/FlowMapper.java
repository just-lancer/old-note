package practice05_sort;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowMapper extends Mapper<LongWritable, Text, FlowBean, Text> {
    // 定义输出的key
    FlowBean keyFlowBean = new FlowBean();
    // 定义输出的value
    Text outValue = new Text();

    long up;
    long down;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String strValue = value.toString();

        String[] listValue = strValue.split("\t");

        int len = listValue.length;

        up = Long.parseLong(listValue[len - 3]);
        down = Long.parseLong(listValue[len - 2]);

        keyFlowBean.setUpFlow(up);
        keyFlowBean.setDownFlow(down);
        keyFlowBean.setSumFlow(up + down);
        keyFlowBean.setPhoneNum(listValue[1]);

        outValue.set(listValue[1]);
        context.write(keyFlowBean, outValue);
    }
}
