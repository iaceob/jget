package name.iaceob.jget.test.t;

import name.iaceob.jget.test.serv.SimpleWgetService;
import org.junit.Test;

/**
 * Created by iaceob on 2015/9/20.
 */
public class testWget {


    @Test
    public void tw() {
        try {
            SimpleWgetService sws = new SimpleWgetService();
            String s = sws.fetchContent("http://www.chinaso.com/search/pagesearch.htm?q=T%E5%A4%A7", 3000, "utf-8");
            System.out.println(s);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
