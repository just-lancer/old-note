package practice05_sort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FlowDriver {
    // 需求：排序，按各手机号的总流量降序排序
    public static void main(String[] args) throws Exception {
        // 创建配置对象和任务对象
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        // 设置关联Driver的jar包
        job.setJarByClass(FlowDriver.class);
        // 设置关联Mapper和Reducer的jar包
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        // 设置Mapper的输出数据类型
        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(Text.class);

        // 设置最终输出的数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        // 设置文件的输入输出路径
        String inputFile = "F:\\20211216DBFile\\HDFSTestFile\\up\\phone_data.txt";
        String outputFile = "F:\\20211216DBFile\\HDFSTestFile\\out5";
        FileInputFormat.setInputPaths(job, new Path(inputFile));
        FileOutputFormat.setOutputPath(job, new Path(outputFile));

        // 提交任务
        job.waitForCompletion(true);

    }
}
