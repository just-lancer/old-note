package Utils;

public class Event {
    public String name;
    public String page;
    public Long time;

    public Event() {
    }

    // 额外提供一个全属性构造器
    public Event(String name, String page, Long time) {
        this.name = name;
        this.page = page;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Event{" + "name=" + name + '\'' + ", page='" + page + '\'' + ", time=" + time + '}';
    }

}
