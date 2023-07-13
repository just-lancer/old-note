package practice02_serializable;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FlowDriver {

    public static void main(String[] args) throws Exception {
        // 创建配置并获取Job任务对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 关联Driver、Mapper、Reducer的jar包
        job.setJarByClass(FlowDriver.class);
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        // 设置Mapper输出K-V的数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        // 设置最终输出的K-V的数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        // 设置数据的输入路径和输出路径
        String inputFile = "F:\\20211216DBFile\\HDFSTestFile\\up\\phone_data.txt";
        String outputFile = "F:\\20211216DBFile\\HDFSTestFile\\out1";
        FileInputFormat.setInputPaths(job, new Path(inputFile));
        FileOutputFormat.setOutputPath(job, new Path(outputFile));

        // 提交job任务
        job.waitForCompletion(true);

    }


}
