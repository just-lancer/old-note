package function;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

/**
 * Author: shaco
 * Date: 2022/7/1
 * Desc: DWD层，流量域，脏数据处理函数
 */
public class DWDTrafficDirtyDataProcessFunction extends ProcessFunction<String, JSONObject> {
    /*
    脏数据过滤：如果数据不能被解析成JSONObject对象，那么说明是脏数据
     */

    // 申明一个侧输出流标识对象
    OutputTag<String> dirtyTag;

    public DWDTrafficDirtyDataProcessFunction() {
    }

    public DWDTrafficDirtyDataProcessFunction(OutputTag<String> dirtyTag) {
        this.dirtyTag = dirtyTag;
    }

    @Override
    public void processElement(String value, Context ctx, Collector<JSONObject> out) throws Exception {
        try {
            out.collect(JSONObject.parseObject(value));
        } catch (Exception e) {
            ctx.output(dirtyTag, value);
        }
    }
}
