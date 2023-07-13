package item.firststage.myinterceptor;

import com.alibaba.fastjson.JSON;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.Iterator;
import java.util.List;

public class Interceptor01 implements Interceptor {
    // 日志采集项目拦截器
    // 排除日志文件中，非json格式的数据
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        // 获取Event的body，并对数据进行判断
        byte[] body = event.getBody();
        String s = new String(body);
        try {
            // 如果数据能转换成Json，不报错，那么是我们需要的数据
            JSON.parse(s);
            return event;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<Event> intercept(List<Event> list) {
        Iterator<Event> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == null) {
                iterator.remove();
            }
        }
        return list;
    }

    @Override
    public void close() {

    }

    // 创建内部类，加载Interceptor
    public static class MyBulid implements Builder {

        @Override
        public Interceptor build() {
            return new Interceptor01();
        }

        @Override
        public void configure(Context context) {

        }
    }

}
