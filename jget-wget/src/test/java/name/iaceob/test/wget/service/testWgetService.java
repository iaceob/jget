package name.iaceob.test.wget.service;

import name.iaceob.wget.common.Charset;
import name.iaceob.wget.common.Const;
import name.iaceob.wget.common.Method;
import name.iaceob.wget.service.WgetService;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by iaceob on 2015/9/21.
 */
public class testWgetService {



    @Test
    public void testWS() throws IOException{
        WgetService ws = new WgetService();
        String html = ws.fetchContent("http://tieba.com", Charset.GBK, Method.GET, Const.DEFAULT_UA);
        System.out.println(html);
    }


}
