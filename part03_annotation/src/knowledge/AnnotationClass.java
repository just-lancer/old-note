package knowledge;

public class AnnotationClass {
    /**
     * 注解（Annotation）主要内容
     * 1、注解概述
     * 2、常见的注解示例
     * 3、自定义注解
     * 4、JDK中的元注解
     * 5、注解的使用：利用反射获取注解信息（反射中讲解）
     * 6、JDK 8中注解的新特性
     */

    /**
     * 一、注解的概述
     *      注解是代码里的特殊标记，这些标记可以在编译，类加载，运行时被读取，并执行相应的处理
     *      通过注解，我们可以在不改变原有逻辑的情况下，在源文件中嵌入一些补充信息
     *      代码分析工具、开发工具和部署工具可以通过这些补充信息进行验证或者进行部署
     *
     *      在JavaSE阶段中，注解的使用较为简单，如标记过时的功能、忽略警告等
     *      而在JavaEE/Android中都有着较为重要的应用
     *      未来的开发模式也都是基于注解的，一定程度上，框架 = 注解 + 反射 + 设计模式
     *
     */

    /**
     * 二、常见的Annotation示例
     *      注解大致可以分为三类
     *      ① 生成文档相关的注解
     *      ② 在编译是进行格式检查（JDK内置的三个注解）
     *          // @override- 检查该方法是否是重写方法
     *          // @Deprecated- 标记过时的方法
     *          // @suppressWarnings- 指示编译器忽略注解中声明的警告
     *      ③ 跟踪代码依赖性，实现替代配置文件功能
     *          反射中详细说明
     */

    /**
     * 三、自定义注解(Annotation)
     *      定义新的Annotation类型使用@interface关键字
     *      自定义注解自动实现java.lang.annotation.Annotation接口
     *      Annotation的成员变量在Annotation定义中以无参方法的形式来声明。其方法名和返回值类型定义了该成员的名字和类型
     *          其类型只能是八种基本数据类型、String类型、Class类型、enum类型、Annotation类型
     *      可以在定义Annotation的成员变成时为其指定初始值（默认值），利用关键字default进行定义
     *
     *      注意：自定义注解必须配上注解的信息处理流程（反射）才有意义
     */

    /**
     * 四、四种元注解
     *      元注解：用于修饰其他注解的注解
     *      // @Retention - 用于指定被修饰注解的声明周期，@Retention包含一个RetentionPolicy类型的成员变量，
     *          使用@Retention时必须为该注解的value成员变量赋值
     *          RetentionPolicy是一个枚举类，包含三个成员变量
     *              > RetentionPolicy.SOURECE：在源文件中有效（即源文件保留），编译器会丢弃这种策略的注解
     *              > RetentionPolicy.CLASS：在.class文件中有效（即.class文件中保留），当运行Java程序时，JVN不会保留注释
     *                  注意：这是@Retention注解成员变量的默认值
     *              > RetentionPolicy.RUNTIME：在运行时有效（即运行时也保留），当运行Java程序时，JVM会保留注释，程序可以通过反射获取该注释
     *
     *       // @Target - 用于指定被修饰的注解能用于修饰哪些程序元素，@Target也有一个value变量
     *              > TYPE - 用于描述类、接口、枚举类
     *              > CONSTRUCTOR - 用于描述构造器
     *              > PACKAGE - 用于描述构造器
     *              > FIELD - 用于描述属性
     *              > PARAMETER - 用于描述形式参数
     *              > LOCAL_VARIABLE - 用于描述局部变量
     *              > MOTHOD - 用于描述方法
     *
     *      // @Documented - 被修饰的注解能够被javadoc工具识别并保留
     *              如API中，java.util.Date类中的一些方法被修饰为@Deprecated
     *
     *      // @Inherited - 被修饰的注解会具有继承性，
     *              如果某个类使用了被@Inherited修饰的注解，那么该类的子类会自动继承这个注解
     *
     */

    /**
     * 五、注解的使用：利用反射获取注解信息（反射中讲解）
     */

    /**
     * 六、JDK 8中的注解新特性
     *      > 可重复的注解
     *      > 用于类型的注解
     */
}

