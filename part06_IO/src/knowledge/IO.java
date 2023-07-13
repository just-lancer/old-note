package knowledge;

public class IO {
    /**
     *  IO体系
     *      IO流的存在相当于数据管道，连接数据池和数据目的地
     *
     *  1、IO流分类
     *      按数据传输单位的不同，可以分为字节流（byte）和字符流（char）
     *      按站立角度不同，可以分为输入流和输出流
     *      按角色不同，可以分为节点流和处理流
     *
     *  2、IO流四大抽象基类
     *                                  节点流                   缓冲流
     *      字节输入流：InputStream       FileInputStream         BufferedInputStream
     *      字节输出流：OutputStream      FileOutputStream        BufferedOutputStream
     *      字符输入流：Reader            FileReader              BufferedReader
     *      字符输出流：Writer            FileWriter              BufferedReader
     *
     *      说明：对于纯文本文件，使用字符流去处理
     *          对于非纯文本文件，使用字节流去处理
     *
     *  3、基类中定义的方法
     *      输入流：InputStream / Reader
     *          > public int read()
     *          > public int read(byte[] b)
     *          > public int read(byte[] b, int offset, int len)
     *
     *       说明：程序中的IO资源不属于内存里的资源，垃圾回收机制无法回收该资源，所以需要显式地关闭IO资源
     *            程序从文件中读取数据时，该文件在硬盘中必须存在
     *
     *            输入流中，被被读取的文件必须存在，否则报FileNotFound异常
     *
     *      InputStream:
     *          > public int read():从输入流中读取数据的下一个字节。返回0~255范围内的int字节值，
     *                              说明：read()方法的返回值就是该从流中读取的数据，因为没有“容器”来装，所以只能返回
     *                              如果因为已经到达流末尾而没有可用的字节，则返回-1
     *
     *          > public int read(byte[] b):从输入流中将最多b.length个字节的数据读入一个byte数组中，
     *                              说明：因为read(byte[] b)方法传入了一个用于数据存储的数组，所以，读入的数据会放入数组中；并且其方法的返回值表示实际读取的字节数
     *                              如果因为已经到达流末尾而没有可用的字节，则返回-1
     *
     *          > public int read(byte[] b, int offset, int len)：与read(byte[] b)方法相同，区别在于，存放数据是从偏置offset开始，存放指定长度len的字节数
     *
     *          > public void close() throws IOException:关闭此输入流，并释放与该流相关的所有系统资源
     *
     *          对read()方法的说明：read()方法中应该是有一个指针的，每读取一个字节都会向下移动一次，直到遇到null
     *
     *      Read:
     *          > public int read()
     *          > public int read(char[] b)
     *          > public int read(char[] b, int offset, int len)
     *          > public void close() throws IOException:关闭此输入流，并释放与该流相关的所有系统资源
     *
     *          Read的read()方法与InputStream的read()方法基本相同，不同点在于，Read的read()方法一次读取的单个字符，范围在0~65535之间；多出的部分，进行高位截取，低位保留（也该不会有这种情况）
     *
     *
     *      输出流：OutputStream / Writer
     *          两者方法基本相似，区别在于，字符输出流能够输出字符串
     *
     *          OutputStream:
     *              > public void write(int b)：将指定的字节写入此输出流。一次只能写入一个byte范围大小的数据，超出的高位将被忽略
     *
     *              > public void write(byte[] b)：将b.length长度的字节从数组中写入输出流
     *
     *              > public void write(byte[] b, int offset, int len)：从指定索引开始，写入len个字节到流中
     *
     *              > public void flush() throws IOException：刷新此输出流，并强制写出所有缓冲字节到其指定目标
     *
     *              > public void close() throws IOException：关闭此流，并释放与之相关的所有系统资源
     *
     *          Writer:
     *              > public void write(int b)：写入单个字符，16位的数据会被写入，高于16位的数据将被截取
     *
     *              > public void write(char[] c)：将字符数组写入到流中
     *
     *              > public void write(char[] c, int offset, int len)：从偏置offset开始写入len个字符到流中
     *
     *              // 对于写入到流的操作，可以直接写入字符，也可以写入字符串
     *              > public void write(String str)：写入一个完整的字符串
     *              > public void write(String[] str, int offset, int len)：写入一个字符串的一部分
     *
     *              > public void flush()：刷新该流的缓冲，并立即将其写入文件中
     *              > public void close() throws IOException：关闭此输出流并释放与之相关的所有系统资源
     *
     *         说明：对于字符流，其输入输出存在一个缓冲区，用于减少硬盘与内存的交互次数，提高效率
     *
     */

