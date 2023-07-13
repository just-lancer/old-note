package practice06_combiner;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class WordCountCombiner extends Reducer {
    // 定义合并
    long sum;

    @Override
    protected void reduce(Object key, Iterable values, Context context) throws IOException, InterruptedException {
        Iterator<LongWritable> iterator = values.iterator();
        while (iterator.hasNext()) {
            LongWritable next = iterator.next();

            sum += 1;
        }
        LongWritable outValue = new LongWritable(sum);

        context.write(key, outValue);
    }
}
