package HDFSOperation;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.net.URI;

public class HDFSUtils {


    // 获取NameNode连接
    public static FileSystem getFileSystem() {
        FileSystem fs = null;
        try {
            // 获取NameNode的连接对象
            // 设置具有访问HDFS服务器的用户
            String user = "atguigu";

            // 创建HDFS的配置对象，该对象能够对此次操作进行配置，比如设置副本数量
            Configuration conf = new Configuration();

            // 创建URI对象，用于指定访问指定的服务器
            URI uri = new URI("hdfs://hadoop132:9820");

            // 创建NameNode服务器连接对象
            fs = FileSystem.get(uri, conf, user);
            // 注意FileSystem是一个抽象类，其创建的NameNode连接对象是实现类DistributedFileSystem的对象

            return fs;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fs;

    }

    // 关闭连接
    public static void closeFileSystem(FileSystem file) {
        if (file != null) {
            try {
                file.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}


