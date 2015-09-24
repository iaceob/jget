package name.iaceob.jget.web.test;


import name.iaceob.jget.web.kit.http.HttpKit;
import org.junit.Test;

import java.util.Map;

/**
 * Created by iaceob on 2015/9/25.
 */
public class testHttp {

    @Test
    public void ttd1() {
        HttpKit.get("https://taobao.com", (Map)null, (Map)null);
    }

}
