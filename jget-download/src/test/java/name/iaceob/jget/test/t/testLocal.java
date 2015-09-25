package name.iaceob.jget.test.t;

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

}
