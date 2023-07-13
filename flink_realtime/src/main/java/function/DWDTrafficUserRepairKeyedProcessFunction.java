package function;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import utils.DateFormatUtils;
import utils.StateDescriptorUtils;

/**
 * Author: shaco
 * Date: 2022/7/1
 * Desc: DWD层，流量域，新老用户身份修复函数
 */
public class DWDTrafficUserRepairKeyedProcessFunction extends KeyedProcessFunction<String, JSONObject, JSONObject> {
    /*
        对于用户行为日志数据中，common字段中的is_new字段，如果该字段值为1表是是新用户，值为0表示是老用户
        在用户端，可能由于用户将缓存清除，is_new数据从0又会回复到1，从老用户编程新用户，所以，我们需要将该字段进行整理
        整理逻辑：用一个键控状态存储每个新用户第一次登录时的日期
        is_new = 1：
            如果键控状态为null，说明是新用户，在状态中添加新用户登录的当前日期，并且，该用户当天的所有登录使用行为都是以新用户的身份
            如果键控状态不为null，说明不是型用户，只是用户端清除了缓存，使得is_new从0变成了1
        is_new = 0:
            如果键控状态为null，说明该实时项目第一次处理该用户的数据，并且该用户的用户端没有清除缓存，该用户是老用户，在该用户的键控状态中添加昨日日期为其登录日期
            如果键控状态不为null，说明该用户就是一个老用户，不用处理
         */

    // 申明键控状态，值状态就可以
    ValueState<String> loginDate;

    @Override
    public void open(Configuration parameters) throws Exception {
        // 初始化键控状态
        loginDate = getRuntimeContext().getState(StateDescriptorUtils.getDWDTrafficUserRepairStateDescriptor());
    }

    @Override
    public void processElement(JSONObject value, Context ctx, Collector<JSONObject> out) throws Exception {
        // 用户的key，也就是mid
        String mid = ctx.getCurrentKey();

        // 相应的键控状态中的值
        String stateValue = loginDate.value();

        // 对应的is_new的值
        String is_new = value.getJSONObject("common").getString("is_new");

        // 相应的该条数据时间戳对应的日期，以及转换成的日期字符串
        String date = DateFormatUtils.toDate(Long.valueOf(value.getString("ts")));

        // 逻辑判断
        if ("1".equals(is_new)) {
            if (stateValue == null) {
                // 确定是一个新用户，在状态中添加日期
                // 在后续的判断中，还需要判断是不是在同一天
                loginDate.update(date);
            } else {
                // 不是一个新用户，只是用户端缓存清除了
                // 将is_new 的值修改为0：调用put()方法，直接覆盖原来的值
                value.getJSONObject("common").put("is_new", "0");
            }
        } else {
            if (stateValue == null) {
                // 显示是一个老用户，flink应用程序首次处理该用户的数据
                // 添加状态
                String dt = DateFormatUtils.toDate(Long.parseLong(value.getString("ts")) - 24 * 60 * 60 * 1000);
                loginDate.update(dt);
            } else {
                // 这种情况需要看一下是不是在同一天中的多次访问
                if (date.equals(loginDate.value())) {
                    // 二者相等，那就是同一天的浏览记录，仍旧是新用户
                    value.getJSONObject("common").put("is_new", "0");
                } else {
                    System.out.println(">>>>>> 老用户：" + mid + "<<<<<");
                }
            }
        }

        out.collect(value);
    }
}
