package Utils;

public class UserInfo {
    public String name;
    public Integer age;
    public String address;
    public Long time;

    public UserInfo() {
    }

    public UserInfo(String name, Integer age, String address, Long time) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.time = time;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", time=" + time +
                '}';
    }
}
