public class Emp {
    String id;
    String name;
    String deptNum;
    Long ts;

    public Emp() {
    }

    public Emp(String id, String name, String deptNum, Long ts) {
        this.id = id;
        this.name = name;
        this.deptNum = deptNum;
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

    public String getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(String deptNum) {
        this.deptNum = deptNum;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", deptNum='" + deptNum + '\'' +
                ", ts=" + ts +
                '}';
    }
}