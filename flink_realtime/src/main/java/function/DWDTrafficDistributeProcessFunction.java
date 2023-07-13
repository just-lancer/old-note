package function;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.DWDTrafficOutputTagConstants;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

/**
 * Author: shaco
 * Date: 2022/7/1
 * Desc: DWD层，流量域，用户行为日志分流处理函数
 */
public class DWDTrafficDistributeProcessFunction extends ProcessFunction<JSONObject, String> {
    // TODO 启动日志、错误日志、动作日志、曝光日志都发送到对应的Kafka主题中
    // TODO 没有对页面日志做任何操作，那么剩下的就是页面日志

    /*
    关于用户行为日志数据的说明：
    用户行为日志分为两类：一类是启动日志，包含字段：start, common, err
    一类是页面日志，包含字段：common, actions（数组，有多种操作）, display（数组，有多种曝光）, page, err
     */
    @Override
    public void processElement(JSONObject value, Context ctx, Collector<String> out) throws Exception {
        JSONObject common = value.getJSONObject("common");
        JSONObject start = value.getJSONObject("start");
        JSONArray displays = value.getJSONArray("displays");
        JSONObject page = value.getJSONObject("page");
        JSONArray actions = value.getJSONArray("actions");
        JSONObject err = value.getJSONObject("err");
        String ts = value.getString("ts");

        if (err != null) {
            // 错误日志
            // 直接将错误日志输出，并从JSON中删除
            ctx.output(DWDTrafficOutputTagConstants.ERRORLOGTAG, err.toJSONString());

            // value中删除错误日志字段
            value.remove("err");
        }

        if (start != null) {
            // 启动日志不为null，那么就是启动日志
            JSONObject resStart = new JSONObject();
            resStart.put("common", common);
            resStart.put("start", start);
            resStart.put("ts", ts);
            String res = resStart.toString();
            // 侧输出流
            ctx.output(DWDTrafficOutputTagConstants.STRATLOGTAG, res);
        } else {

            if (displays != null) {
                // 曝光日志不为null，那么就是页面日志
                // 曝光日志是由多个曝光构成的一个数组，需要将每个小的曝光日志拆出来，发送出去，做到最小粒度
                for (int i = 0; i < displays.size(); i++) {
                    JSONObject resDisplay = new JSONObject();
                    resDisplay.put("common", common);
                    resDisplay.put("display", displays.get(i));
                    resDisplay.put("page", page);
                    resDisplay.put("ts", ts);

                    // 侧输出流
                    ctx.output(DWDTrafficOutputTagConstants.DISPLAYLOGTAG, resDisplay.toJSONString());
                }
            }

            if (actions != null) {
                // 动作日志不为null
                // 动作日志也是一个数组类型的
                // 动作日志中，有自带的时间戳
                for (int i = 0; i < actions.size(); i++) {
                    JSONObject resAction = new JSONObject();
                    resAction.put("common", common);
                    resAction.put("action", actions.get(i));
                    resAction.put("page", page);

                    // 侧输出流
                    ctx.output(DWDTrafficOutputTagConstants.ACTIONLOGTAG, resAction.toJSONString());
                }
            }

            // 获取页面日志
            value.remove("displays");
            value.remove("actions");
            out.collect(value.toJSONString());
        }

    }
}