    /**
     *  节点流（文件流）：以文件流讲解利用IO流进行数据读取写入的数据的步骤
     *
     *      读取文件：
     *      > 1、创建数据节点，即数据源或数据目的地
     *      File file = new File("hello.txt");
     *
     *      > 2、创建（节点）流，将数据源或数据目的地连接起来
     *      // 以字节流为例
     *      FileInputStream fis = new FileInputStream(file); // File对象经常作为流构造器的参数
     *
     *      > 3、读取数据
     *      int a = fis.read();
     *
     *      > 4、关闭资源，并处理异常
     *      fis.close();
     *
     *      写入文件
     *      > 1、创建流对象和数据存放文件
     *      FileOutputStream fos = new FileOutputStream("hello1.txt");
     *
     *      > 2、将文件写入流中
     *      fos.write(16);
     *
     *      > 3、关闭流资源
     *      fos.close();
     *
     *      说明：
     *          > 写入文件路径时，可以使用"\\"或者"/"或者Fiel.separator
     *          > 在写入（将数据输出到硬盘上，输出流）一个文件时，
     *              如果使用构造器FileOutputStream(File file)，并写入目录下有同名文件时，该同名文件将被覆盖
     *              如果使用FileOutputStream(File file, boolean true)，那么会在同名文件中进行追加写入操作
     *          > 在读取文件时，必须保证被读写的文件已经存在，否则报异常
     *
     */

    /**
     *  缓冲流：是一种处理流，作用是提高节点流的读取速度。基本原理是提供了一个缓冲区，在数据读写时减少内存与硬盘的交互次数，进而提高效率
     *  处理流：一种作用在其他流上的流
     *
     *  缓冲字节输入流：BufferedInputStream
     *  缓冲字节输出流：BufferedOutputStream
     *  缓冲字符输入流：BufferedReader
     *  缓冲字符输出流：BufferedWriter
     *
     *  说明：缓冲字符输入流BufferedReader除了重载的read()方法外，还有一个readline()方法
     *      > public String readLine() throws IOException:一次性读入一行字符，并用字符串进行返回
     *
     *      缓冲字符输出流BufferedReader除了重载的write()方法以外，还有一个newLine()方法
     *      > public void newLine() throws IOException:向内存中写入一个换行
     *
     *
     *  说明：利用缓冲流进行数据读取和写入时，需要创建两个流。
     *      一个是节点流，一个是缓冲流
     *      创建时：先创建节点流，再创建缓冲流
     *      关闭时，先关闭缓冲流，再关闭节点流
     *
     */

    /**
     *  转换流：也是一种处理流，其作用是将字节输入流、字节输出流转换成字符输入流、字符输出流。可以按照指定的编码或解码方式
     *
     *  字符输入转换流：InputStreamReader，将一个字节输入流转换成字符输入流
     *  字符输出转换流：OutputStreamWriter，将一个字符输出流转换成字节输出流
     *
     *  说明：转换流是一种字符流，并且没有字节流。看其结尾部分
     *
     *  构造器：
     *  > InputStreamReader(FileInputStream fis)：将节点流所对应的字节流按IDEA默认的解码方式转换成字符流读进内存中的变量中
     *  > InputStreamReader(FileInputStream fis, String decode)：将节点流所对应的字节流按指定的编码方式转换成字符流读进内存变量中
     *  说明：还有其他的构造器，很少用
     *
     *  > OutputStreamReader(FileOutputStream fos)：将原本以字符流输出的数据按IDEA默认的编码方式转换成字节流写入到内存中
     *  > OutputStreamReader(FileOutputStream fos, String encode)：将原本以字符流输出的数据按指定的编码方式转换成字节流写入到内存中
     *  说明：还有其他的构造器，很少用
     *
     *  字符集：
     *  > ASCII：美国标准信息交换码，用一个字节的7位可以表示
     *  > ISO8859-1：拉丁码表。欧洲码表，用一个字节的8位表示
     *  > GB2312：中国的中文编码表。最多两个字节编码所有字符
     *  > GBK：中国的中文编码表升级，融合了更多的中文文字符号。最多两个字节编码
     *  > Unicode：国际标准码，融合了目前人类使用的所有字符。为每个字符分配唯一的字符码。所有的文字都用两个字节来表示。
     *  > UTF-8：变长的编码方式，可用1-4个字节来表示一个字符。
     *  说明：Unicode只是一个字符集，包含了全人类所有使用的字符，但其本身存在一些问题无法直接落地。
     *      而UTF-8、UTF-16、UTF-32都是其落地的具体体现
     *
     */

