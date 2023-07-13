package HDFSOperation;

public class HDFSAPIText {
    /**
     * HDFSAPI操作分三步
     *  1、获取HDFS连接对象，实质是获取NameNode服务器的连接对象
     *  2、HDFS的API操作
     *  3、关闭NameNode服务器的连接资源
     */

    /**
     * 相关API
     *  说明：这些API都是FileSystem实现类对象才能调用的
     *  1、从本地上传文件到HDFS文件系统中:
     *      public void copyFromLocalFile(boolean delSrc, boolean overwrite, Path src, Path dst) throws IOException
     *
     *  2、下载HDFS文件到本地
     *      public void copyToLocalFile(boolean delSrc, Path src, Path dst, boolean useRawLocalFileSystem) throws IOException
     *
     *  3、删除HDFS文件
     *      public abstract boolean delete(Path var1, boolean var2) throws IOException
     *
     *  4、HDFS文件更名
     *      public abstract boolean rename(Path var1, Path var2) throws IOException
     *
     *  5、获取HDFS文件（文件和目录）的元数据信息
     *      获取文件的元数据
     *      > public RemoteIterator<LocatedFileStatus> listFiles(final Path f, final boolean recursive) throws FileNotFoundException, IOException
     *      该方法获取文件的元数据，具有文件名称，长度，权限，分组，存储块信息
     *      即RemoteIterator的实现类对象具有：
     *          > getPath() 获取文件名称
     *          > getLen() 获取文件长度
     *          > getPermission() 获取文件权限
     *          > getGroup() 获取文件所属组
     *          > public BlockLocation[] getBlockLocations() 获取文件块信息
     *          > 块信息包含很多信息，比如块存储节点：getHosts()、块大小等
     *
     *      获取目录或文件的元数据
     *      public abstract FileStatus[] listStatus(Path var1) throws FileNotFoundException, IOException
     *          > 块信息只有块的大小信息，没有节点信息
     *
     *          二者区别见下
     *
     * 6、HDFS文件、文件夹判断
     *      文件是文件还是目录的信息存储在元数据中，依然利用元数据的方法进行判断
     *      方法为，isDirectory()    isFile()
     */

    // listFiles(Path path)与listStatus(Path path)方法的区别
    /**
     *  listFiles(Path path)和listStatus(Path path)都是抽象类FileSystem中的的抽象方法
     *  其区别是：
     *      > listFiles(Path path, boolern bool) 只能获取文件的元数据信息，并封装到一个迭代器中，
     *          当path路径指向一个文件时，获取该文件的元数据信息
     *          当path路径指向一个目录时，
     *              若bool为false，那么只获取该目录下文件的元数据信息，并进行封装
     *              若bool为ture，那么获取该目录下所有文件以及子目录下的所有文件的元数据信息，并进行封装
     *
     *      > listStatus(Path path) 能够获取文件和目录的元数据，并将每个文件（文件和目录）的元数据封装成一个对象存放在数组中。
     *          listStatus(Path path) 将目录下文件的元数据信息和一级子目录的信息的元数据进行返回
     *
     */


}
