package utils;

import bean.ConfigTableBean;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.ValueStateDescriptor;

/**
 * Author: shaco
 * Date: 2022/6/28
 * Desc: 获取状态描述器
 */
public class StateDescriptorUtils {
    // dim层，配置流，广播状态描述器
    public static MapStateDescriptor<String, ConfigTableBean> getDIMBroadcastStateDescriptor() {
        return new MapStateDescriptor<String, ConfigTableBean>("dimBroadcastStateDesc", String.class, ConfigTableBean.class);
    }

    // DWD层，流量域，新老用户身份修复值键控状态
    public static ValueStateDescriptor<String> getDWDTrafficUserRepairStateDescriptor() {
        return new ValueStateDescriptor<String>("dwdTrafficUserRepairStateDescriptor", String.class);
    }
}
