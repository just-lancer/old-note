package common;

/**
 * Author: shaco
 * Date: 2022/6/29
 * Desc: Phoenix的配置常量
 */
public class PhoenixConstants {
    // 命名空间的名称
    public static final String NAMESPACE = "GMALL_REALTIME";

    // Phoenix url
    // zookeeper地址
    public static final String URL = "jdbc:phoenix:hadoop132,hadoop133,hadoop134:2181";

    // Phoenix Driver
    public static final String DRIVER = "org.apache.phoenix.jdbc.PhoenixDriver";

}
