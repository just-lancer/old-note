package common;

import org.apache.flink.util.OutputTag;

/**
 * Author: shaco
 * Date: 2022/7/1
 * Desc: DWD层，流量域，侧输出流标识
 */
public class DWDTrafficOutputTagConstants {
    // 脏数据侧输出流标签
    public static final OutputTag<String> DIRTYDATATAG = new OutputTag<String>("dirtyData") {
    };

    // 启动日志侧输出流标签
    public static final OutputTag<String> STRATLOGTAG = new OutputTag<String>("startLog") {
    };

    // 曝光日志侧输出流标签
    public static final OutputTag<String> DISPLAYLOGTAG = new OutputTag<String>("displayLog") {
    };

    // 动作日志侧输出流标签
    public static final OutputTag<String> ACTIONLOGTAG = new OutputTag<String>("actionLog") {
    };

    // 错误日志侧输出流标签
    public static final OutputTag<String> ERRORLOGTAG = new OutputTag<String>("errorLog") {
    };
}
