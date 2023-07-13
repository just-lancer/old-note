package mysql;

public class chap01_mysql_overview {
    /**
     *  第一章 数据库概述
     *
     *  一、数据库相关概念
     *      > 数据库（Database）:即存储数据的“仓库”，其本质是一个文件系统。它保存了一系列有组织的数据
     *      > 数据库管理系统（Database Management System, DBMS）：是一种操纵和管理数据库的大型软件，用于建立、使用和维护数据库，对数据库进行统一管理和控
     *              制。用户通过数据库管理系统访问数据库中表内的数据
     *      > 结构化查询语言（Strutured Query Language）：专门用来与数据库通信的语言
     *
     *  二、MySQL环境搭建
     *      1、MySQL的卸载
     *          > 停止MySQL服务
     *          > 软件卸载：控制面板卸载；360，百度管家等安全软件卸载；安装包卸载
     *          > 卸载完成后，清理残余文件。安装目录和数据目录
     *          > 注册表清理：选做。如果前面三个步骤做了，再次安装数据库的时候出现错误，那么需要做这一步
     *          > 删除环境变量配置
     *
     *      2、MySQL安装及配置，环境变量的配置
     *
     *      3、MySQL服务的启动与停止
     *          方法一：图形化界面
     *          方法二：cmd命令行输入命令： net start MySQL80   net stop MySQL80
     *
     *      4、MySQL服务器登录
     *          方法一：cmd命令行登录：mysql -h 主机名 -P 端口号 -u 用户名 -p密码
     *          说明：-h 主机名 -P 端口号 -u 用户名，空格都可以省略；-p密码，空格不能省略
     *                客户端和服务器在同一台机器上时，主机名可以输入localhost或者本机回环地址127.0.0.1
     *                -hlocalhost可以省略，如果端口号使用默认的，P3306可以省略
     *
     *      5、MySQL常见库表命令：首先需要连接上服务器
     *          ① 查看当前MySQL版本：
     *              命令行输入：c:\> mysql -V   或     c:\> mysql --version
     *              或者  连接服务器之后：select version();
     *
     *          ② 退出服务器：exit    或   quit
     *
     *          ③ 查看所有数据库：show databases；
     *
     *          ④ 使用数据库/进入数据库：use 数据库名;
     *
     *          ⑤ 查看某个数据库的所有表：show tables;  或者  show tables from 数据库名;
     *
     *          ⑥ 查看表的创建信息：show create table 表名;
     *
     *          ⑦ 查看数据库的创建信息：show create database 数据库名称;
     *
     *          ⑧ 删除表格：drop table 表名;
     *
     *          ⑨ 删除数据库：drop database 数据库名;
     *
     *          ⑩ 显式表结构：DESCRIBE 表名;
     *
     *      6、MySQL字符集编码格式设置：
     *          ① 查看编码命令1：show variables like 'character_%';
     *          ② 查看编码命令2：show variables like 'collation_%';
     *
     *      7、MySQL图形化管理工具
     *          > MySQL workbench：官方提供的MySQL图形化工具
     *
     *          > Navicat：
     *          > SQLyog:
     *          > dbeaver:
     *
     *      8、MySQL目录结构与源码
     *          > bin目录：所有MySQL的可执行文件。如：mysql.exe
     *          > MySQLInstanceConfig.exe：数据库的配置向导，在安装时出现的内容
     *          > data目录：系统数据库所在的目录
     *          > my.ini文件：MySQL的主要配置文件
     *          > c:\ProgramData\MySQL\MySQL Server 8.0\data\ 用户创建的数据库所在的目录
     *
     *          > 源码：MySQL社区版时免费的，其官网对外公布源码，可以自行下载
     *
     *
     */
}
