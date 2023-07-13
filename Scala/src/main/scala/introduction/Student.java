package introduction;

public class Student {
    String name;
    int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString(String name, int age) {
        return "name: " + name + ", age: " + age;
    }
}
