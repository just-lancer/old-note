package HDFSOperation;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HDFSAPITest {

    // 测试相关API
    public static void main(String[] args) {
    }

    FileSystem fs = null;

    @Before
    // 获取NN连接
    public void getFS() {
        fs = HDFSUtils.getFileSystem();
    }

    @After
    public void closeFS() {
        HDFSUtils.closeFileSystem(fs);
    }

    // 上传文件到HDFS
    @Test
    public void hdfsTest1() {
        // 指定文件路径
        Path srcFile = new Path("F:\\20211216DBFile\\HDFSTestFile\\up\\ja.txt");
        Path srcDir = new Path("F:\\20211216DBFile\\HDFSTestFile\\up\\upchild");
        Path hdfsDir = new Path("/test");

        try {
            // 上传文件和目录
            fs.copyFromLocalFile(false, false, srcFile, hdfsDir);
            fs.copyFromLocalFile(false, false, srcDir, hdfsDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从HDFS下载文件到本地
    @Test
    public void hdfsTest2() {
        // 指定文件和目录
        Path aimDir = new Path("F:\\20211216DBFile\\HDFSTestFile\\down");
        Path hdfsFile = new Path("/test/upchild/lol1.txt");
        Path hdfsDir = new Path("/test/upchild");

        // 执行下载
        try {
            fs.copyToLocalFile(false, hdfsDir, aimDir, true);
            fs.copyToLocalFile(false, hdfsFile, aimDir, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 删除HDFS中存储的文件
    // 说明，删除的时候如果删除目录，那么需要传入boolean类型参数，表示递归删除
    @Test
    public void hdfsTest3() {

        // 指定要删除的HDFS文件
        Path delFile1 = new Path("/test/ja.txt");
        Path delFile2 = new Path("/test/upchild/lol1.txt");
        Path delFile3 = new Path("/test/upchild/lol2.txt");
        Path delDir = new Path("/test/upchild");

        // 执行删除
        try {
            fs.delete(delDir, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 对文件进行改名，或者移动
    // rename方法兼具重命名和移动的双重作用
    @Test
    public void hdfsTest4() {
        // 指定需要改名的文件
        Path hdfsFile = new Path("/test/ja.txt");
        // 指定文件新的名字
        Path newName = new Path("/jianai.txt");

        try {
            fs.rename(hdfsFile, newName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取文件的元数据信息，listFiles(Path path) 或listStauts(Path path)方法
    @Test
    public void meteDateTest() {
        // 获取文件的元数据信息：
        // 指定文件的路径
        Path filePath = new Path("/jianai.txt");
        // 指定文件目录的路径
        Path dirPath = new Path("/test");

        try {
            RemoteIterator<LocatedFileStatus> fileMetaDate = fs.listFiles(dirPath, false);
            while (fileMetaDate.hasNext()) {
                LocatedFileStatus next = fileMetaDate.next();

                String name = next.getPath().getName();// 获取文件名称
                Path path = next.getPath();
                long len = next.getLen();
                String owner = next.getOwner();
                String group = next.getGroup();
                FsPermission permission = next.getPermission();
                short replication = next.getReplication();
                long modificationTime = next.getModificationTime();
                long blockSize = next.getBlockSize();
                long accessTime = next.getAccessTime();
                System.out.println("文件名称：" + name);
                System.out.println("文件路径：" + path);
                System.out.println("文件大小：" + len);
                System.out.println("文件所属用户：" + owner);
                System.out.println("文件所在组：" + group);
                System.out.println("文件访问权限：" + permission);
                System.out.println("文件备份数量：" + replication);
                System.out.println("文件最后一次修改时间：" + modificationTime);
                System.out.println("文件访问时间：" + accessTime);
                System.out.println("文件块大小：" + blockSize);

                BlockLocation[] blockLocations = next.getBlockLocations();

                for (int i = 0; i < blockLocations.length; i++) {
                    // 文件块的典型信息是文件存放的节点
                    String[] names = blockLocations[i].getNames();

                    String[] hosts = blockLocations[i].getHosts();

                    System.out.println(names);
                    System.out.println(hosts);
                }

                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 获取目录的元数据信息
    @Test
    public void judgeFile() {
        // 获取文件的元数据信息：
        // 指定文件的路径
        Path filePath = new Path("/jianai.txt");
        // 指定文件目录的路径
        Path dirPath = new Path("/test");

        try {
            FileStatus[] fileStatuses = fs.listStatus(dirPath);
            for (int i = 0; i < fileStatuses.length; i++) {
                System.out.println(i);
                FileStatus next = fileStatuses[i];

                String name = next.getPath().getName();// 获取文件名称
                Path path = next.getPath();
                long len = next.getLen();
                String owner = next.getOwner();
                String group = next.getGroup();
                FsPermission permission = next.getPermission();
                short replication = next.getReplication();
                long modificationTime = next.getModificationTime();
                long blockSize = next.getBlockSize();
                long accessTime = next.getAccessTime();
                System.out.println("文件名称：" + name);
                System.out.println("文件路径：" + path);
                System.out.println("文件大小：" + len);
                System.out.println("文件所属用户：" + owner);
                System.out.println("文件所在组：" + group);
                System.out.println("文件访问权限：" + permission);
                System.out.println("文件备份数量：" + replication);
                System.out.println("文件最后一次修改时间：" + modificationTime);
                System.out.println("文件访问时间：" + accessTime);
                System.out.println("文件块大小：" + blockSize);

                System.out.println();
            }

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    // 判断文件的类型：目录还是文件

    @Test
    public void judgeFileTest() {
        // 指定文件路径
        Path file = new Path("/test/ja.txt");
        Path dir = new Path("/test");

        try {
            // 文件是目录还是文件，这个信息是元数据，应该存在元数据中
            FileStatus[] next = fs.listStatus(dir);
            for (int i = 0; i < next.length; i++) {
                if (next[i].isDirectory()) {
                    System.out.println(next[i]);
                    System.out.println(next[i].getPath().getName() + "是目录");
                } else {
                    System.out.println(next[i]);
                    System.out.println(next[i].getPath().getName() + "是文件");
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
