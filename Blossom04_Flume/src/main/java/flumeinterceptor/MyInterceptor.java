package flumeinterceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyInterceptor implements Interceptor {
    // 自定义flume拦截器，当数据中含有atguigu时，传输到channel1，其他的数据传输到channel2
    public void initialize() {

    }

    public Event intercept(Event event) {
        // 获取数据，event的数据用字节数组来表示数据，转换成字符串
        byte[] body = event.getBody();
        String bodyStr = new String(body);

        // 获取event的header，数据形式是map
        Map<String, String> headers = event.getHeaders();

        // 对数据进行过滤
        if (bodyStr.contains("atguigu")) {
            headers.put("tou", "atguigu");
        } else {
            headers.put("tou", "other");
        }

        event.setHeaders(headers);

        return event;
    }

    public List<Event> intercept(List<Event> list) {
        // 创建一个新的集合用于方法的返回
        List<Event> events = new ArrayList<Event>();

        // 这是一次性处理多个event的方法
        for (Event event : list
        ) {
            Event intercept = intercept(event);
            events.add(intercept);
        }
        return events;
    }

    public void close() {

    }

    // 注意：还需要创建一个内部类，用于实例化拦截器
    public static class MyBuilder implements Builder {

        public Interceptor build() {
            MyInterceptor myInterceptor = new MyInterceptor();
            return myInterceptor;
        }

        public void configure(Context context) {

        }
    }
}
