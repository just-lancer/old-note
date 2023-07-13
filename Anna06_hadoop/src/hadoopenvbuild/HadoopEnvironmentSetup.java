package hadoopenvbuild;

public class HadoopEnvironmentSetup {
    /**
     *  Hadoop环境搭建步骤
     *  阶段一：
     *      > 新建CentOS 7系统虚拟机，作为模板机
     *      > 固定虚拟机IP地址
     *          修改/etc/sysconfig/network-scripts/ifcfg-ens33文件：
     *          将DHCP字段的值改为static
     *          添加字段和值：值，视情况而定
     *              IPADDR=192.168.200.131
     *              GATEWAY=192.168.200.2
     *              DNS1=192.168.200.2
     *      > 关闭防火墙，并关闭防火墙开机自启
     *          查看防火墙状态：systemctl stasus firewalld.service
     *          停止防火墙服务：systemctl stop firewalld.service
     *          查看防火墙开机自启状态：systemctl list-unit-files | grep firewall
     *          修改防火墙开机自启状态：systemctl diable firewalld
     *          注意：firewall 是防火墙的名字,firewalld 是防火墙的服务进程, firewall-cmd 是给用户使用的接口命令
     *      > 创建atguigu用户，并赋予root权限
     *          赋予权限：修改/etc/sudoers文件，在其中添加一行atguigu  ALL=(ALL) NOPASSWD:ALL
     *      > 在/opt目录下创建：module和software目录
     *          module目录用于存放软件安装包
     *          software目录为软件的安装地址
     *      > 修改主机名，并设置IP地址和主机名映射
     *          主机名：修改/etc/hostname文件
     *          主机IP地址映射：修改/etc/hosts文件
     *
     *  阶段二：克隆并设置虚拟机
     *      > 克隆三台虚拟机
     *
     *  阶段三：JDK和Hadoop软件安装
     *      > 将Linux版JDK安装包和Hadoop安装包传入到Linux系统/opt/software目录下
     *          利用xtpf5
     *      > 解压安装包到/opt/module下
     *          命令：tar -zxvf /opt/software/jdk-8u212-linux-x64.tar.gz -C /opt/module/
     *               tar -zxvf /opt/software/jhadoop-3.1.3.tar.gz -C /opt/module/
     *      > 配置JDK和Hadoop环境变量
     *          > 配置JDK环境变量：
     *              在指定位置创建脚本文件：
     *                  touch /etc/profile.d/env_var.sh
     *              输入指定语句：
     *                  #JAVA_HOME
     *                  export JAVA_HOME=/opt/module/"jdk1.8.0-212"
     *                  export PATH=$PATH:$JAVA_HOME
     *              保存后，运行/etc/profile文件，运行设置的环境变量
     *                  source /etc/profile
     *              测试是否配置成功：
     *                  java -version
     *
     *          > 配置Hadoop环境变量：不同于JDK环境变量，Hadoop需要添加两个环境变量，对应文件bin和sbin
     *              一般将Hadoop和JDK的环境变量的配置代码放在一个脚本中
     *              > 创建配置文件
     *              > 编写脚本文件：在/etc/profile.d/env_var.sh中追加：
     *                  #HADOOP_HOME
     *                  export HADOOP_HOME=/opt/module/hadoop-3.1.3
     *                  export PATH=$PATH:$HADOOP_HOME/bin
     *                  export PATH=$PATH:$HADOOP_HOME/sbin
     *              > 添加环境变量，运行source命令：
     *                  source /etc/profile
     *              > 测试环境变量：
     *                  hadoop version
     *
     *  阶段四：编写集群节点分发脚本
     *      > scp命令：可以发送数据、可以拉取数据、可以站在第三方，控制数据发送和拉取
     *      > rsync命令：远程同步，只同步差异化的数据
     *      > 集群节点文件自动分发脚本实际是对rsync的包装，代码如下：
     *      #!/bin/bash
     *      #1. 判断参数个数
     *      if [ $# -lt 1 ]
     *          then
     *              echo Not Enough Arguement!
     *              exit;
     *      fi
     *      #2. 遍历集群所有机器
     *      for host in hadoop102 hadoop103 hadoop104
     *          do
     *              echo ==================== $host ====================
     *      #3. 遍历所有目录，挨个发送
     *      for file in $@
     *          do
     *          #4. 判断文件是否存在
     *              if [ -e $file ]
     *                  then
     *                  #5. 获取父目录
     *                  pdir=$(cd -P $(dirname $file); pwd)
     *                  #6. 获取当前文件的名称
     *                  fname=$(basename $file)
     *                  ssh $host "mkdir -p $pdir"
     *                  rsync -av $pdir/$fname $host:$pdir
     *              else
     *                  echo $file does not exists!
     *              fi
     *          done
     *      done
     *
     *  第五阶段：配置SSH进行免密通信
     *      > ssh免密通讯原理是：利用非堆成加密密钥进行
     *      配置步骤：
     *          > 生成密钥：在~/.shh目录下执行命令：ssh-keygen -t rsa
     *              生成过程中输入三次回车，生成两个文件，分别是私钥和公钥
     *          > 将公钥拷贝到需要免密访问的服务器上：ssh-copy-id 服务器IP地址
     *
     *      说明：用户家目录下有隐藏文件.ssh记录了密钥相关信息
     *          > known_hosts   记录 ssh 访问过计算机的公钥(public key)
     *          > id_rsa    生成的私钥
     *          > id_rsa.pub 生成的公钥
     *          > authorized_keys 存放授权过的无密登录服务器公钥
     */
}
