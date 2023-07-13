package function;

import bean.ConfigTableBean;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.state.BroadcastState;
import org.apache.flink.api.common.state.ReadOnlyBroadcastState;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction;
import org.apache.flink.util.Collector;
import utils.JDBCUtils;
import utils.PhoenixUtils;
import utils.StateDescriptorUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: shaco
 * Date: 2022/6/28
 * Desc: 广播连接流的处理，主业务流和广播流
 */
public class DIMBroadcastProcessFunction extends BroadcastProcessFunction<JSONObject, String, JSONObject> {
    DruidDataSource druidDataSource;

    @Override
    public void open(Configuration parameters) throws Exception {
        // 获取连接池对象
        druidDataSource = JDBCUtils.druidDataSource;
    }

    @Override
    public void processElement(JSONObject value, ReadOnlyContext ctx, Collector<JSONObject> out) throws Exception {
        // 业务数据流处理
        // 业务数据流的处理较为简单，判断数据是否来自维度表，如果是，那么将数据进行清洗成想要的格式，输出到下一个阶段
        // 如果不是维度表，那么不做任何处理，直接过滤掉
        // 获取广播状态
        ReadOnlyBroadcastState<String, ConfigTableBean> mainBroadcastState = ctx.getBroadcastState(StateDescriptorUtils.getDIMBroadcastStateDescriptor());
        // 主要就是两个字段，一个是table，一个是data
        String tableName = value.getString("table");
        if (mainBroadcastState.contains(tableName)) {
            // 说明该数据是维度数据，处理后向下游传递
            // data字段中的数据，不是每一条都是对分析统计是有用的，过滤掉不需要的数据，再向下游传递
            JSONObject dataJSONObject = value.getJSONObject("data");
            ConfigTableBean configTableBean = mainBroadcastState.get(tableName);
            // 数据过滤
            businessDataFilter(dataJSONObject, configTableBean);

            // 再添加数据将去向哪个表
            dataJSONObject.put("sink_table", mainBroadcastState.get(tableName).sink_table);

            // 向下游发送数据
            out.collect(dataJSONObject);
        } else {
            System.out.println(">>>>>>>>不是维度数据，过滤！<<<<<<<<");
        }

    }

    @Override
    public void processBroadcastElement(String value, Context ctx, Collector<JSONObject> out) throws Exception {
        // TODO 配置表数据流处理
        // 获取广播状态
        BroadcastState<String, ConfigTableBean> broadcastState = ctx.getBroadcastState(StateDescriptorUtils.getDIMBroadcastStateDescriptor());

        // 广播数据处理：配置流数据处理
        // 将配置流转换成JSONObject对象处理
        JSONObject jsonObject = JSONObject.parseObject(value);

        if ("d".equals(jsonObject.getString("op"))) {
            // 如果配置表中对数据的操作是删除一条配置表信息，那么在状态中就要删除对应的值
            String tableName = jsonObject.getJSONObject("before").getString("source_table");
            broadcastState.remove(tableName);
        } else {
            // 如果是修改配置表中的一条数据，应该不会是修改表结构，只是修改表数据，那么表依然存在，不需要做任何操作，表仍然存在
            // 或者创建表：create table if not exists tb_name
            // 或者是首次读取配置表，或者是插入一条数据到配置表中，那么就需要在HBase中创建一张表
            // 所以对op为：r、u、c等类型的情况，合并到一起
            // r：首次读取配置表的数据时
            // c：向配置表中插入一条数据
            // u：在配置表中更新一条数据
            // 除此之外，还需要在广播状态中更新状态或者插入新的状态

            JSONObject after = jsonObject.getJSONObject("after");

            String sourceTable = after.getString("source_table");
            String sinkTable = after.getString("sink_table");
            String sinkColumns = after.getString("sink_columns");
            String sinkPk = after.getString("sink_pk");
            String sinkExtend = after.getString("sink_extend");

            ConfigTableBean configTableBean = new ConfigTableBean(sourceTable, sinkTable, sinkColumns, sinkPk, sinkExtend);

            // 更新或者插入广播状态
            broadcastState.put(sourceTable, configTableBean);

            // 通过Phoenix在HBase中创建相应的表
            System.out.println("&&&&：创建维度表" + sinkTable);
            System.out.println(configTableBean);
            PhoenixUtils.createHBaseTable(sourceTable, sinkTable, sinkColumns, sinkPk, sinkExtend, druidDataSource);
        }
    }

    public void businessDataFilter(JSONObject data, ConfigTableBean conf) {
        // 需要的字段
        String[] columns = conf.sink_columns.split(",");
        List<String> columnsArr = Arrays.asList(columns);

        // 业务数据字段
        Set<Map.Entry<String, Object>> entries = data.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if (!columnsArr.contains(entry.getKey())) {
                // 如果需要的字段中不包含该字段，那么就需要删除该字段
                data.remove(entry.getKey());
            }
        }
    }
}
