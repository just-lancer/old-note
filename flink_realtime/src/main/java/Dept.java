public class Dept {
    String id;
    String name;
    Long ts;

    public Dept() {
    }

    public Dept(String id, String name, Long ts) {
        this.id = id;
        this.name = name;
        this.ts = ts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ts=" + ts +
                '}';
    }
}