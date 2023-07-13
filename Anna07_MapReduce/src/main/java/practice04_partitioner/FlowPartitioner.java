package practice04_partitioner;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class FlowPartitioner extends Partitioner<Text, FlowBean> {

    public int getPartition(Text text, FlowBean flowBean, int i) {
        String key = text.toString();

        if (key.startsWith("13")) {
            return 0;
        } else {
            return 1;
        }
    }
}
