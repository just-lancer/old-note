package practice02_serializable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class FlowReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
    // 创建输出FlowBean
    FlowBean flowBean = new FlowBean();
    long up;
    long down;

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        Iterator<FlowBean> iterator = values.iterator();

        while (iterator.hasNext()) {
            FlowBean next = iterator.next();

            up = up + next.getUpFlow();
            down = down + next.getDownFlow();

        }
        flowBean.setUpFlow(up);
        flowBean.setDownFlow(down);
        flowBean.setSumFlow(up + down);

        context.write(key, flowBean);
    }
}
