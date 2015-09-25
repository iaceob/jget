package name.iaceob.jget.download.kit;


import name.iaceob.jget.download.kit.http.HttpKit;

/**
 * Created by iaceob on 2015/9/25.
 */
public class IpKit {

    public static String getPublicIp() {
        return HttpKit.get("http://ip.cip.cc").getHtml().trim();
    }

}
