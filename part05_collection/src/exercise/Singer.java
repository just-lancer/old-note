package exercise;

import java.util.Objects;

public class Singer {
    private String name;
    private int age;
    private int num;

    public Singer() {
    }

    public Singer(String name, int age, int num) {
        this.name = name;
        this.age = age;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Singer singer = (Singer) o;
        return age == singer.age &&
                num == singer.num &&
                Objects.equals(name, singer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, num);
    }

    @Override
    public String toString() {
        return "Singer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", num=" + num +
                '}';
    }
}
