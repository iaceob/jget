package name.iaceob.jget.download.thread;

import name.iaceob.jget.download.entity.JgetEntity;
import name.iaceob.jget.download.model.CliModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by iaceob on 2015/9/24.
 */
public class CliHeartbeatThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(CliHeartbeatThread.class);

    private String server;
    private Integer interval;
    private String id;
    private String ip;
    private String usr;

    public CliHeartbeatThread(String server, String id, String ip, String usr, Integer interval) {
        this.server = server;
        this.interval = interval;
        this.id = id;
        this.ip = ip;
        this.usr = usr;
    }


    @Override
    public void run() {
        Integer i=0;
        do {
            try {
                if (i==300) {
                    log.info("客户机心跳失败次数达到 300 次, 心跳将停止一天");
                    TimeUnit.DAYS.sleep(1);
                    i=0;
                    continue;
                }
                JgetEntity je = CliModel.dao.heartbeatCli(this.server, this.id, this.ip, this.usr);
                log.info("来自服务端的消息: {}", je.getMsg());
                if (je.getStat()<0) {
                    log.error("客户机心跳失败");
                    i+=1;
                }
                log.info("心跳成功, 将在 {} 秒后再次更新", this.interval);
                TimeUnit.SECONDS.sleep(this.interval);
            } catch (InterruptedException e) {
                i+=1;
                log.error(e.getMessage(), e);
            } catch (Exception e) {
                i+=1;
                log.error(e.getMessage(), e);
            }
        } while (true);
    }
}
