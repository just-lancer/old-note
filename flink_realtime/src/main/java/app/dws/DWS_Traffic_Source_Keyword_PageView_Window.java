package app.dws;

import bean.KeywordBean;
import common.KafkaConstants;
import function.DWSKeywoldUDTF;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import utils.ClickHouseUtils;
import utils.KafkaUtils;

/**
 * Author: shaco
 * Date: 2022/7/22
 * Desc: DWS层，流量域，来源关键字粒度，页面浏览过程，开窗（窗口大小即是统计周期）
 * declare: DWS层表名的命名规范为dws_数据域_统计粒度_业务过程_统计周期
 */

// 已经定义好IK分词器和自定义UDTF函数
public class DWS_Traffic_Source_Keyword_PageView_Window {
    public static void main(String[] args) throws Exception {
        // TODO 1、创建流执行环境，并获得表环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        env.setParallelism(4);
        // 表环境中注册自定义函数
        tableEnv.createFunction("keyword_split", DWSKeywoldUDTF.class);

        // TODO 2、检查点设置（略）

        // TODO 3、从页面日志中获取页面数据，并过滤出搜索页面
        // 3.1 编写SQL，创建动态表
        String page_log_sql =
                "create table page_log_table (\n" +
                        "    `common` map<string, string>,\n" +
                        "    `page` map<string,string>,\n" +
                        "    `ts` string,\n" +
                        "    `row_time` AS TO_TIMESTAMP(FROM_UNIXTIME(cast(`ts` as bigint)/1000, 'yyyy-MM-dd HH:mm:ss')),\n" +
                        "    WATERMARK FOR row_time AS `row_time` - INTERVAL '5' SECOND \n" +
                        ")" + KafkaUtils.getKafkaConnector(KafkaConstants.DWD_TRAFFIC_PAGE_LOG, "dwd_traffic_page_log");
        // 3.2 执行sql，表环境中注册表
        tableEnv.executeSql(page_log_sql);

        // TODO 4、过滤搜索页数据
        // 4.1 编写SQL，过滤搜索页数据
        String filter_search_page_sql =
                "select \n" +
                        "    `page`['item'] as full_keyword,\n" +
                        "    `row_time`\n" +
                        "from page_log_table\n" +
                        "where `page`['last_page_id'] = 'search'\n" +
                        "and `page`['item'] is not null\n" +
                        "and `page`['item_type'] = 'keyword'";
        // 4.2 执行sql，获取Table对象
        Table filter_search_page_table = tableEnv.sqlQuery(filter_search_page_sql);
        // 4.3 表环境中注册表
        tableEnv.createTemporaryView("filter_search_page_table", filter_search_page_table);

        // TODO 5、进行关键词分解
        // 5.1 编写sql，进行关键词分解
        String split_keyword_sql =
                "select\n" +
                        "    `keyword`,\n" +
                        "    `row_time`\n" +
                        "from filter_search_page_table, \n" +
                        "lateral table(keyword_split(full_keyword)) as tem_table(`keyword`)";
        // 5.2 执行sql，并在获取Table对象
        Table keyword_table = tableEnv.sqlQuery(split_keyword_sql);
        // 5.3 表环境中注册表
        tableEnv.createTemporaryView("keyword_table", keyword_table);

        // TODO 6、进行分组开窗聚合
        // 6.1 编写sql
        String operation_sql =
                "select \n" +
                        "    DATE_FORMAT(TUMBLE_START(`row_time`, INTERVAL '10' SECOND),'yyyy-MM-dd HH:mm:ss') `stt`,\n" +
                        "    DATE_FORMAT(TUMBLE_END(`row_time`, INTERVAL '10' SECOND),'yyyy-MM-dd HH:mm:ss') `edt`,\n" +
                        "    cast('search' as string) as `source`,\n" +
                        "    `keyword`, \n" +
                        "    cast(count(*) as string) as `keyword_count`,\n" +
                        "    cast(UNIX_TIMESTAMP()*1000 as string) as `ts`\n" +
                        "from keyword_table \n" +
                        "group by `keyword`, TUMBLE(`row_time`, INTERVAL '10' SECOND)";
        // 6.2 执行sql，创建表
        Table operation_table = tableEnv.sqlQuery(operation_sql);

        // TODO 7、将分组、开窗、聚合结果写入到ClickHouse
        // Flink SQL当前版本的连接器不支持CLickHouse，可以自定义ClickHouse连接器，或者将表数据转换成DataStream，利用addSink()进行输出
        // 7.1 将表数据转换成流数据
        // 在转换成数据流之前，还需要封装一个JavaBean对象
        DataStream<KeywordBean> keywordDS = tableEnv.toAppendStream(operation_table, KeywordBean.class);

        // 7.2 将数据写入到ClickHouse中
        String to_ClickHouse_sql =
                "insert into table_temp (?,?,?,?,?,?)";
        keywordDS.addSink(ClickHouseUtils.getClickHouseSinkFunction(to_ClickHouse_sql));

        // 8、执行流数据处理
        env.execute();


    }
}
