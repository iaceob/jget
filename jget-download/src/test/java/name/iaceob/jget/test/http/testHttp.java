package name.iaceob.jget.test.http;


import com.jfinal.kit.JsonKit;
import name.iaceob.jget.download.kit.http.HttpEntity;
import name.iaceob.jget.download.kit.http.HttpKit;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iaceob on 2015/9/25.
 */
public class testHttp {
    
    private static final Logger log = LoggerFactory.getLogger(testHttp.class);

    @Test
    public void ttd1() {
        // HttpEntity he = HttpKit.get("http://cip.cc");
        HttpEntity he = HttpKit.get("https://taobao.com", (Map) null, (Map) null);
        log.debug("result: {}", JsonKit.toJson(he));
        log.debug("ContentType: {}", he.getContentType());
        log.debug("ContentLength: {}", he.getContentLength());
        log.debug("Stat: {}", he.getStat());
        log.debug("Server: {}", he.getServer());
        log.debug("LastModified: {}", he.getLastModified());
        log.debug("Cookie: {}", he.getCookie());
        log.debug("Charset: {}", he.getCharset());
    }


    @Test
    public void ttd2() {
        Map<String, String> paras = new HashMap<>();
        paras.put("name", "ttd5");
        // paras.put("email", "ss@yopmail.com");
        paras.put("passwd", "0");
        HttpEntity he = HttpKit.post("http://localhost:8120/post/account/signin", paras, "");
        log.debug("result: {}", JsonKit.toJson(he));
        log.debug("ContentType: {}", he.getContentType());
        log.debug("ContentLength: {}", he.getContentLength());
        log.debug("Stat: {}", he.getStat());
        log.debug("Server: {}", he.getServer());
        log.debug("LastModified: {}", he.getLastModified());
        log.debug("Cookie: {}", he.getCookie());
        log.debug("Charset: {}", he.getCharset());
    }

}
