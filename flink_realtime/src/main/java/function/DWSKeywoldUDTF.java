package function;

import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;
import utils.SplitWorldUtil;

/**
 * Author: shaco
 * Date: 2022/7/22
 * Desc: DWS层，用户自定义UDTF函数，
 */

@FunctionHint(output = @DataTypeHint("ROW<word STRING>"))
public class DWSKeywoldUDTF extends TableFunction<Row> {
    public void eval(String text) {
        for (String keyword : SplitWorldUtil.analyse(text)) {
            collect(Row.of(keyword));
        }
    }

}
