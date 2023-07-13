package bean;

/**
 * Author: shaco
 * Date: 2022/7/23
 * Desc: DWS层，Flink SQL表数据转换成DataStream数据时，封装的Java Bean对象
 */
public class KeywordBean {
    public String stt;
    public String edt;
    public String source;
    public String keyword;
    public String keyword_count;
    public String ts;

    public KeywordBean() {
    }

    public KeywordBean(String stt, String edt, String source, String keyword, String keyword_count, String ts) {
        this.stt = stt;
        this.edt = edt;
        this.source = source;
        this.keyword = keyword;
        this.keyword_count = keyword_count;
        this.ts = ts;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getEdt() {
        return edt;
    }

    public void setEdt(String edt) {
        this.edt = edt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword_count() {
        return keyword_count;
    }

    public void setKeyword_count(String keyword_count) {
        this.keyword_count = keyword_count;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "KeywordBean{" +
                "stt='" + stt + '\'' +
                ", edt='" + edt + '\'' +
                ", source='" + source + '\'' +
                ", keyword='" + keyword + '\'' +
                ", keyword_count='" + keyword_count + '\'' +
                ", ts='" + ts + '\'' +
                '}';
    }
}
