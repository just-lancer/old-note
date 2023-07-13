package practice04_partitioner;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlowBean implements Writable {
    private long upFlow;
    private long downFlow;
    private long sumFlow;

    public FlowBean() {

    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlowBean flowBean = (FlowBean) o;

        if (upFlow != flowBean.upFlow) return false;
        if (downFlow != flowBean.downFlow) return false;
        return sumFlow == flowBean.sumFlow;
    }

    @Override
    public int hashCode() {
        int result = (int) (upFlow ^ (upFlow >>> 32));
        result = 31 * result + (int) (downFlow ^ (downFlow >>> 32));
        result = 31 * result + (int) (sumFlow ^ (sumFlow >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "FlowBean{" +
                "upFlow=" + upFlow +
                ", downFlow=" + downFlow +
                ", sumFlow=" + sumFlow +
                '}';
    }

    public void write(DataOutput dataOutput) throws IOException {
        // 属性序列化
        dataOutput.writeLong(upFlow);
        dataOutput.writeLong(downFlow);
        dataOutput.writeLong(sumFlow);
    }

    public void readFields(DataInput dataInput) throws IOException {
        // 反序列化
        this.upFlow = dataInput.readLong();
        this.downFlow = dataInput.readLong();
        this.sumFlow = dataInput.readLong();
    }
}
