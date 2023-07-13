package datasource;

public class Event {
    // 创建一个访问事件的POJO类
    // POJO类的要求：
    // 1、具有public属性
    // 2、具有空参构造器
    // 3、为所有属性提供get、set方法
    // 4、最好重写toString()方法
    public String name;
    public String url;
    public Long ts;

    public Event() {
    }

    // 提供一个全属性构造器，便于创建对象
    public Event(String name, String url, Long ts) {
        this.name = name;
        this.url = url;
        this.ts = ts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", ts=" + ts +
                '}';
    }
}
