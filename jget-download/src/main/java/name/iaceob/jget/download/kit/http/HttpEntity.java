package name.iaceob.jget.download.kit.http;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    // ==== Header Method
    public String getContentType() {
        String ct = this.getHeader().getStr("Content-Type");
        return StrKit.isBlank(ct) ? null : ct.split(";")[0];
    }
    public Integer getContentLength() {
        return StrKit.isBlank(this.getHeader().getStr("Content-Length")) ? null : Integer.valueOf(this.getHeader().getStr("Content-Length"));
    }
    public Integer getStat() {
        Record r = this.getHeader();
        String msg = this.getHeader().getStr(null);
        Pattern p = Pattern.compile("[ ](\\d*)[ ]");
        Matcher m = p.matcher(msg);
        return m.find() ? Integer.valueOf(m.group(1).trim()) : 0;
    }
    public String getServer() {
        return this.getHeader().getStr("Server");
    }
    public String getLastModified() {
        return this.getHeader().getStr("Last-Modified");
        /*
        try {
            DateFormat format=new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            return format.parse(this.getHeader().getStr("Last-Modified"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        */
    }
    public String getCookie() {
        Object obj = this.getHeader().get("Set-Cookie");
        if (obj instanceof List)
            return HttpParse.parseCookie((List) obj);
        if (obj instanceof String)
            return HttpParse.parseCookie(obj.toString());
        return null;
    }

    public String getCharset() {
        String contentType = this.getContentType();
        Pattern p = Pattern.compile("charset=(.*)");
        Matcher m = p.matcher(contentType);
        String cs = null;
        if (m.find())
            cs = m.group(1);
        if (StrKit.isBlank(cs)) {
            String html = this.getHtml();
            Pattern p2 = Pattern.compile("charset=[\"|'](.*?)[\"|']|<meta.*content=.*charset=(.*?)[\"|']");
            Matcher m2 = p2.matcher(html);
            if (m2.find())
                cs = StrKit.isBlank(m2.group(1)) ? m2.group(2) : m2.group(1);
        }
        return StrKit.isBlank(cs) ? null : cs.toUpperCase();
    }




}
