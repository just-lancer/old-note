import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import utils.JDBCUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Author: shaco
 * Date: 2022/6/29
 * Desc:
 */
public class Demo {
    // 测试一下JDBC连接池有没有用
    public static void main(String[] args) {


    }

    @Test
    public void test1() {
        DruidDataSource druidDataSource = JDBCUtils.druidDataSource;

        // 获取连接
        DruidPooledConnection connection = null;
        PreparedStatement ps = null;
        try {
            connection = druidDataSource.getConnection();

            // 编写SQL
            String sql = "upsert into student values ('1010', 'diwu', 'wuhan')";

            ps = connection.prepareStatement(sql);

            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test2() {
        String str = "a,b,c,d,e";
        String[] arr = str.split(",");

        // 将数组转换成ArrayList
        List<String> strings = Arrays.<String>asList(arr);

        strings.remove("a");
//        for (String s : strings) {
//            if (s.equals("a")) {
//                strings.remove("a");
//            }
//        }
        System.out.println(strings);
    }

    @Test
    public void test3() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("a", 1);
        jsonObject.put("b", 2);
        jsonObject.put("c", 3);
        jsonObject.put("d", 4);

        System.out.println(jsonObject);

        for (int i = 0; i < jsonObject.size(); i++) {
            System.out.println(jsonObject);
        }
    }

    @Test
    public void test4() {
        String str = "{\"common\":{\"ar\":\"110000\",\"ba\":\"Huawei\",\"ch\":\"xiaomi\",\"is_new\":\"1\",\"md\":\"Huawei Mate 30\",\"mid\":\"mid_61130\",\"os\":\"Android 11.0\",\"uid\":\"621\",\"vc\":\"v2.1.134\"},\"displays\":[{\"display_type\":\"query\",\"item\":\"26\",\"item_type\":\"sku_id\",\"order\":1,\"pos_id\":1},{\"display_type\":\"query\",\"item\":\"24\",\"item_type\":\"sku_id\",\"order\":2,\"pos_id\":1},{\"display_type\":\"query\",\"item\":\"18\",\"item_type\":\"sku_id\",\"order\":3,\"pos_id\":3},{\"display_type\":\"query\",\"item\":\"7\",\"item_type\":\"sku_id\",\"order\":4,\"pos_id\":5},{\"display_type\":\"promotion\",\"item\":\"15\",\"item_type\":\"sku_id\",\"order\":5,\"pos_id\":2},{\"display_type\":\"promotion\",\"item\":\"14\",\"item_type\":\"sku_id\",\"order\":6,\"pos_id\":5},{\"display_type\":\"promotion\",\"item\":\"10\",\"item_type\":\"sku_id\",\"order\":7,\"pos_id\":3},{\"display_type\":\"query\",\"item\":\"2\",\"item_type\":\"sku_id\",\"order\":8,\"pos_id\":3},{\"display_type\":\"query\",\"item\":\"25\",\"item_type\":\"sku_id\",\"order\":9,\"pos_id\":4}],\"page\":{\"during_time\":18891,\"item\":\"图书\",\"item_type\":\"keyword\",\"last_page_id\":\"search\",\"page_id\":\"good_list\"},\"ts\":1593523052000}";

        JSONObject jsonObject = JSONObject.parseObject(str);


        JSONArray display = jsonObject.getJSONArray("displays");
        System.out.println(display);
        JSONObject displays = jsonObject.getJSONObject("displays");
        System.out.println(displays);
    }

    @Test
    public void test5() {
        String str = "{\"actions\":[{\"action_id\":\"get_coupon\",\"item\":\"3\",\"item_type\":\"coupon_id\",\"ts\":1593442109935}],\"common\":{\"ar\":\"230000\",\"ba\":\"Oneplus\",\"ch\":\"xiaomi\",\"is_new\":\"0\",\"md\":\"Oneplus 7\",\"mid\":\"mid_494080\",\"os\":\"Android 8.1\",\"uid\":\"740\",\"vc\":\"v2.1.134\"},\"page\":{\"during_time\":17870,\"item\":\"29\",\"item_type\":\"sku_id\",\"last_page_id\":\"login\",\"page_id\":\"good_detail\",\"source_type\":\"recommend\"},\"ts\":1593442101000}";

        JSONObject jsonObject = JSONObject.parseObject(str);

        JSONArray actions = jsonObject.getJSONArray("actions");

        System.out.println(actions);

    }
}
