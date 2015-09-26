package name.iaceob.jget.test.t;

import com.jfinal.plugin.activerecord.Record;
import name.iaceob.jget.download.kit.JsonKit;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by iaceob on 2015/9/26.
 */
public class testLocal {

    @Test
    public void ttd1() throws UnknownHostException{
        InetAddress ia = InetAddress.getLocalHost();
        System.out.println(ia.getHostName());
    }

    @Test
    public void ttd2() {
        String str = "null";
        Record r = JsonKit.fromJson(str, Record.class);
        System.out.println(com.jfinal.kit.JsonKit.toJson(r));
    }

}
