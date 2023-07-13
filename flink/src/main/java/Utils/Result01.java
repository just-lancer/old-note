package Utils;

public class Result01 {
    public String name;
    public Long sum;
    public Long max;
    public Long min;

    public Result01() {
    }

    public Result01(String name, Long sum, Long max, Long min) {
        this.name = name;
        this.sum = sum;
        this.max = max;
        this.min = min;
    }

    @Override
    public String toString() {
        return "Result01{" +
                "name=\"" + name + '\"' +
                ", sum=" + sum +
                ", max=" + max +
                ", min=" + min +
                '}';
    }
}
