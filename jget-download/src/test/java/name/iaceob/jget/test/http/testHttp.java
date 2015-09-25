package name.iaceob.jget.test.http;


import com.jfinal.kit.JsonKit;
import name.iaceob.jget.download.kit.http.HttpEntity;
import name.iaceob.jget.download.kit.http.HttpKit;
import org.junit.Test;

import java.util.Map;

/**
 * Created by iaceob on 2015/9/25.
 */
public class testHttp {

    @Test
    public void ttd1() throws Exception{

        HttpEntity he = HttpKit.get("http://ip.cip.cc", (Map) null, (Map) null);
        // HttpEntity he = HttpKit.get("https://taobao.com", (Map) null, (Map) null);
        System.out.println(JsonKit.toJson(he));
        // System.out.println(he.getHtml());
        System.out.println(he.getContentType());
        System.out.println(he.getContentLength());
        System.out.println(he.getStat());
        System.out.println(he.getServer());
        System.out.println(he.getLastModified());
        System.out.println(he.getCookie());
        System.out.println(he.getCharset());
    }

}
