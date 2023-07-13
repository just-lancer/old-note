package knowledge;

public class FileClass {
    /**
     *  IO流的学习
     *  1、File类的使用
     *  2、IO流的原理及分类
     */

    /**
     * 一、File类的使用
     *  1、File类概述：这个类的对象封装了系统中文件或目录的相关信息
     *      > File类的一个对象，代表硬盘中实际存在的一个文件或一个文件目录；无论该路径下是否 存在文件或目录，都不影响File对象的创建
     *      > File类声明在java.io包下
     *      > File类中涉及到关于文件或文件目录的创建、删除、重命名、修改时间、文件大小等方法，
     *          并未涉及到写入或读取文件内容的操作。如果需要读取或写入文件内容，必须使用IO流来完成。
     *      > 后续File类的对象常会作为参数传递到流的构造器中，指明读取或写入的"终点".
     *
     *  2、构造器：
     *      > File(String filePath)：以filePath为路径创建File对象，可以是相对路径或绝对路径。若为相对路径，则是相对于当前模块的路径
     *          相对路径：相对于当前文件的路径
     *          绝对路径：在硬盘上真正的路径
     *      > File(String parentPath,String childPath)：以parentPath为父路径，child为子路径创建File对象
     *      > File(File parentFile,String childPath)：根据一个父File对象和子文件路径创建File对象
     *
     *      说明：路径的分隔符
     *          Windows和DOS系统默认使用"\"(斜杆)来表示
     *          UNIX和URL使用"/"(反斜杠)来表示
     *          为了解决此问题，File类提供一个常量：public static final String separator，此常量会根据操作系统，动态提供分隔符
     *
     *      注意：对于相对路径的说明
     *          在IDEA中，如果在Junit单元测试方法中使用相对路径，那么这个相对路径是相对于module的路径
     *                  如果是在main方法中使用相对路径，那么这个相对路径相对的是当前工程
     *          在eclipse中，不论是单元测试方法还是还是main方法，都是相对当前工程的路径
     *
     *  3、常用方法：获取File类对象相关信息的方法
     *
     *      获取属性类：
     *      public File getAbsoluteFile()：获取文件的绝对路径，返回File对象
     *      public String getAbsolutePath()：获取文件的绝对路径，返回路径的字符串
     *      public String getPath() ：获取File对象中封装的路径，返回路径字符串
     *      public String getName() ：获取文件或文件夹的名称
     *      public String getParent()：获取当前路径的上级（父级）路径。若无，返回null
     *      public long length() ：获取文件长度（即：字节数）。不能获取目录的长度。
     *      public long lastModified() ：获取最后一次的修改时间，毫秒值
     *
     *      如下的两个方法适用于文件目录：
     *      public String[] list() ：获取指定目录下的所有文件或者文件目录的名称数组
     *      public File[] listFiles() ：获取指定目录下的所有文件或者文件目录的File数组
     *
     *      判断类方法：
     *      public boolean isDirectory()：判断是否是文件目录
     *      public boolean isFile() ：判断是否是文件
     *      public boolean exists() ：判断是否存在
     *      public boolean canRead() ：判断是否可读
     *      public boolean canWrite() ：判断是否可写
     *      public boolean isHidden() ：判断是否隐藏
     *
     *      创建、删除类方法：
     *      创建硬盘中对应的文件或文件目录
     *      > public boolean createNewFile() ：按当前File对象所包含的路径信息进行对象创建，若路径在硬盘中并不存在，报错：找不到文件路径；
     *              若当前路径下已经含有相应的文件，则不创建文件，返回false
     *
     *      > public boolean mkdir() ：按当前File对象所包含的路径信息创建文件夹，成功返回true，失败返回false。
     *              注意： mkdir()一次只能创建一级文件夹；
     *                  mkdir()创建文件夹时，若上层文件夹不存在则创建失败，返回false
     *
     *      > public boolean mkdirs() ：按当前File对象所包含的路径信息创建文件夹，成功返回true，失败返回false
     *              注意：mkdirs()方法会按当前File的路径信息一次性创建文件夹，若上层文件夹不存在或者路径缺失（部分文件夹存在或失败）一并创建
     *
     *     删除磁盘中的文件或文件目录
     *     > public boolean delete()：删除文件或者文件夹
     *              注意：删除一次只能删除一级文件夹；若文件不存在则删除失败
     *
     *     重命名
     *     > public boolean renameTo(File f):renameTo可以进行文件的重命名，也可以进行文件的移动
     *         说明：要想renameTo()返回true，那么当前File（this）必须在硬盘中存在，而目标File（f）在硬盘中不能存在
     *
     */

    /**
     *  关于文件夹的两个方法
     *      > public String[] list() ：获取指定目录下的所有文件或者文件目录的名称数组
     *      > public File[] listFiles() ：获取指定目录下的所有文件或者文件目录的File数组
     */
}
