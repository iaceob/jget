package name.iaceob.jget.download.thread;

import name.iaceob.jget.download.core.Jget;
import name.iaceob.jget.download.entity.JgetEntity;
import name.iaceob.jget.download.kit.CliKit;
import name.iaceob.jget.download.kit.http.HttpEntity;
import name.iaceob.jget.download.model.AccountModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by iaceob on 2015/9/26.
 */
public class AccountConnectThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(AccountConnectThread.class);

    public String server;
    public String name;
    public String passwd;
    public Integer interval;

    public AccountConnectThread(String server, String name, String passwd, Integer interval) {
        this.server = server;
        this.name = name;
        this.passwd = passwd;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true) {
            try {
                JgetEntity je = AccountModel.dao.connectAccount(this.name, this.passwd);
                CliKit.setUsr(je.getCookie());
                log.info("账户连接成功, 将在 {} 天后再次连接", this.interval);
                TimeUnit.DAYS.sleep(this.interval);
            } catch (InterruptedException e) {
                log.error("账户同步线程休眠失败", e);
            } catch (RuntimeException e) {
                log.error("获取 url 失败", e);
            } catch (Exception e) {
                log.error("账户连接失败", e);
            }
        }
    }
}