    /**
     *  标准输入、输出流
     *  1.标准的输入、输出流
     *     1.1
     *     System.in:标准的输入流，默认从键盘输入
     *     System.out:标准的输出流，默认从控制台输出
     *     1.2
     *     System类的setIn(InputStream is) / setOut(PrintStream ps)方式重新指定输入和输出的流。
     *
     *     1.3练习：
     *     从键盘输入字符串，要求将读取到的整行字符串转成大写输出。然后继续进行输入操作，
     *     直至当输入“e”或者“exit”时，退出程序。
     *
     *     方法一：使用Scanner实现，调用next()返回一个字符串
     *     方法二：使用System.in实现。System.in  --->  转换流 ---> BufferedReader的readLine()
     *
     */

    /**
     *  数据流
     *  3. 数据流
     *     3.1 DataInputStream 和 DataOutputStream
     *     3.2 作用：用于读取或写出基本数据类型的变量或字符串
     *
     *     练习：将内存中的字符串、基本数据类型的变量写出到文件中。
     *
     *     注意：处理异常的话，仍然应该使用try-catch-finally.
     *
     *     将文件中存储的基本数据类型变量和字符串读取到内存中，保存在变量中。
     *
     *     注意点：读取不同类型的数据的顺序要与当初写入文件时，保存的数据的顺序一致！
     */


    /**
     *  对象流：也是一种处理流
     *      用于存储和读取基本数据类型或对象的处理流。与数据流不同的是，对象流能处理存取对象
     *
     *  > 对象字节输入流：ObjectInputStream
     *  > 对象字节输出流：ObjectOutputStream
     *
     *  序列化：ObjectInputStream将对象数据或对象从硬盘中读入到内存中的机制
     *  反序列化：ObjectOutputStream从内存中将对象或对象数据写入到硬盘中的机制
     *
     *  需要说明的是：ObjectInputStream和ObjectOutputStream不能序列化或反序列化static和transient修饰的成员变量
     *
     *  对象序列化机制：把内存中的Java对象转换成与平台无关的二进制流，从而允许把这种二进制流持久地保存在磁盘上，
     *      或者通过网络将这种二进制流传输到另一个网络节点。
     *      当其他程序获取了这种二进制流，就可以恢复成原来的Java对象
     *
     *   对象序列化：凡是实现了Serializable接口的类，都可以进行序列化
     *          而实现了Serializable的类都会有一个静态常量：public static final long serialVersionUID
     *          这个常量的作用是，在序列化或者反序列化的时候，Java会根据这个静态常量来完成序列及反序列化
     *              如果这个常量变了，那么可能出现错误
     *
     */

    /**
     *  随机存取文件流；RandomAccessFile类
     *  RandomAccessFile的使用
     *   1.RandomAccessFile直接继承于java.lang.Object类，实现了DataInput和DataOutput接口
     *   2.RandomAccessFile既可以作为一个输入流，又可以作为一个输出流
     *
     *   3.如果RandomAccessFile作为输出流时，写出到的文件如果不存在，则在执行过程中自动创建。
     *     如果写出到的文件存在，则会对原有文件内容进行覆盖。（默认情况下，从头覆盖）
     *
     *   4. 可以通过相关的操作，实现RandomAccessFile“插入”数据的效果
     */

}
