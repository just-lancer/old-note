package datasource;

public class Temp {
    public String monitor;
    public Double temp;

    public Temp() {
    }

    public Temp(String monitor, Double temp) {
        this.monitor = monitor;
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "Temp{" +
                "monitor='" + monitor + '\'' +
                ", temp=" + temp +
                '}';
    }
}
