package function;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.RichFilterFunction;

/**
 * Author: shaco
 * Date: 2022/6/28
 * Desc: dim层，将Kafka中的数据流进行过滤。
 * 如果无法data字段中的数据无法解析成一个JSON对象，那么说明该数据不完整，过滤
 * Maxwell首次全量同步时，开始全量同步和结束全量同步的标志是type类型分别为bootstrap-start和bootstrap-complete，这两个数据过滤
 */
public class DIMKafkaDSFilter extends RichFilterFunction<JSONObject> {
    @Override
    public boolean filter(JSONObject value) {
        try {
            value.getJSONObject("data");
            if ("bootstrap-start".equals(value.getString("type")) ||
                    "bootstrap-complete".equals(value.getString("type"))) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }
}
