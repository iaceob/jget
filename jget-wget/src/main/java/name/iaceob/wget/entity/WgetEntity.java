package name.iaceob.wget.entity;

import com.jfinal.plugin.activerecord.Record;

/**
 * Created by iaceob on 2015/9/20.
 */
public class WgetEntity extends Record {

    private String getHtml() {
        return super.getStr("html");
    }
    public WgetEntity setHtml(String html) {
        super.set("html", html);
        return this;
    }
    public Record getHeader() {
        return super.get("header");
    }
    public WgetEntity setHeader(Record header) {
        super.set("header", header);
        return this;
    }
    public Integer getStat() {
        return super.getInt("stat");
    }
    public WgetEntity setStat(Integer stat) {
        super.set("stat", stat);
        return this;
    }


}
