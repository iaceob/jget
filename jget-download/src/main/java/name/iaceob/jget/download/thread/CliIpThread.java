package name.iaceob.jget.download.thread;

import name.iaceob.jget.download.kit.CliKit;
import name.iaceob.jget.download.kit.IpKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by iaceob on 2015/9/25.
 */
public class CliIpThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(CliIpThread.class);

    private Integer interval;

    public CliIpThread(Integer interval) {
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String ip = IpKit.getPublicIp();
                CliKit.setIp(ip);
                log.info("IP 更新成功, {} , {} 小时后再次更新", ip, this.interval);
                TimeUnit.HOURS.sleep(this.interval);
            } catch (InterruptedException e) {
                log.error("客户机 IP 更新线程休眠失败", e);
            } catch (RuntimeException e) {
                log.error("获取 url 失败", e);
            } catch (Exception e) {
                log.error("客户机 IP 更新失败", e);
            }
        }
    }
}
