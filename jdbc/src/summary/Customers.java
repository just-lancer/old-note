package summary;

import java.sql.Date;
import java.util.Objects;

public class Customers {
    private int id;
    private String name;
    private String email;
    private Date birth;

    public Customers() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customers customers = (Customers) o;
        return id == customers.id &&
                Objects.equals(name, customers.name) &&
                Objects.equals(email, customers.email) &&
                Objects.equals(birth, customers.birth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, birth);
    }
}
