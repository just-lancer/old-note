package bean;

/**
 * Author: shaco
 * Date: 2022/6/28
 * Desc: 配置表Java Bean
 */
public class ConfigTableBean {
    public String source_table;
    public String sink_table;
    public String sink_columns;
    public String sink_pk;
    public String sink_extend;

    public ConfigTableBean() {
    }

    public ConfigTableBean(String source_table, String sink_table, String sink_columns, String sink_pk, String sink_extend) {
        this.source_table = source_table;
        this.sink_table = sink_table;
        this.sink_columns = sink_columns;
        this.sink_pk = sink_pk;
        this.sink_extend = sink_extend;
    }

    public String getSource_table() {
        return source_table;
    }

    public void setSource_table(String source_table) {
        this.source_table = source_table;
    }

    public String getSink_table() {
        return sink_table;
    }

    public void setSink_table(String sink_table) {
        this.sink_table = sink_table;
    }

    public String getSink_columns() {
        return sink_columns;
    }

    public void setSink_columns(String sink_columns) {
        this.sink_columns = sink_columns;
    }

    public String getSink_pk() {
        return sink_pk;
    }

    public void setSink_pk(String sink_pk) {
        this.sink_pk = sink_pk;
    }

    public String getSink_extend() {
        return sink_extend;
    }

    public void setSink_extend(String sink_extend) {
        this.sink_extend = sink_extend;
    }

    @Override
    public String toString() {
        return "ConfigTableBean{" +
                "source_table='" + source_table + '\'' +
                ", sink_table='" + sink_table + '\'' +
                ", sink_columns='" + sink_columns + '\'' +
                ", sink_pk='" + sink_pk + '\'' +
                ", sink_extend='" + sink_extend + '\'' +
                '}';
    }
}
