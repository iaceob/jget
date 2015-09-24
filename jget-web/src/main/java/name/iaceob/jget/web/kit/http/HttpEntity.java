package name.iaceob.jget.web.kit.http;

import com.jfinal.plugin.activerecord.Record;

/**
 * Created by iaceob on 2015/9/25.
 */
public class HttpEntity extends Record {

    public String getHtml() {
        return super.getStr("html");
    }
    public HttpEntity setHtml(String html) {
        super.set("html", html);
        return this;
    }
    public Record getHeader() {
        return super.get("header");
    }
    public HttpEntity setHeader(Record header) {
        super.set("header", header);
        return this;
    }
    public Integer getResponseCode() {
        return super.getInt("responsecode");
    }
    public HttpEntity setResponseCode(Integer code) {
        super.set("responsecode", code);
        return this;
    }


}
