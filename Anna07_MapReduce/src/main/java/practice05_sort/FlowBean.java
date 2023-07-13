package practice05_sort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlowBean implements WritableComparable<FlowBean> {
    private String phoneNum;
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlowBean flowBean = (FlowBean) o;

        if (upFlow != flowBean.upFlow) return false;
        if (downFlow != flowBean.downFlow) return false;
        if (sumFlow != flowBean.sumFlow) return false;
        return phoneNum != null ? phoneNum.equals(flowBean.phoneNum) : flowBean.phoneNum == null;
    }

    @Override
    public int hashCode() {
        int result = phoneNum != null ? phoneNum.hashCode() : 0;
        result = 31 * result + (int) (upFlow ^ (upFlow >>> 32));
        result = 31 * result + (int) (downFlow ^ (downFlow >>> 32));
        result = 31 * result + (int) (sumFlow ^ (sumFlow >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "FlowBean{" +
                "phoneNum='" + phoneNum + '\'' +
                ", upFlow=" + upFlow +
                ", downFlow=" + downFlow +
                ", sumFlow=" + sumFlow +
                '}';
    }

    // 序列化
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(phoneNum);
        dataOutput.writeLong(upFlow);
        dataOutput.writeLong(downFlow);
        dataOutput.writeLong(sumFlow);
    }

    //反序列化
    public void readFields(DataInput dataInput) throws IOException {
        this.phoneNum = dataInput.readUTF();
        this.upFlow = dataInput.readLong();
        this.downFlow = dataInput.readLong();
        this.sumFlow = dataInput.readLong();
    }

    // 设置排序方式：按照总流量倒序排列
    public int compareTo(FlowBean o) {
        return this.sumFlow > o.sumFlow ? -1 : (this.sumFlow < o.sumFlow ? 1 : 0);
    }
}
