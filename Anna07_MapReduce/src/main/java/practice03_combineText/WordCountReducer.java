package practice03_combineText;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
    int sum = 0;


    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        Iterator<LongWritable> iterator = values.iterator();
        while (iterator.hasNext()) {
            LongWritable next = iterator.next();

            sum += 1;
        }
        LongWritable outValue = new LongWritable(sum);

        context.write(key, outValue);
    }
}
