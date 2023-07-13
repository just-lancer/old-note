package common;

/**
 * Author: shaco
 * Date: 2022/6/28
 * Desc: Kafka中需要用到的常量
 */
public class KafkaConstants {
    // DIM层需要消费的Kafka主题，即业务数据库中的表名
    public static final String DIM_TOPICS = "cart_info,comment_info,coupon_use,favor_info,order_detail,order_detail_activity,order_detail_coupon,order_info,order_refund_info,order_status_log,payment_info,refund_payment,user_info";

    // DIM层Kafka消费者组组名
    public static final String DIM_CONSUMER_GROUPID = "dim_consumer_group";

    // DWD层，流量域，用户行为日志数据的Kafka消费主题
    public static final String DWD_TRAFFIC_TOPICS = "topic_log";

    // DWD层，流量域，用户行为日志——页面日志数据的Kafka生产主题
    public static final String DWD_TRAFFIC_PAGE_LOG = "dwd_traffic_page_log";

    // DWD层，流量域，用户行为日志——错误日志数据的Kafka生产主题
    public static final String DWD_TRAFFIC_ERROR_LOG = "dwd_traffic_error_log";

    // DWD层，流量域，用户行为日志——启动日志数据的Kafka生产主题
    public static final String DWD_TRAFFIC_START_LOG = "dwd_traffic_start_log";

    // DWD层，流量域，用户行为日志——曝光日志数据的Kafka生产主题
    public static final String DWD_TRAFFIC_DISPLAY_LOG = "dwd_traffic_display_log";

    // DWD层，流量域，用户行为日志——动作日志数据的Kafka生产主题
    public static final String DWD_TRAFFIC_ACTIVE_LOG = "dwd_traffic_active_log";

    // DWD层，流量域，Kafka消费者组组名
    public static final String DWD_CONSUMER_GROUPID = "dwd_traffic_consumer_group";

    // DWD层，交易域，Kafka，购物车加购主题
    public static final String DWD_TRADE_CART_ADD = "dwd_trade_cartAdd";

    // DWD层，交易域，Kafka，订单预处理主题
    public static final String DWD_TRADE_ORDER_PREPROCESS = "dwd_trade_order_preprocess";

    // DWD层，交易域，Kafka，下单操作主题
    public static final String DWD_TRADE_ORDER_DETAIL = "dwd_trade_order_detail";

    // DWD层，交易域，Kafka，取消下单操作主题
    public static final String DWD_TRADE_ORDER_CANCEL = "dwd_trade_order_cancel";

}
