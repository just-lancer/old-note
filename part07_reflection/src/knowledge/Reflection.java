package knowledge;

public class Reflection {
    /**
     *  反射
     *
     *  1、动态语言与静态语言
     *      动态语言：是一类可以在运行时可以改变其结构的语言，如：Object-C、C#、JavaScript、PHP、python
     *      静态语言：与动态语言相反，静态语言在运行时不能改变其结构，如Java、C
     *
     *      Java虽然不是动态语言，但因为其反射的机制，Java具有一定的动态性，可以称之为准动态语言
     *
     *  2、反射机制：允许程序执行期间借助于Reflection API获取任何类的内部信息，并能直接操作任意对象的内部属性及方法
     *
     *  3、java.lang.Class类
     *     1.类的加载过程：
     *     程序经过javac.exe命令以后，会生成一个或多个字节码文件(.class结尾)。
     *     接着我们使用java.exe命令对某个字节码文件进行解释运行。相当于将某个字节码文件加载到内存中。此过程就称为类的加载。
     *
     *     加载到内存中的类，我们就称为运行时类，此运行时类，就作为Class的一个实例。
     *
     *     2.换句话说，Class的实例就对应着一个运行时类。
     *          类加载完成之后，在堆内存的方法区中就产生了一个Class类型的对象，这个Class类型对象就包含了完整的类的结构信息
     *          通过这个对象，我们可以看到类的所有结构。
     *
     *          说明：
     *              这个类就像一面镜子，通过这个镜子可以看到类的结构，所以称之为反射
     *              一个类只会有一个Class对象
     *
     *     3.加载到内存中的运行时类，会缓存一定的时间。在此时间之内，我们可以通过不同的方式
     *     来获取此运行时类。
     *
     *  4、对java.lang.Class类的理解：
     *      > 在Object类中定义了public final Class getClass()方法，该方法会返回当前对象所属类的对象。
     *          这也从侧面反映了类也是个对象，并且类对象都是Class类的实例
     *      > 类是用来创造对象的，而类本身也是一个对象；这就好像，概念是用于解释一个名词的，而概念本身也是一个概念一样
     *
     *      > 对于一个Class类对象的创建而言，意味着：
     *          > 一个类被加载到JVM的方法区中
     *          > 一个类只会反射出一个Class类对象，这个对象包含了该类的所有信息：属性、方法、构造器、注解、访问权限、接口等等
     *              所以类反射出的Class对象是反射的源头，从这里开始，可以做很多操作
     *
     *              因此，首要问题是获取相应的Class类对象
     *
     *  5、获取Class类的四种方法：以自定义Person类为例
     *      > 方法一：通过已知类的class属性获取Class类对象
     *              Class clazz = Person.class;
     *              注意：数组对象不能利用此种方式创建Class对象
     *
     *      > 方法二：通过已知类的实例对象调用getClass()方法获取CLass类对象
     *              Class clazz = person1.getClass();
     *
     *      > 方法三：当已知一个类的全类名（包含：包名和类名），可以通过Class类的静态方法forName()获取。此方法会抛ClassNotFoundExeceotion
     *              Class clazz = Class.forName("java.lang.String");
     *
     *      > 方法四：使用类加载器ClassLoader调用loadClass创建Class对象
     *          // 第一步：利用自定义类的类名调用class方式创建Class对象
     *          // 第二步：利用Class对象调用getClassLoad()方法，获取类加载器
     *          // 第三步：利用类加载器调用loadClass(String classPath)方法创建Class对象
     *
     *
     *  6、Class类对象可以是哪些结构？或者说，哪些结构可以有Class对象
     *      > 外部类、类的5个成员（包含静态内部类）、局部类内部、匿名内部类
     *      > 接口
     *      > 数组：需要说明的是，只要数组的维度和数组元素的类型相同，就是同一个Class
     *      > 枚举类
     *      > 注解
     *      > 基本数据类型
     *      > void
     *
     *  7、Class类的常用方法
     *      > static Class forName(String classPath)：根据指定的全类名classPath，创建一个Class对象，并返回
     *
     *      > public Object newInstance()：Class对象调用此方法，会创建Class类对象所对应的类的对象
     *          说明：Class类对象必须提供空参构造器；所提供的构造器的访问权限必须能够让运行时类访问
     *
     *      > public String getName()：返回此Class类对象所表示的实例（类、数组、接口、基本数据类型）的名称
     *
     *      > public native Class getSuperclass()：返回此Class类对象的直接父类的CLass对象
     *
     *      > public Class[] getInterfaces()：返回此Class类对象所实现的接口
     *
     *      > public ClassLoader getClassLoader()：返回加载此运行时类的类加载器
     *
     *      > public Constructor<T> getConstructor(Class<?>... parameterTypes)
     *          throws NoSuchMethodException, SecurityException：根据传入的不定长参数列表，返回一个该运行时类所对应类的权限能够访问的构造器
     *
     *      > public Constructor<?>[] getConstructors() throws SecurityException：返回该运行时类所对应类的全部权限可访问的构造器，顺序随机
     *
     *      > public Field[] getDeclaredFields() throws SecurityException：返回该运行时类所对应类的全部属性构成的数组，无视访问权限，顺序随机
     *
     *      > public Method getMethod(String name, Class<?>... parameterTypes)
     *         throws NoSuchMethodException, SecurityException：返回该运行时类所对应类的一个public的方法
     *
     *  8、类的加载过程：
     *      > 加载：将class文件字节码内容加载到内存中，并将这些静态数据转换成方法区中的运行时数据结构，在堆中生成一个代表这个类的java.lang.Class对象，作为方法区类数据的访问入口。
     *      > 链接：将java类的二进制代码合并到 jvm的运行状态之中的过程,链接过程又分为3个过程：
     *              验证：确保加载的类信息符合jvm规范，没有安全方面的问题。
     *              准备：正式为类变量（static变量）分配内存并设置类变量初始值的阶段， 这些内存都将在方法区中进行分配。
     *              解析：虚拟机常量池内的符号引用替换为直接引用的过程。（比如String s =“aaa”,转化为 s的地址指向“aaa”的地址）
     *      > 初始化：初始化阶段是执行类构造器方法的过程。类构造器方法是由编译器自动收集类中的所有类变量的赋值动作和静态语句块（static块）中的语句合并产生的。当初始化一个类的时候，如果发现其父类还没有进行过初始化，则需要先初始化其父类的初始化虚拟机会保证一个类的构造器方法在多线程环境中被正确加锁和同步 当访问一个java类的静态域时，只有真正声明这个静态变量的类才会被初始 化。
     *
     *      java类的生命周期：加载 --> 验证 --> 准备 --> 解析 --> 初始化 --> 调用 --> 卸载
     *
     *  9、类加载器：ClassLoader
     *      类加载器：将.class文件的字节码内容加载到内存中，并将这些静态数据转换成方法区的运行时数据结构，然后在堆中生成一个代表这个类的java.lang.Class对象，作为方法区中类数据的访问入口
     *      类缓存：当一个类被加载到内存中，它将维持一段加载时间。需要说明的是JVM垃圾回收机制能够回收这些Class对象
     *
     *      类加载器的分类：
     *          > 引导类加载器：用C++编写，是JVM自带的类加载器，负责Java核心类库的加载，该加载器无法直接获取
     *          > 扩展类加载器：负责jre/lib/ext目录下的jar包或者java.ext.dirs指定目录下的jar包的加载
     *          > 系统类加载器：负责java-classpath或者java.class.path目录下的类的加载
     *          > 自定义加载器：不建议自定义
     *
     *          说明：类加载时，从上到下尝试加载类；类加载检查时，从下到上检查类是否加载
     *
     *      类加载器相关方法：
     *          > public static ClassLoader getSystemClassLoader()：获取一个系统类加载器
     *          > public final ClassLoader getParent()：获取一个系统类加载器的父类加载器，及扩展加载器
     *          > public static InputStream getSystemResourceAsStream(String name)：返回一个类路径下的指定文件的输入流
     *
     *
     */
}
