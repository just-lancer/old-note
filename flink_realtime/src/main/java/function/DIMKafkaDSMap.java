package function;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.RichMapFunction;

/**
 * Author: shaco
 * Date: 2022/6/28
 * Desc: dim层，对Kafka中的数据流进行映射，映射成一个JSONObject对象
 */
public class DIMKafkaDSMap extends RichMapFunction<String, JSONObject> {
    @Override
    public JSONObject map(String value) {
        return JSONObject.parseObject(value);
    }
}
