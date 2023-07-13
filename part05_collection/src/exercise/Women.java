package exercise;

import java.util.Objects;

public class Women implements Comparable {
    private String name;
    private int age;

    public Women() {
    }

    public Women(String name, int age) {
        this.name = name;
        this.age = age;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Women women = (Women) o;
        return age == women.age &&
                Objects.equals(name, women.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "Women{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Women) {
            Women w = (Women) o;
            if (this.getAge() > w.getAge()) {
                return 1;
            } else if (this.getAge() < w.getAge()) {
                return -1;
            } else {
                return 0;
            }
        } else {
            throw new RuntimeException("输入的数据不是Women类型的");
        }
    }
}
