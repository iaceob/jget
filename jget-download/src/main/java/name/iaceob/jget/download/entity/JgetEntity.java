package name.iaceob.jget.download.entity;

import com.jfinal.plugin.activerecord.Record;

/**
 * Created by iaceob on 2015/9/25.
 */
public class JgetEntity extends Record {

    public Integer getStat() {
        return super.getInt("stat");
    }
    public String getMsg() {
        return super.getStr("msg");
    }
    public String getCookie() {
        return super.getStr("cookie");
    }
    public <T> T getExtra() {
        return super.get("extra");
    }

}
