package name.iaceob.jget.download.thread;

import com.jfinal.kit.HttpKit;
import com.sun.org.apache.xpath.internal.operations.Bool;
import name.iaceob.jget.download.common.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by iaceob on 2015/9/24.
 */
public class HeartbeatThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(HeartbeatThread.class);

    private Boolean ssl;
    private String host;
    private Integer port;
    private String basePath;

    public HeartbeatThread(Boolean ssl, String host, Integer port, String basePath) {
        this.ssl = ssl;
        this.host = host;
        this.port = port;
        this.basePath = basePath;
    }


    @Override
    public void run() {
        do {
            try {
                String heartbeatUrl = this.ssl ? "http" : "https" + "://" + this.host + ":" + this.port + "/" + this.basePath + Const.HEARTBEATMETHOD;
                String result = HttpKit.get(heartbeatUrl);
                log.debug(result);
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } while (true);
    }
}
