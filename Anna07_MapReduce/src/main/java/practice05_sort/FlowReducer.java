package practice05_sort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class FlowReducer extends Reducer<FlowBean, Text, Text, FlowBean> {
    // 定义输出的Key
    Text outKey = new Text();

    long up;
    long down;

    @Override
    protected void reduce(FlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Iterator<Text> iterator = values.iterator();
        while (iterator.hasNext()) {
            Text next = iterator.next();
            outKey.set(next);
            context.write(outKey, key);
        }
    }
}
