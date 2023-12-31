package knowledge;

public class LinuxSystem {
    /**
     *  Linux操作系统学习
     *  一、Linux简介
     *
     *  二、虚拟机和Linux操作系统安装
     *  1、虚拟机
     *      虚拟机(英文全称：Virtual Machine)是指通过软件模拟的具有完整硬件系统功能的、运行在一个完全隔离环境中的完整计算机系统，
     *      目前流行的虚拟机软件有VMware(VMWare ACE)、Virtual Box和Virtual PC，它们都能在Windows系统上虚拟出多个计算机，
     *      每个虚拟计算机可以独立运行，可以安装各种软件与应用等。
     *
     *  2、Linux操作系统
     *      Linux是一套免费使用和自由传播的类Unix操作系统，是一个多用户、多任务、支持多线程和多CPU的操作系统。
     *      它能运行主要的UNIX工具软件、应用程序和网络协议。它支持32位和64位硬件。
     *      Linux继承了Unix以网络为核心的设计思想，是一个性能稳定的多用户网络操作系统。
     *
     *      简单来说，Linux是一个操作系统
     *
     *  3、Linux发行版
     *      Linux 发行版是一个由 Linux 内核、GNU 工具、附加软件和软件包管理器组成的操作系统，它也可能包括显示服务器和桌面环境，以用作常规的桌面操作系统。
     *      这个术语之所以是 “Linux 发行版”，是因为像 Debian、Ubuntu 这样的机构“发行”了 Linux内核以及所有必要的软件及实用程序（如网络管理器、软件包管理器、
     *      桌面环境等），使其可以作为一个操作系统使用。
     *      所以，“Linux” 是内核，而 “Linux 发行版”是操作系统。这就是为什么它们有时也被称为基于 Linux 的操作系统的原因。
     *
     *  4、虚拟机和Linux的安装
     *      为了学习和利用操作系统，我们首先需要创建一个Linux操作系统，因此首先需要创建一个虚拟机（或者拿一台安装了Linux系统的真机）
     *
     *      模拟虚拟机的软件：VMware
     *      Linux操作系统的发行版：CentOS 7
     *
     *      安装过程：
     *      ① 先安装虚拟机
     *      ② 在虚拟机中划分磁盘区域，先将磁盘划分好，然后再安装Linux操作系统
     *
     *  5、虚拟机网络连接的三种方式：
     *      ① 桥接方式：仿照物理主机上的网络配置，相当于在你电脑连接的局域网中多添加一台设备，虚拟机和你的电脑处于同一局域网内（同一网段），属于同级
     *                  桥接模式时，虚拟机的IP必须和主机在统一网段，且子网掩码、网关与DNS也要和主机网卡一致
     *
     *      ② NAT(Network Address Transaction, NAT)：以物理机（你的电脑）作为媒介（相当于交换机）访问外部网络，虚拟机就像物理机的下级，在物理机的基础下再创建（虚拟）一个局域网。
     *                  假如不在局域网内，只有一个ip,那可以采用NAT模式，在局域网中也可以使用NAT模式，不过相比于桥接模式，NAT模式更消耗性能。
     *      ③ 仅主机模式(Host-Only)：虚拟主机只能访问物理机，不能访问Internet,这是一个完全隔离网络的模式，Host-only相当于使用双绞线直接连接虚拟机和物理机（最原始的网络结构）
     *
     *
     *  三、Linux操作系统的文件与目录结构
     *      1、linux 的文件系统是采用级层式的树状目录结构，在此结构中的最上层是根目录“/”，然后在此目录下再创建其他的目录。
     *          在 Linux 世界里，一切皆文件（即使是一个硬件设备，也是使用文件来标识）。深刻理解 linux 树状文件目录是非常重要的。
     *
     *      2、目录结构
     *      /
     *          |----/root：超级用户（系统管理员）的主目录（特权阶级^o^）
     *          |----/bin：存放二进制可执行文件(ls,cat,mkdir等)，常用命令一般都在这里
     *          |----/home：存放所有用户文件的根目录，是用户主目录的基点，比如用户user的主目录就是/home/user，可以用~user表示
     *          |----/etc：存放系统管理和配置文件
     *          |----/usr：用于存放系统应用程序，比较重要的目录/usr/local本地系统管理员软件安装目录（安装系统级的应用）。这是最庞大的目录，要用到的应用程序和文件几乎都在这个目录。
     *              |----
     *          |----/boot：存放用于系统引导时使用的各种文件
     *          |----/media：Linux系统会自动识别一些设备，例如USB、光驱等，当识别后，Linux会把识别的设备挂载到该目录下
     *          |----/dev：类似于windows的设备管理器，把所有的硬件用文件的形式存储
     *          |----/var：用于存放运行时需要改变数据的文件，也是某些大文件的溢出区，比方说各种服务的日志文件（系统启动日志等。）等。
     *          |----/mnt：系统管理员安装临时文件系统的安装点，系统提供这个目录是让用户临时挂载其他的文件系统。
     *          |----/lib：存放跟文件系统中的程序运行所需要的共享库及内核模块。共享库又叫动态链接共享库，作用类似windows里的.dll文件，存放了根文件系统程序运行所需的共享文件。
     *          |----/opt：额外安装的可选应用程序安装包所放置的位置。一般情况下，我们可以把tomcat等都安装到这里。
     *          |----/proc：虚拟文件系统目录，是系统内存的映射。可直接访问这个目录来获取系统信息。
     *          |----/sbin：存放二进制可执行文件，只有root才能访问。这里存放的是系统管理员使用的系统级别的管理命令和程序。如ifconfig等。
     *          |----/tmp：用于存放各种临时文件，是公用的临时文件存储点。
     *
     *      小结：
     *          > Linux中，目录结构中只有一个根目录 /，其他目录都是根目录分支
     *          > Linux系统安装后，有很多目录，不要去修改
     *          > 在 linux 中，所有的设备都可以通过文件来体现(字符设备文件[比如键盘，鼠标]，块设备文件[硬盘])
     *
     *  四、Linux服务器的远程连接
     *      1、远程登录Linux服务器的原因
     *      2、Linux与远程管理软件--XShell
     *          Xshell是目前最好的远程登录到Linux操作系统的软件，流畅的速度并且完美解决了中文乱码的问题，是目前程序员首选的软件。
     *          Xshell是一个强大的安全终端模拟软件，它支持 SSH1, SSH2, 以及 Microsoft Windows平台的TELNET 协议。
     *          Xshell可以在Windows 面下用来访问远端不同系统下的服务器，从而比较好的达到远程控制终端的目的。
     *
     *      3、文件传输软件--Xftp
     *          Xftp是一个用于MS Windows平台的强大的FTP和SFTP文件传输程序。Xftp能安全地在Unix/Linux和Windows PC之间传输文件。
     *
     *  五、Vi和Vim编辑器
     *      所有的Linux操作系统都自带Vi编辑器，可以用于文件编辑；Vim编辑器是Vi的升级版，它不仅兼容vi的所有指令，而且还有一些新的特性在里面。
     *      相同的是Vi和Vim都是多模式编辑器。
     *
     *      Vi和Vim编辑器特点：Linux下标配的一个纯字符界面的文本编辑器，由于不是图形界面，相关的操作都要通过键盘输入命令来完成
     *
     *      只讲解Vim编辑器，因为功能向下兼容了Vi编辑器
     *
     *      1、启动Vim编辑器：
     *          [命令行]命令：Vim 文件名.后缀
     *          说明：如果文件存在，则打开该文件，如果参文件不存在，则会新建该文件（不是立即创建，而是在保存的时候创建）
     *      2、Vim编辑器的三种模式：
     *          > 正常模式：以vim打开一个文件就直接进入一般模式了(这是默认的模式)。
     *              在这个模式中，你可以使用『上下左右』按键来移动光标，你可以使用『删除字符』或『删除整行』来处理档案内容，
     *              也可以使用『复制、贴上』来处理你的文件数据。在正常模式下可以使用快捷键。
     *
     *          > 插入模式（编辑模式）：在正常或命令行模式下，按下i（insert）, I, o, O, a, A, r, R等任何一个字母之后才会进入编辑模式, 一般来说按i即可
     *              i：在当前光标所在位置插入随后输入的文本，光标后的文本相应向后移动
     *              I：在光标所在行的行首插入随后输入的文本，行首是改行的第一个非空白字符，相当于光标移动到首行执行
     *              o：在光标所在行的下面插入新的一行，光标停在空行行首，等待输入的文本
     *              O：在光标所在行的上面插入新的一行，光标停在空行的行首，等待输入的文本
     *              a：在当前光标所在位置之后插入随后输入的文本
     *              A：在光标所在行的行尾插入随后输入的文本，相当于光标移动到行尾再执行a命令
     *
     *          > 命令行模式：在正常模式下，输入':'进入命令行模式，在这个模式当中，可以提供你相关指令，完成读取、存盘、替换、离开 vim 、显示行号等的操作。
     *              相关命令：w --> write    q --> quit
     *              w：写入
     *              q：退出（如果文件未保存，则不允许退出）
     *              q!：不保存退出（强行退出）
     *              wq：保存并退出
     *              x：保存并退出
     *
     *      3、Vim编辑器的使用
     *          设置行号：
     *          命令行模式：
     *              > set nu：设置行号
     *              > set nonu：取消行号
     *
     *          ① 光标移动：
     *          正常模式：
     *              > 逐个字符移动：
     *              h：向左
     *              j：向下
     *              k：向上
     *              l：向右
     *
     *              > 以单词为单位移动：
     *              w：移动到下一个单词的词首
     *              e：跳至当前或下一个单词的词尾
     *              b：跳至当前或上一个单词的词首
     *
     *              > 行内跳转：
     *              0：绝对首行
     *              ^：行首第一个非空白字符
     *              $：绝对行尾
     *
     *          ② 复制：
     *          正常模式：
     *              > yy：复制当前行
     *              > #yy：复制从当前行向下的#行
     *              > p：粘贴
     *
     *          ③ 删除：
     *          正常模式：
     *              > dd：删除当前行
     *              > #dd：删除从前行向下的#行
     *
     *          ④ 查找：
     *          命令行模式：
     *              > /被查找关键字   Enter --> 查找      n --> 查找下一个
     *
     *          ⑤ 首尾：
     *          正常模式：
     *              > G：到达文档尾行行首
     *              > gg：到达文档首行行首
     *
     *          ⑥ 撤销：
     *          正常模式：
     *              > u：撤销一次动作
     *              > #u：撤销#次动作
     *
     *
     *  六、关机、重启和用户注销命令
     *      1、关机、重启
     *      说明：关机和重启之前必须执行sync命令，将内存中的数据写入磁盘保存
     *          shutdown
     *          > shutdown -h now   立即关机
     *          > shutdown -h #     #分钟后，立即关机
     *          > shutdown -r now   立即重启
     *          > shutdown -r #     #分钟后，立即重启
     *          > halt  挂起
     *          > reboot 立即重启
     *
     *      2、注销登录用户
     *
     *  七、用户管理
     *     基本介绍：Linux系统是一个多用户多任务的操作系统，任何一个要使用系统资源的用户，都必须首先向系统管理员申请一个账号，然后以这个账号的身份进入系统。
     *
     *     1、用户相关管理
     *      ① 添加用户并指定组名和家目录：useradd [-g groupId] [-d homeDirector] userName
     *        不显式指明组名时，组名即为用户名；不显式指明家目录时，以/home/userName作为家目录
     *
     *      ② 删除用户：userdel [-r] userName
     *          -r 表示删除用户的同时删除家目录
     *          一般不删除家目录
     *
     *      ③ 查询用户信息：id userName
     *          当用户存在时，展示用户id，用户所属组id，用户所属组名称
     *          当用户不存在时，展示无此用户
     *
     *      ④ 切换用户：su userName
     *          > 从权限较低的用户切换到权限高的用户，需要输入密码
     *          > 从权限高的用户切换到权限低的用户，不需要输入密码
     *          > 命令exit：返回原来的用户，无视权限
     *          > 当su没有带用户名，那么默认切换到root用户
     *
     *      ⑤ 查看当前登录的用户：whoami / who am i
     *
     *      2、用户组相关管理
     *          基本介绍：linux系统中的用户组(group)就是具有相同特性的用户(user)集合；
     *              值得注意的是用户的角色是通过UID和GID识别的；特别是UID，在运维工作中，一个UID是唯一标识一个系统用户的账号。
     *
     *          用户分类：
     *              > 超级用户 root（0）：
     *                  默认是root用户，其UID和GID均为0。在每台unix/linux操作系统中都是唯一且真实存在的，通过它可以登录系统，可以操作系统中任何文件和命令
     *                  拥有最高的管理权限。在生产环境，一般禁止root账号远程登录SSH连接服务器，以加强系统安全。
     *              > 程序用户（1~499）
     *                  与真实用户区分开来，这类用户的最大特点是安装系统后默认就会存在的，且默认情况不能登录系统，它们是系统正常运行必不可少的，
     *                  他们的存在主要是方便系统管理，满足相应的系统进程都文件属主的要求。例如系统默认的bin、adm、nodoby、mail用户等。
     *              > 普通用户（1000~65535）
     *                  这类用户一般是由具备系统管理员root的权限的运维人员添加的。
     *
     *          用户与用用户组的关系
     *              一对一：即一个用户可以存在一个组中，也可以是组中的唯一成员。比如，root。
     *              一对多：即一个用户可以存在多个组中，这个用户就具有这些组。
     *              多对一：即多个用户可以存在一个组中，这些用户具有该组的共同权限。
     *              多对多：即多用户可以存在于多个组中。并且几个用户可以归属相同的组；其实多对多的关系是前面三条的扩展。
     *
     *          用户组相关命令：
     *              > 添加用户组：groupadd groupName
     *              > 删除用户组：groupdel groupName
     *              > 修改用户所在的组：usermod -g groupName userName
     *
     *      3、用户及用户组配置文件
     *          Linux系统的账户文件主要有：/etc/passwd、/etc/shadow、/etc/group、/etc/gshadow四个文件
     *          用户相关配置文件：
     *              > /etc/passwd ：用户的配置文件，保存用户账户的基本信息
     *                  每行的含义 ：[用户名:口令:用户标识号:组标识号:注释性描述:家目录:使用的Shell类型]
     *              > /etc/shadow ：用户影子口令文件
     *                  每行的含义 ：[用户名:加密口令:最后一次修改时间:最小时间间隔:最大时间间隔:警告时间:不活动时间:失效时间:保留]
     *
     *          用户组相关配置文件：
     *              > /etc/group ：组(group)的配置文件，记录Linux包含的组的信息
     *                  每行含义：[组名:口令:组标识号(组账户GID号,用户组ID):组内用户列表]
     *              > /etc/gshadow ：用户组影子文件
     *                  /etc/gshaow是/etc/group的加密文件，比如用户组的管理密码就是存放在这个文件
     *
     *  八、Linux运行级别
     *      00  系统的关机级别                 poweroff.target   # init 0 -- 进入到关机模式
     *      01  系统的单用户模式               rescue.target     # 用于修复系统 或 重置密码信息  没有网络
     *      02  系统的多用户模式               multi-user.target # 没有网络
     *      03  系统的多用户模式               multi-user.target # 正常系统运行级别 多用户模式级别  有网络
     *      04  预留级别                      multi-user.target
     *      05  图形化界面级别                 graphical.target
     *      06  系统的重启级别                 reboot.target
     *
     *      > 查看当前系统运行级别：runlevel
     *      > 切换系统当前级别：init levelNum
     *
     *      面试题：如何找回丢失的root密码？
     *          进入单用户模式，更新root密码。单用户模式直接进入root账户，无需密码
     *
     *  九、常见的文件目录指令
     *      帮助指令
     *          语法格式
     *          > man commond/files
     *          > help commond
     *
     *      > pwd指令，显示当前工作目录的绝对路径
     *
     *      > ls指令，默认显示当前所在目录所包含的文件和文件夹
     *          语法格式：
     *              ls [parameter] [filesName/fileName] 显示目录下的所有文件和文件夹
     *          可选参数：
     *              -a ：显示当前目录所有的文件和目录，包括隐藏的 (文件名以'.'开头就是隐藏)。
     *              -l ：以长列表的方式显示信息
     *              -h : 显示文件大小时，以 k , m, G 单位显示
     *          说明：ll并不是Linux的基本命令，是ls的别名，有的Linux不支持这种命令
     *
     *      > cd指令，进入指定的目录
     *          cd directoryFiles 进入指定的目录，只能进入目录，不能进入文件
     *          相对路径：从当前位置开始定位
     *          绝对路径：必须从根目录下开始定位
     *          cd ..   返回上一级目录
     *          cd ~    返回家目录
     *          cd /    返回根目录
     *
     *      > mkdir指令，创建文件夹
     *          mkdir [parameter] directoryFilesName 创建指定名称文件夹
     *          说明：只能创建文件夹，而且默认只能创建一级目录
     *          可选参数：
     *              -p ：一次性创建多级目录
     *
     *      > rmdir directoryFilesName 删除指定的名称的目录
     *          说明：只能删除一级空目录
     *
     *      > touch directoryFileName 创建指定名称的空文件，当文件存在时，那么刷新该文件的修改时间
     *
     *      > cp指令，复制文件或文件夹，不添加参数的情况下，只能复制文件
     *          语法格式：
     *              cp [parameter] sourceDirectoryFiles aimDirectoryFiles  将sourceDirectoryFiles复制到aimDirectoryFiles目录下
     *          说明：在cp前添加'/'符号，强制覆盖已存在的文件，并且不提示
     *          可选参数：
     *              -r 用于文件目录的递归处理，将指定目录下的文件与子目录一并处理
     *
     *      > rm指令，删除指定的文件，或文件夹
     *          rm [parameter] directoryFileName 删除文件或文件夹directoryFileName
     *          说明：不添加参数只能删除文件，参数-r能迭代删除文件夹
     *          可选参数：
     *              -r ：递归删除整个文件夹及其内部文件或文件夹
     *              -f ：强制删除不提示
     *
     *      > mv指令，移动文件或文件夹，相当于剪切
     *          语法格式：
     *              mv [parameter] sourceDirectoryFilesName aimDirectoryFilesName
     *                  将sourceDirectoryFilesName文件或文件夹移动到aimDirectoryFilesName文件夹下
     *              说明：目标文件夹必须存在，否则目录的绝对路径最后的一项会变成文件名
     *              可选参数：
     *                  -i 若指定目录已有同名文件，则先询问是否覆盖旧文件
     *                  -f 在mv操作要覆盖某已有的目标文件时不给任何指示;
     *
     *      > cat [parameter] directoryFileName : 查看文件内容
     *          说明：cat只能查看文件，而不能修改文件，为了浏览方便，一般带上管道命令 | more
     *          可选参数：
     *              -n ：显式行号
     *
     *      > more directoryFileName : 查看文件内容
     *          说明：more 指令是一个基于 VI 编辑器的文本过滤器，它以全屏幕的方式按页显示文本文件的内容。
     *              more 指令中内置了一些快捷键，用于查看文件内容
     *              快捷键         功能
     *              [space]         向下翻一页
     *              [Enter]         向下翻一行
     *              [q]             立刻离开more，结束查看
     *              [ctrl + f]      向下翻一屏
     *              [ctrl + b]      向上翻一屏
     *              [=]             输出当前行号
     *              [:f]            输出当前文件名和行号
     *
     *      > less directoryFileName : 查看文件内容
     *          说明：less指令用来分屏查看文件内容，它的功能与more指令类似，但是比more指令更加强大，支持各种显示终端。
     *              less指令在显示文件内容时，并不是一次将整个文件加载之后才显示，而是根据显示需要加载内容，对于显示大型文件具有较高的效率。
     *              快捷键         功能
     *              [space]         向下翻一页
     *              [pagedown]      向下翻一页
     *              [pageup]        向下翻一页
     *              [/字符串]        向下查找字符串。n：向下查找，N：向上查找
     *              [?字符串]        向上查找字符串。n：向上查找，N：向下查找
     *              [q]             离开less，结束查看
     *
     *      > echo [parameter] text : 将text所代表的内容输出到控制台
     *          可选参数：
     *              -e：支持反斜线控制的字符转换
     *              -n：取消输出后行末的换行符号
     *
     *      > head [parameter] directoryFileName : 用于查看文件的开头部分的内容
     *          可选参数：
     *              -q 隐藏文件名
     *              -v 显示文件名
     *              -c<数目> 显示的字节数，可设置
     *              -n<行数> 显示的行数，默认显示10行，可进行设置
     *
     *      > tail [parameter] directoryFileName : 用于查看文件的内容
     *          可选参数：
     *              -f 循环读取，（实时读取）
     *              -q 不显示处理信息
     *              -v 显示详细的处理信息
     *              -c<数目> 显示的字节数
     *              -n<行数> 默认显示文件的尾部10行内容，可以进行设置
     *
     *      > > : 输出重定向；>> : 追加
     *          语法格式：
     *              1) ls -l >文件		 （功能描述：列表的内容写入文件中（覆盖写））
     *              2) ls -al >>文件	 （功能描述：列表的内容追加到文件的末尾）
     *              3) cat 文件1 > 文件2	 （功能描述：将文件1的内容覆盖到文件2）
     *              3) cat 文件1 >> 文件2	 （功能描述：将文件1的内容追加到文件2）
     *              4) echo "内容"> 文件    （功能描述：将输出的内容覆盖到文件中）
     *              4) echo "内容">> 文件   （功能描述：将输出的内容追加到文件中）
     *
     *      > ln ：软连接指令，了解即可
     *
     *      > history [parameter] : 用于显示历史记录和执行过的指令命令
     *          说明：history命令的作用：读取历史命令文件中的目录到历史命令缓冲区，和将历史命令缓冲区中的目录写入命令文件
     *              该命令单独使用时，仅显示历史命令，在命令行中，可以使用符号!执行指定序号的历史命令。例如，要执行第2个历史命令，则输入!2。
     *              history命令：是被保存在内存中的，当退出或者登录shell时，会自动保存或读取。
     *              在内存中，历史命令仅能够存储1000条历史命令，该数量是由环境变量 HISTSIZE进行控制。
     *              默认是不显示命令的执行时间，命令的执行时间，history 已经记录，只是没有显示。
     *          可选参数：
     *              -N: 显示历史记录中最近的N个记录；
     *              -c：清空当前历史命令；
     *              -r：将历史命令文件中的命令读入当前历史命令缓冲区；
     *              -w：将当前历史命令缓冲区命令写入历史命令文件中;
     *              -a：将历史命令缓冲区中命令写入历史命令文件中；
     *              -d<offset>：删除历史记录中第offset个命令
     *              -n<filename>：读取指定文件
     *
     *  十、其他命令
     *      1、时间类指令：
     *          ① date指令：日期类指令
     *              基本语法
     *              > date 显示当前时间
     *              > date "+%Y" 显示当前年份
     *              > date "+%m" 显示当前月份
     *              > date "+%d" 显示当前天数
     *              Y、m、d、H、M、S可以随意组合
     *              > date "+%Y-%m-%d %H:%M:%S" 显示当前年月日 时分秒
     *              > date "+%F"  显示年月日
     *              > date "+%T"  显示时分秒
     *
     *              使用date指令修改系统时间，基本语法：date -s 字符串时间
     *
     *          ② cal指令：日历类指令
     *              显示日历，基本语法：cal [parameter]
     *              不添加参数时，默认显示当月的日历
     *
     *      2、搜索查找类命令
     *          ① find指令，从指定目录向下递归地遍历其各个子目录，将满足条件的文件或者目录显示在终端
     *              基本语法：find filesName1 [parameter] fileName/filesName2
     *              说明：file(s)Name2是搜索的目标文件或文件夹的名字；filesName1是指在该文件夹下进行搜索；参数表示搜索的方式
     *              可选参数：
     *                  > -name 按指定文件的名称进行搜索，可以使用通配符'*'、'?'进行模糊查询
     *                  > -user 按文件所属的用户进行查找，查找属于当前用户的指定文件
     *                  > -size 按文件大小进行查找，（+#表示大于#，-#表示小于#，#表示等于#）
     *                  例如：查找等于大小等于20k的文件：find -size +20k
     *
     *          ② locate指令，可以快速定位文件路径。
     *              基本语法：
     *                  > updatedb  更新locate数据库
     *                  > locate fileName/filesName 搜索指定的文件或文件夹，将其绝对路径输出在控制台
     *              说明：locate 指令利用事先建立的系统中所有文件名称及路径的locate数据库实现快速定位给定的文件。
     *                  因此在利用lovate指令进行搜索前，需要更新locate数据库。
     *                  locate指令特点：无需搜索整个文件系统，查询速度快
     *
     *
     *          ③ grep指令，一种强大的文本搜索工具，它能使用正则表达式搜索文本，并把匹配的行打印出来
     *              基本语法：grep [parameter] aimString fileName
     *              说明：在fileName文件中搜索aimString这个文本
     *              可选参数：
     *                  -n 显示匹配行和行号
     *                  -i 忽略字母大小写进行搜索
     *              | 管道符号：将符号前的命令的执行结果作为符号后的命令的执行对象
     *
     *      3、压缩和解压缩命令
     *          ① gzip指令，将单个文件进行压缩，生成.gz文件，并删除源文件
     *              基本语法：gizp fileName  将fileName压缩成fileName.gz文件，并删除fileName
     *            gunzip指令，将.gz文件解压缩
     *              基本语法：gunzip fileName.gz 将fileName.gz文件解压成fileName文件，并 删除fileName.gz
     *
     *          ② zip指令，将单个文件或多个文件进行压缩，生成.zip文件
     *              基本语法：zip [parameter] name.zip filesName  将filesName文件夹或者多个文件压缩成名字为name的.zip文件
     *              可选参数：
     *                  -r 递归压缩目录，及将制定目录下的所有文件以及子目录全部压缩
     *                  -m 将文件压缩之后，删除原始文件，相当于把文件移到压缩文件中
     *                  -v 显示详细的压缩过程信息
     *                  -p 在压缩的时候不显示命令的执行过程
     *                  -# #表示压缩级别，压缩级别取值1~9，1代表压缩速度更快，9代表压缩效果更好
     *                  -u 更新压缩文件，即往压缩文件中添加新文件
     *
     *            unzip指令，将.zip文件解压
     *              基本语法：unzip [parameter] name.zip 将name.zip解压
     *              可选参数：
     *                  -d directoryFiles    将解压的结果放在directoryFiles路径下
     *
     *          ③ tar指令，打包指令，可压缩，可解压，压缩和解压对象为.tar.gz文件
     *              说明：打包和压缩，打包是指将一大堆文件或目录变成一个总的文件；压缩则是将一个大的文件通过一些压缩算法变成一个小文件。
     *                    为什么要区分这两个概念？
     *                    这源于 Linux 中很多压缩程序只能针对一个文件进行压缩，这样当你想要压缩一大堆文件时，你得先将这一大堆文件先打成一个包（tar命令），
     *                    然后再用压缩程序进行压缩（gzip bzip2命令）
     *              基本语法：tar [parameter] name.tar.gz filesName/fileName 将指定的单个文件或多个文件或单个文件夹或多个文件夹压缩成name.tar.gz
     *              可选参数：
     *                  -c  create 建立新的备份文件  创建压缩文件
     *                  -v 显示详细信息
     *                  -f 指定压缩后的文件名
     *                  -z 通过gzip指令处理备份文件
     *                  -x 从备份文件中还原文件   解压文件
     *                  -C 指定解压文件的存放位置
     *              示例：
     *              > 将 /home/a1.txt 和 /home/a2.txt 压缩成 a.tar.gz： tar -zcvf a.tar.gz /home/a1.txt /home/a2.txt
     *              > 将/home 的文件夹压缩成 myhome.tar.gz  tar -zcvf myhome.tar.gz /home/
     *              > 将 a.tar.gz 解压到当前目录   tar -zxvf a.tar.gz
     *              > 将 myhome.tar.gz 解压到 /opt/tmp2 目录下 tar -zxvf myhome.tar.gz -C /opt/tmp2
     *
     * 十一、用户管理、用户组管理和权限管理
     *      Linux系统是一个多用户多任务的分时操作系统，任何一个要使用系统资源的用户，都必须首先向系统管理员申请一个账号，然后以这个账号的身份进入系统。
     *      用户的账号一方面可以帮助系统管理员对使用系统的用户进行跟踪，并控制他们对系统资源的访问；另一方面也可以帮助用户组织文件，并为用户提供安全性保护。
     *
     *      用户管理
     *      1、添加用户：
     *          语法格式：useradd [parameter] userName   创建userName用户
     *          可选参数：
     *              -c comment     指定一段注释性描述。
     *              -d 目录        指定用户家目录，如果此目录不存在，则同时使用-m选项，可以创建主目录。
     *              -g 用户组      指定用户所属的用户组。
     *              -G 用户组      指定用户所属的附加组。
     *              -s Shell文件   指定用户的登录Shell。
     *
     *      2、删除用户：
     *          语法格式：userdel [-r] userName
     *          -r 表示连同用户的家目录一起删除
     *
     *      3、修改已有用户的信息：
     *          语法格式：usermod [parameter] userName
     *          参数含义同上
     *          常用的两个参数：-g：修改用户的所在组；-d:修改用户的家目录
     *          例如：usermod -g groupName userName
     *               usermod -d homeDirectory
     *
     *      4、用户口令管理：设置口令和修改口令都是同一命令
     *          语法格式：passwd [parameter] userName
     *          可选参数：略
     *
     *      用户组管理：
     *      每个用户都有一个用户组，系统可以对一个用户组中的所有用户进行集中管理。
     *      1、新增一个新的用户组：
     *          语法格式：groupadd [parameter] groupName
     *          可选参数:
     *              -g UID 创建用户组时，同时指定用户组标识号（用户组ID）
     *
     *      2、删除用户组：
     *          语法格式：groupdel groupName
     *
     *      3、修改已有用户组信息
     *          语法格式：groupmod [parameter] userName
     *          可选参数含义同上
     *
     *      文件管理
     *      在linux中的每个用户必须属于一个组，不能独立于组外。
     *      在linux中每个文件都有所有者、所在组等概念。
     *
     *      一般文件的所有者是文件的创建者，所在组为所有者所在的组。
     *      通过Linux指令可以查看文件的所有者，也可以修改文件的所有者；可以查看文件的所在组，也可以修改文件的所在组
     *      1、查看文件的所有者及所在组：需要进入文件所在的目录
     *          语法格式：ls -l
     *          即查看文件的详细信息即可
     *
     *      2、修改文件的所有者：
     *          语法格式：chown userName directoryFileName
     *
     *      3、修改文件的所在组：
     *          语法格式：chgrp groupName directoryFileName
     *
     *      4、修改文件权限：
     *          chmod指令，详情见下述
     *
     *      ============================================================================================================
     *      权限管理
     *      ls -l指令输出结果详解
     *      ls -l 输出结果：
     *      文件对应结果：-rw-------. 1 root root 1397 2月  11 16:43 anaconda-ks.cfg
     *      目录对应结果：drwxrwxr-x. 2 tom tom 4096 2月  11 20:43 java
     *
     *      结果详解：
     *      第一部分：前10个字符
     *          第1个字符：表示文件类型，需要说明的是，Linux系统中一切皆文件，目录也是一种文件
     *              -   普通文件
     *              d   目录
     *              l   连接文件
     *              c   字符设备文件，即串行端口的接口设备，例如键盘、鼠标等等
     *              d   块设备文件，即存储数据以供系统存取的接口设备，简单而言就是硬盘
     *
     *          第2~4个字符：表示文件·所有者·对该文件所拥有的权限
     *          第5~7个字符：表示文件·所在组·对该文件所拥有的权限，即所属组的其他用户对该文件的权限
     *          第8~10个字符：表示文件·其他组·对该文件所拥有的权限，即文件其他组的用户对该文件的权限
     *
     *          权限说明：
     *              权限有三种：r、w、x，分别表示可读、可写、可执行。r、w、x对作用到文件和目录，会有不同的效果
     *              作用于文件：r 代表用户对该文件可读取，可查看；
     *                         w 表示用户可以对该文件内容进行修改，但不表示可以删除该文件。删除一个文件的前提是，用户必须具有对该文件所在目录具有写的权限。
     *                         x 表示文件本身是可执行文件
     *
     *              作用于目录：r 表示用户可以读取该目录，可以通过ls指令查看目录内容
     *                         w 表示该目录可写，即用户可以在该目录内通过mkdir、rm、mv等命令在目录内创建、删除、移动文件等
     *                         x 表示用户可以进入该目录
     *
     *               权限可以用数字表示：r=4,w=2,x=1
     *               rwx=7，rw-=6,r-w=5，-wx=3，r--=4，-w-=2，--x=1，---=0
     *      ls -l 输出结果：
     *            文件对应结果：-rw-------. 1 root root 1397 2月  11 16:43 anaconda-ks.cfg
     *            目录对应结果：drwxrwxr-x. 2 tom tom 4096 2月  11 20:43 java
     *      第二部分：文件信息
     *          1或2 对于文件而言，表示该文件的硬链接数；对于目录而言，表示该目录下有多少个子目录，注意不包含文件的
     *          root root 第一个root表示该文件的所有者，第2个root表示该文件的所在组
     *          1397或4096 对于文件而言，表示该文件的大小；对于目录而言，一般是数值4096（4K），表示目录的大小
     *          2月11 16：43 表示文件或目录的创建时间
     *
     *      文件权限修改
     *      第一种方式：利用+、-、=修改文件权限
     *          说明：u表示文件所有者，g表示文件所在组的其他用户，o表示其他组的用户，a表示所有用户
     *               + 表示授予权限，- 表示收回权限，= 表示赋予用户权限
     *          举例：
     *              chmod   u=rwx,g=rx,o=x   file/files 表示赋予file/files所有用户rwx，所在组用户rx，其他组用户x权限
     *              chmod   o+w    file/files 表示赋予其他组用户w的权限
     *              chmod   a-x    file/files 表示收回所有用户x权限
     *
     *      第二种方式：通过数字变更权限
     *          chmod   751  file/files 表示赋予file/files所有用户rwx，所在组用户rx，其他组用户x权限
     *
     *      补充：修改目录及目录下所有子文件、子目录的所有者、所在组
     *      语法格式：
     *          所有者
     *          chown [-R] userName file/files
     *          参数-R表示将文件或目录下所有文件的所有者改变
     *
     *          所在组
     *          chgrp [-R] grpName file/files
     *          参数-R表示将文件或目录下所有文件的所在组改变
     *
     *  十二、定时任务调度
     *      任务调度，是指系统在指定时间执行特定的命令或程序
     *      任务调度分类：
     *          系统任务调度
     *          用户任务调度
     *
     *      使用场景：系统周期性所要执行的工作，比如写缓存数据到硬盘、日志清理等。
     *
     *      在/etc目录下有一个crontab文件，这个就是系统任务调度的配置文件。
     *      所有用户定义的crontab文件都被保存在/var/spool/cron目录中。其文件名与用户名一致，使用者权限文件如下：
     *          /var/spool/cron/  所有用户crontab文件存放的目录,以用户名命名
     *          /etc/cron.deny  该文件中所列用户不允许使用crontab命令
     *          /etc/cron.allow  该文件中所列用户允许使用crontab命令
     *
     *      crontab命令语法格式：
     *          crontab [parameter]
     *      可选参数：
     *          -e 创建任务调度文件
     *          -l 查询所有的任务调度文件
     *          -r 删除所有的任务调度文件
     *
     *      创建定时任务调度的步骤：
     *          ① 创建任务调度文件：crontab -e
     *          ② 编写任务调度文件，即编写任务调度脚本，
     *          ③ 执行定时任务调度
     *
     *      任务调度文件（crontab文件）的含义：用户所建立的crontab文件中，每一行都代表一项任务，每行的每个字段代表一项设置，它的格式共分为六个字段
     *      前五段是时间设定段，第六段是要执行的命令段，格式如下：
     *          minute hour day month week command（分 时 日 月 周 命令）
     *          > minute： 表示一小时当中的第几分钟，可以是从0到59之间的任何整数。
     *          > hour：表示一天当中的第几小时，可以是从0到23之间的任何整数。
     *          > day：表示一个月当中的第几天，可以是从1到31之间的任何整数。
     *          > month：表示一年当中的第几月，可以是从1到12之间的任何整数。
     *          > week：表示一周当中的星期几，可以是从0到7之间的任何整数，这里的0或7代表星期日。
     *          > command：要执行的命令，可以是系统命令，也可以是自己编写的脚本文件。
     *          说明：1、如果只是简单的任务，可以不用写脚本，直接在crontab中加入任务命令即可。2、对于复杂的任务，需要写脚本shell编程
     *
     *          在以上各个字段中，还可以使用特殊字符：
     *          > * 代表所有可能的值，如month字段是*，则表示在满足其他条件后，每个月都执行该操作
     *          > , 代表不连续的取值，如“0 8,12,16 * * * 命令”，就代表在每天的8点0分，12点0分，16点0分都执行一次命令
     *          > - 代表连续的时间范围。比如“0 5  *  *  1-6命令”，代表在周一到周六的凌晨5点0分执行命令
     *          > * /# 表示执行频率，比如“* /10  *  *  *  *  命令”，代表每隔10分钟就执行一遍命令
     *
     *      crond服务：
     *          /sbin/service crond start //启动服务
     *          /sbin/service crond stop //关闭服务
     *          /sbin/service crond restart //重启服务
     *          /sbin/service crond reload //重新载入配置
     *          查看crontab服务状态：service crond status
     *          查看开机启动服务 ：ntsysv
     *
     *  十三、磁盘管理
     *      1、磁盘基础知识
     *          分区方式：
     *          ① mbr分区：
     *              > 最多支持四个主分区
     *              > 系统只能安装在主分区
     *              > 扩展分区要占一个主分区
     *              > MBR最大只支持2TB，但拥有最好的兼容性
     *
     *          ② gtp分区：
     *              > 支持无限多个主分区（但操作系统可能限制，比如 windows下最多128个分区）
     *              > 最大支持18EB的大容量（1EB=1024 PB，1PB=1024 TB ）
     *              > windows7 64位以后支持gtp
     *
     *      2、磁盘分区：
     *          Linux来说无论有几个分区，分给哪一目录使用，它归根结底就只有一个根目录，一个独立且唯一的文件结构 ,
     *          Linux中每个分区都是用来组成整个文件系统的一部分
     *
     *          Linux采用了一种叫“载入”的处理方法，它的整个文件系统中包含了一整套的文件和目录，且将一个分区和一个目录联系起来。
     *          这时要载入的一个分区将使它的存储空间在一个目录下获得
     *
     *          硬盘说明：
     *              > Linux硬盘分IDE硬盘和SCSI硬盘，目前基本上是SCSI硬盘
     *              > 对于IDE硬盘，驱动器标识符为“hdx~”,其中“hd”表明分区所在设备的类型，这里是指IDE硬盘了。
     *                  “x”为盘号（a为基本盘，b为基本从属盘，c为辅助主盘，d为辅助从属盘）,
     *                  “~”代表分区，前四个分区用数字1到4表示，它们是主分区或扩展分区，从5开始就是逻辑分区。
     *                  例，hda3表示为第一个IDE硬盘上的第三个主分区或扩展分区,hdb2表示为第二个IDE硬盘上的第二个主分区或扩展分区。
     *              > 对于SCSI硬盘则标识为“sdx~”，SCSI硬盘是用“sd”来表示分区所在设备的类型的，其余则和IDE硬盘的表示方法一样。
     *                  sdb1 [表示第2块scsi 硬盘的第1个分区]
     *
     *          查看系统设备挂载情况
     *              命令：lsblk 或 lsblk -f
     *
     *          添加一块硬盘的步骤：
     *              > 虚拟机添加硬盘
     *              > 分区
     *              > 格式化
     *              > 挂载
     *              > 设置可以自动挂载。
     *          实操：略
     *
     *      3、磁盘管理：
     *          > 查询系统磁盘使用情况：df -h
     *
     *
     *  十四、网络配置
     *      将Linux系统的IP地址设置为静态IP地址
     *
     *      Linux的系统配置文件为：/etc/sysconfig
     *      其网络配置相关文件为：/etc/sysconfig/network-scripts/ifcfg-ens33，其文件内容为：
     *          > TYPE="Ethernet"
     *          > PROXY_METHOD="none"
     *          > BROWSER_ONLY="no"
     *          > [BOOTPROTO="static"]
     *          > DEFROUTE="yes"
     *          > IPV4_FAILURE_FATAL="no"
     *          > IPV6INIT="yes"
     *          > IPV6_AUTOCONF="yes"
     *          > IPV6_DEFROUTE="yes"
     *          > IPV6_FAILURE_FATAL="no"
     *          > IPV6_ADDR_GEN_MODE="stable-privacy"
     *          > NAME="ens33"
     *          > UUID="94cb2116-10a0-49d8-aa47-50fd168d0104"
     *          > DEVICE="ens33"
     *          > ONBOOT="yes"
     *          > [IPADDR=192.168.200.10]
     *          > [GATEWAY=192.168.200.2]
     *          > [DNS1=192.168.200.2]
     *
     *      为了配置静态获取IP地址，需要将BOOTPROTP设置为"static"
     *                                  IPADDR设置为需要的IP地址
     *                                  并设置相应的网关GATEWAY和域名解析服务器DNS1
     *
     *  十五、主机名
     *      1、查看主机名：hostname
     *
     *      2、修改主机名：
     *          修改主机映射文件：/etc/hostname
     *          注意，/etc/hostname只识别第1行文本，作为主机名
     *
     *      3、修改/etc/hosts文件，增加ip和主机的映射
     *          格式：ip地址  主机名
     *
     *  十六、进程管理
     *      1、基本介绍
     *          > 在LINUX中，每个执行的程序（代码）都称为一个进程。每一个进程都分配一个ID号。
     *          > 每一个进程，都会对应一个父进程，而这个父进程可以复制多个子进程。例如www服务器。
     *          > 每个进程都可能以两种方式存在的。前台与后台，所谓前台进程就是用户目前的屏幕上可以进行操作的。
     *              后台进程则是实际在操作，但由于屏幕上无法看到的进程，通常使用后台方式执行[sshd , crond]。
     *          > 一般系统的服务都是以后台进程的方式存在，而且都会常驻在系统中。直到关机才才结束
     *
     *      2、查看系统正在运行的进程
     *          ps [parameter]
     *          可选参数：
     *              -a 显示同一终端下的所有程序
     *              -A 显示所有进程
     *              -u  指定用户的所有进程
     *              -au 显示较详细的资讯
     *              -aux 显示所有包含其他使用者的行程
     *              -f  显示程序间的关系，即显示父进程
     *
     *      3、终止进程
     *          kill [parameter] PID 根据进程号终止进程
     *          可选参数：
     *              -9 表示强制终止
     *          killall PNAME 根据进程名终止进程
     *              该命令支持通配符
     *
     *      4、查看进程树
     *          pstree [parameter]
     *          可选参数：
     *              -p 显示进程号
     *              -u 显示进程所属用户
     *
     *      5、服务管理
     *          服务(service) 本质就是进程，但是是运行在后台的，通常都会监听某个端口，等待其它程序的请求，比如(mysql , sshd  防火墙等)，
     *          因此我们又称为守护进程，是Linux中非常重要的知识点。
     *          1、查看服务
     *              > setup
     *              > /etc/init.d/  目录下全是服务
     *
     *          2、服务管理指令
     *          centOS 6  service 服务名 [start | stop | restart | reload | status]
     *          centOS 7  systemctl [start | stop | restart | reload | status] 服务名
     *
     *          说明：
     *              > 关闭或启动服务后，服务会立即生效，但重启虚拟机后会失效
     *              > 如果希望设置某个服务开机自启关闭，那么需要使用chkconfig指令
     *
     *          3、更改服务开机自启动状态
     *              服务运行级别，同系统运行级别
     *              语法格式：以防火墙为例
     *                  systemctl list-unit-files   查看所有服务开机自启状态
     *                  systemctl list-unit-files | grep 服务名   查看指定服务的开机启动状态
     *                  systemctl  list-unit-files | grep enable 查看开机自启的服务有哪些
     *                  systemctl enable firewalld  设置防火墙服务开机自启
     *                  systemctl disable firewalld  设置防火墙服务开机不自启
     *                  firewall 是防火墙的名字,firewalld 是防火墙的服务进程, firewall-cmd 是给用户使用的接口命令
     *
     *  十七、RPM和YUM
     *      1、RMP包的管理
     *          一种用于互联网下载包的打包及安装工具，它包含在某些Linux分发版中。它生成具有.RPM扩展名的文件。
     *          RPM是RedHat Package Manager（RedHat软件包管理工具）的缩写，类似windows的setup.exe
     *
     *          查询已安装的rpm列表：
     *              语法格式：rpm  –q -a | grep xx
     *              -q 表示查询
     *              -a 表示全部
     *
     *          其他查询语句：
     *              > rpm -q 软件包名 :查询软件包是否安装
     *              > rpm -qi 软件包名 ：查询软件包信息
     *              > rpm -ql 软件包名 :查询软件包中的文件
     *              > rpm -qf 文件全路径名 查询文件所属的软件包
     *
     *          卸载rmp包：
     *              语法格式：
     *                  rpm  -e  RPM包的名称
     *              说明：如果其它软件包依赖于您要卸载的软件包，卸载时则会产生错误信息
     *
     *          安装rmp包：
     *              语法格式：
     *                  rpm -ivh  RPM包全路径名称
     *              参数：
     *                  -i  install 安装
     *                  -v  verbose 提示
     *                  -h  hash 进度条
     *
     *          说明：Linux系统中的所有rmp安装包都在镜像文件中，因此使用rmp安装包时，先需要挂载，很麻烦，很少用
     *
     *      2、yum是一个Shell 软件包管理器。基于RPM包管理，能够从指定的服务器自动下载RPM包并且安装，可以自动处理依赖性关系，并
     *          且一次安装所有依赖的软件包。
     *
     *          相关命令：
     *              查询yum服务器是否有需要安装的软件：
     *                  语法格式：yum list | grep xx软件列表
     *
     *              安装指定的yum包：
     *                  语法格式：yum install xxx  下载安装
     *
     *
     *  补充：
     *  1、source命令：是bash的内部命令，作用是使Shell读入指定的文件，并逐一执行文件中的所有命令
     *      说明：source命令通常用于重新执行刚修改的初始化文件，使之立即生效，而不必注销并重新登录
     *
     *  2、scp命令：
     *      简介：scp是secure copy的简写，用于在Linux下进行远程拷贝文件的命令，和它类似的命令有cp，
     *      不过cp只是在本机进行拷贝不能跨服务器，而且scp传输是加密的。可能会稍微影响一下速度。当
     *      你服务器硬盘变为只读 read only system时，用scp可以帮你把文件移出来。另外，scp还非常不占资源，
     *      不会提高多少系统负荷，在这一点上，rsync就远远不及它了。虽然 rsync比scp会快一点，但当小文件众多的情况下，
     *      rsync会导致硬盘I/O非常高，而scp基本不影响系统正常使用。
     *
     *      语法格式：scp [parameter]  源文件路径  目标文件全路径
     *      常用可选参数：-r 递归传递整个目录
     *      说明：
     *          源文件路径：可以采用绝对路径，可以采用相对路径
     *          目标文件路径：格式：目标服务器的用户名@目标服务器IP地址:/目标文件路径
     *      传输成功的前提是：目标服务器用户必须对目标文件路径上的每一个目录都有读的权限
     *      如果目标文件路径中的目录不存在，那么目标服务器用户还必须具有写的权限
     *
     *  3、rsync命令：
     *      rsync (remote sync) 远程同步
     *      rsync是远程同步工具，主要用于备份和镜像。具有速度快、避免复制相同内容和支持符号链接的优点。
     *
     *      rsync和scp的区别：用 rsync 做文件的复制要比 scp 的速度快， rsync只对差异文件做更新，scp是把所有文件都复制过去。
     *
     *      基本语法：rsync [parameter] 源文件路径  目标文件全路径
     *      可选参数：-a 归档模式，表示以递归方式传输文件，并保持所有文件属性
     *               -v 显示传输过程
     *
     *  4、xsync命令：
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */
}
