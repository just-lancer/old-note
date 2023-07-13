package practice01_mapreduce;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCountDriver {
    // 需求：统计给定文件中每个单词出现的次数

    public static void main(String[] args) throws Exception {
        // 获取配置信息以及Job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 关联Driver的jar包
        job.setJarByClass(WordCountDriver.class);
        // 关联Mapper和Reducer的jar包
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 设置Mapper输出KEY-VALUES的数据类型
        job.setMapOutputValueClass(LongWritable.class);
        job.setMapOutputKeyClass(Text.class);

        // 设置最终输出的KEY-VALUES的数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        // 设置输入和输出的路径
        String inputFile = "F:\\20211216DBFile\\HDFSTestFile\\up\\hello.txt";
        String outputFile = "F:\\20211216DBFile\\HDFSTestFile\\out\\";
        FileInputFormat.setInputPaths(job, new Path(inputFile));
        FileOutputFormat.setOutputPath(job, new Path(outputFile));

        // 提交Job任务
        job.waitForCompletion(true);

    }
}
