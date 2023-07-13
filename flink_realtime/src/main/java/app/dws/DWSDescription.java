package app.dws;

/**
 * Author: shaco
 * Date: 2022/7/22
 * Desc: Flink实时项目，DWS层需求描述
 */
public class DWSDescription {
    /**
     *  需求1：DWS层、流量域，来源关键词粒度，页面浏览，各窗口，汇总表
     *      DWS_Traffic_Source_Keyword_PageView_Window
     *  主要任务：
     *      -- 从DWD_Traffic_Page_log中读取页面日志创建动态表
     *      -- 建表的同时指定watermark以及指定事件时间字段
     *      -- 从日志数据中过滤出搜索行为
     *      -- 对搜索的内容进行分词
     *      -- 对分词得到的结果进行分组、开窗、聚合计算
     *      -- 将动态表转换为DateStream （这里的转换是因为Flink SQL当前还不支持CK的连接器）
     *      -- 将DateStream写到CK中
     */

    /**
     *  需求2：DWS层、流量域、版本-渠道-地区-新老访客粒度、UV、PV、会话数、跳出会话数、会话持续时间等指标、开窗聚合
     *  主要任务：
     *      -- 从dwd_traffic_page_log等相关主题中读取页面日志数据
     *      -- 从各主题中统计相关指标UV、PV、会话数、跳出会话数、会话持续时间等
     *      -- 提取出的各个指标用同一的数据结构（JavaBean对象进行封装，因为后续要使用UNION合并数据）
     *      -- 使用UNION将所有的指标数据进行合并，并设置水位线
     *      -- 按所需维度字段分组、聚合、开窗、聚合计算
     *      -- 最后将聚合结果写入到ClickHouse中
     */

    /**
     *  需求3：DWS层、流量域、首页和商品详情页粒度、独立访客数、开窗聚合
     *      -- 从dwd_traffic_page_log主题中读取页面日志
     *      -- 将JSON字符串转换成JsonObj对象
     *      -- 设置水位线
     *      -- 分组
     *      -- 计算首页和商品详情页的独立访客
     *      -- 开窗、聚合计算
     *      -- 将流中的数据写到ClickHouse中
     */
}
