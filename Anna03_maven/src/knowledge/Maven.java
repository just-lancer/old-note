package knowledge;

public class Maven {
    /**
     *  Maven学习
     *  1、为什么需要使用Maven，她能解决什么问题？
     *      > ①获取并添加第三方jar包
     *          以前的获取第三方jar包的方式：老师或其他人准备的jar包，或者从不规范的地方下载
     *          现在获取第三方jar包：从Maven中央仓库下载
     *          以前添加jar包：复制jar包到lib文件夹下，再Build Path
     *          现在添加jar包：使用Maven的核心配置文件pom.xml直接依赖，多个项目需要相同的jar，本地仓库只需要存在一份即可
     *      > ②管理jar包之间的依赖关系
     *      > ③管理项目，将大的项目拆分成小的项目，开发完成后，再将各个项目整合成一个大的项目
     *
     *  2、Maven是什么？
     *      Maven是Apache软件基金会组织维护的一款自动化构建工具，专注服务于Java平台的项目构建和依赖管理
     *
     *      ① 什么是构建？
     *          首先需要声明的是，构建不是创建。构建一个项目不等于创建一个工程。
     *          构建就是以我们编写的Java代码、框架配置文件、国际化等其他资源文件、JSP页面和图片等静态资源作为“原材料”， “生产”出一个可以运行的项目的过程。
     *
     *      ② 构建包含的主要环节？
     *          > 清理：删除以前的编译结果，为新的编译过程做准备
     *          > 编译：将Java源代码编译成字节码文件
     *          > 测试：针对项目中的关键点进行测试，确保项目在迭代开发过程中关键点的正确性
     *          > 报告：在每一次测试后以标准的格式记录和展示测试结果
     *          > 打包：将一个包含诸多文件的工程封装为一个压缩文件用于安装或部署。Java工程对应jar包，Web工程对应war包
     *          > 安装：在Maven环境下特指将打包的结果：jar包或war包安装到本地仓库中
     *          > 部署：将打包的结果部署到远程仓库或将 war包部署到服务器上运行
     *
     *      ③ Maven的核心概念
     *          > 约定的目录结构
     *          > POM配置文件
     *          > 坐标
     *          > 管理依赖
     *          > 仓库管理
     *          > 生命周期
     *          > 插件和目标
     *          > 继承
     *          > 聚合
     *
     *  3、如何使用Maven？
     *      Maven的核心程序中仅仅定义了抽象的生命周期，而具体的操作则是由 Maven 的插件来完成的。
     *      但是Maven的插件并不包含在Maven的核心程序中，在首次使用时需要联网下载。
     *      下载得到的插件会被保存到本地仓库中。 本地仓库默认的位置是： ~\.m2\repository。
     *
     *  Maven的核心概念解析
     *  ① 约定的目录结构
     *      约定的目录结构对于Maven实现自动化构建而言是必不可少的一环，就拿自动编译来说，Maven必须能找到Java源文件，下一步才能编译，
     *      而编译之后也必须有一个准确的位置保持编译得到的字节码文件。
     *      我们在开发中如果需要让第三方工具或框架知道我们自己创建的资源在哪，那么基本上就是两种方式：一是通过配置的形式明确告诉第三方工具或框架，二是通过约定的方式。
     *      Maven两种方式都允许，其中对工程目录结构的要求属于后者，Maven也能够通过配置文件对资源进行识别和管理
     *
     *      以Hello项目为例，创建Maven约定的目录结构
     *      Hello                       --工程名作为根目录
     *      |----src                    --源码目录
     *          |----main               --主程序目录
     *              |----java           --主程序Java源文件目录
     *              |----resources      --主程序资源的文件目录
     *          |----test               --测试程序目录
     *              |----java           --测试程序的Java源文件目录
     *              |----resources      --测试程序资源的文件目录
     *      |----target                 --编译结果
     *
     *       Maven 正是因为指定了特定文件保存的目录才能够对我们的 Java 工程进行自动化构建。
     *
     *  ② POM配置文件
     *      Project Object Model：项目对象模型。将Java工程的相关信息封装为对象作为便于操作和管理的模型。
     *      Maven工程的核心配置。可以说学习Maven就是学习pom.xml文件中的配置。
     *
     *      具体的pom.xml示例见配置文件
     *
     *  ③ 坐标
     *      Maven坐标，是一个jar包或者war包的唯一标识
     *      Maven坐标的构成
     *          > groupId：公司或组织的域名倒序 + 当前项目名称
     *          > artifactId：当前项目的模块名称
     *          > version：当前模块的版本
     *
     *          例如：
     *          <dependencies>
     *              <dependency>
     *                  <groupId>com.atguigu.maven</groupId>
     *                  <artifactId>Hello</artifactId>
     *                  <version>0.0.1-SNAPSHOT</version>
     *              </dependency>
     *          </dependencies>
     *      通过坐标到本地仓库中查找对应的jar包：将 gav 三个向量连起来，以连起来的字符串作为目录结构到仓库中查找
     *
     *  ④ 依赖：分项目依赖jar包和jar包依赖jar包
     *      > 依赖的描述：当项目中用到了第三方jar包中的功能或者a_jar包中用到了b_jar包中的功能，那么就说该项目依赖该第三方jar包，a_jar包依赖b_jar包
     *
     *      > Maven管理依赖：在Maven项目的pom.xml文件中，使用dependency标签指定被依赖的jar包即可。
     *          需要说明的是，jar包也是Maven项目打包的，所以也会有pom.xml配置文件
     *
     *      > 依赖的范围：使用scope标签指定依赖的范围。常用的依赖范围有compile、test、provided
     *          有效性范围说明：
     *                      compile     test        provided
     *          主程序         √          ×             √
     *          测试程序       √          √             √
     *          参与部署       √          ×             ×
     *
     *      > 依赖的传递性：当A依赖jar包B，jar包B依赖jar包C，那么A是否能够依赖jar包C呢，换句话说，jar包C是否对A可见
     *          A --> B --> C   那么A是否能够依赖C则需要取决于B依赖C时，其依赖范围是否是compile，如果是，那么A也能够依赖于C，否则A不能依赖于C
     *
     *      > 依赖的排除：当A依赖jar包B，jar包B依赖jar包C，假设jar包B对jar包C的依赖是compile，而且此时A不想对jar包C产生依赖，那么可以进行依赖排除
     *          排除方式：在A的pom.xml文件中利用exclusion标签进行排除。指明jar包的坐标只需要groupId和artifactId，不需要指明版本号。这样在依赖排除时，
     *          会将所有的该版本的jar包都排除。
     *          例如：
     *          <exclusions>
     *              <exclusion>
     *                  <groupId>com.atguigu.maven</groupId>
     *                  <artifactId>Hello</artifactId>
     *              </exclusion>
     *          </exclusions>
     *
     *      > 依赖的统一管理：为了对各个模块依赖的相同的jar包的版本管理，可以将jar包的版本信息统一提取出来，进行统一
     *          步骤一：统一声明版本号：
     *              <properties>
     *                  <!-- user.defined.label 是自定义标签名 --!>
     *                  <user.defined.label>0.0.1-SNAPSHOT</user.defined.label>
     *              </properties>
     *          步骤二：引用自定义声明的版本号：
     *              <dependencies>
     *                  <dependency>
     *                      <groupId>com.atguigu.maven</groupId>
     *                      <artifactId>Hello</artifactId>
     *                      <version>${user.defined.label}</version>
     *                  </dependency>
     *              </dependencies>
     *
     *      > 依赖的原则：用于解决jar包冲突
     *          原则一：路径最短优先原则
     *          原则二：当路径长度相同时，pom.xml文件中，先声明者优先
     *
     *  ⑤ 仓库：存放第三方jar包的地方
     *      分类；
     *      本地仓库：为当前本机电脑上的所有 Maven 工程服务。
     *      远程仓库：
     *          私服：为当前本机电脑上的所有 Maven 工程服务。
     *          中央仓库：为当前本机电脑上的所有 Maven 工程服务。
     *          中央仓库镜像：架设在各个大洲，为中央仓库分担流量。减轻中央仓库的压力，同时更快的响应用户请求。
     *
     *  ⑥ 生命周期：
     *      Maven命周期定义了各个构建环节的执行顺序，有了这个清单， Maven就可以自动化的执行构建命令了。
     *
     *      Maven 有三套相互独立的生命周期， 分别是：
     *          Clean Lifecycle 在进行真正的构建之前进行一些清理工作。
     *          Default Lifecycle 构建的核心部分，编译，测试，打包，安装，部署等等。
     *          Site Lifecycle 生成项目报告，站点，发布站点。
     *      它们是相互独立的，你可以仅仅调用 clean 来清理工作目录，仅仅调用 site 来生成站点。
     *      当然你也可以直接运行 mvn clean install site 运行所有这三套生命周期。
     *
     *      生命周期与自动化构建：运行任何一个阶段的时候，它前面的所有阶段都会被运行。
     *      例如我们运行mvn install的时候，代码会被编译，测试，打包。 这就是 Maven 为什么能够自动执行构建过程的各个环节的原因。
     *
     *  ⑦ 插件和目标：
     *      Maven 的核心仅仅定义了抽象的生命周期，具体的任务都是交由插件完成的。
     *      每个插件都能实现多个功能，每个功能就是一个插件目标。
     *      Maven 的生命周期与插件目标相互绑定，以完成某个具体的构建任务。
     *
     *  ⑧ 继承：
     *      概念：一个 maven项目可以继承另一个 maven的依赖，称为子项目 父项目
     *
     *      使用场景：多个子项目都需要依赖一些相同的jar包，那么就可以把子项目共同的依赖抽取到父项目中，
     *          子项目通过继承便能够得到这些依赖，这样也更好的来管理(比如升级, 删除等)
     *
     *      创建父子Maven项目的步骤：
     *      > 1、创建父Maven项目：注意：打包方式为pom
     *          <groupId>com.atguigu.maven</groupId>
     *          <artifactId>Parent</artifactId>
     *          <version>0.0.1-SNAPSHOT</version>
     *      > 2、编辑父Maven项目pom.xml文件，使用dependencyManagement标签来管理子项目能够继承到的依赖，optional 表示子 pom 无论如何都不能继承
     *          <dependencyManagement>
     *              <dependencies>
     *                    <!-- 子 pom 可以继承 -->
     *                    <dependency>
     *                        <groupId>com.alibaba</groupId>
     *                        <artifactId>fastjson</artifactId>
     *                        <version>1.2.47</version>
     *                    </dependency>
     *                    <!-- 子 pom 不可以继承 -->
     *                    <dependency>
     *                        <groupId>log4j</groupId>
     *                        <artifactId>log4j</artifactId>
     *                        <version>1.2.17</version>
     *                        <optional>true</optional>
     *                    </dependency>
     *              </dependencies>
     *          </dependencyManagement>
     *      > 3、子项目配置父项目
     *          <parent>
     *              <!-- 父项目坐标 -->
     *              <artifactId>parent</artifactId>
     *              <groupId>com.ictpaas</groupId>
     *              <version>1.0-SNAPSHOT</version>
     *              <!-- 指定从当前子工程的pom.xml文件出发，查找父工程的pom.xml的路径-->
     *              <relativePath>../parent/pom.xml</relativePath>
     *          </parent>
     *      > 4、子项目依赖配置
     *          <dependencies>
     *              <!-- 不需要版本, 会从父项目继承, 如果指定版本，就是表示不是来自父pom而是子pom自己的，父项目的log4j是不能继承的 -->
     *              <dependency>
     *                  <groupId>com.alibaba</groupId>
     *                  <artifactId>fastjson</artifactId>
     *              </dependency>
     *          </dependencies>
     *
     *      说明：子项目不仅仅继承依赖，url，name，modeVersion等也能继承，换句话说子当前pom文件内容很少，看起来很简洁。完整的pom文件能包含很多东西
     *
     *  ⑨ 聚合
     *      为什么使用聚合？
     *      将多个工程拆分为模块后，需要手动逐个安装到仓库后依赖才能够生效。修改源码后也需要逐个手动进行clean操作。而使用了聚合之后就可以批量进行Maven工程的安装、清理工作。
     *
     *      在使用Java开发项目时，一种常见的情形是项目由多个模块组成，软件开发人员往往会采用各种方式对软件划分模块，以得到更清晰的设计以及更高的重用性。
     *      Maven的聚合特性能够帮助把项目的各个模块聚合在一起构建。
     *
     *      如何配置聚合？
     *      在总的聚合工程中使用modules/module标签组合，指定模块工程的相对路径即可。
     *      例如：
     *      <modules>
     *          <!-- 指定从当前父工程的pom.xml文件出发，查找子工程的pom.xml的路径-->
     *          <module>../Hello</module>
     *          <module>../HelloFriend</module>
     *          <module>../MakeFriends</module>
     *      </modules>
     */
}
