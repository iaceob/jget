package name.iaceob.jget.download.core;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import name.iaceob.jget.download.common.Const;
import name.iaceob.jget.download.entity.JgetEntity;
import name.iaceob.jget.download.kit.CliKit;
import name.iaceob.jget.download.kit.IpKit;
import name.iaceob.jget.download.model.AccountModel;
import name.iaceob.jget.download.model.CliModel;
import name.iaceob.jget.download.model.JobModel;
import name.iaceob.jget.download.thread.AccountConnectThread;
import name.iaceob.jget.download.thread.CliHeartbeatThread;
import name.iaceob.jget.download.thread.CliIpThread;
import name.iaceob.jget.download.thread.JobSearchThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by iaceob on 2015/9/19.
 */
public class Jget {

    private static final Logger log = LoggerFactory.getLogger(Jget.class);
    private Prop conf = PropKit.use(Const.PROPFILE);


    public Boolean initConf() {
        try {
            log.info("初始化客户机信息 ...");

            // 如果发现没有配置客户机名称, 则默认去当前机器的名称
            InetAddress ia = InetAddress.getLocalHost();
            String hostName = ia.getHostName();

            String cliName = this.conf.get("jget.cli.name", hostName);
            String ip = IpKit.getPublicIp();
            String server = this.conf.getBoolean("jget.server.ssl", false) ? "https" : "http" + "://" +
                    this.conf.get("jget.server.host", "localhost") + ":" + this.conf.get("jget.server.port", "80") +
                    this.conf.get("jget.server.basepath");
            CliKit.setCliName(cliName);
            CliKit.setIp(ip);
            CliKit.setServer(server);
            log.info("客户机名: {}", CliKit.getCliName());
            log.info("客户机IP: {}", CliKit.getIp());
            log.info("服务端地址: {}", CliKit.getServer());


            String user = this.conf.get("jget.account.name");
            String passwd = this.conf.get("jget.account.passwd");
            JgetEntity jeUsr = AccountModel.dao.connectAccount(user, passwd);
            log.debug("来自服务端的消息: {}", jeUsr.getMsg());
            if (jeUsr.getStat()<0) {
                log.error("账户连接失败, 请检查账户配置; 配置项目: 账户 [ jget.account.name ] 密码 [ jget.account.passwd ]");
                return false;
            }
            log.info("连接账户 {} 成功", user);
            CliKit.setUsr(jeUsr.getCookie());

            JgetEntity jeCli = CliModel.dao.registerCli(CliKit.getServer(), CliKit.getIp(), CliKit.getCliName(), CliKit.getUsr());
            log.debug("来自服务端的消息: {}", jeCli.getMsg());
            if (jeCli.getStat()<0) {
                log.error("客户机注册失败, 错误原因: {}", jeCli.getMsg());
                return false;
            }
            CliKit.setCliId(jeCli.getExtra());
            log.info("客户机注册成功, Cli Id: {}", CliKit.getCliId());

            log.info("客户机信息初始化成功");
            return true;
        } catch (UnknownHostException e) {
            log.error("获取机器名称失败", e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }


    /**
     * 任务线程
     * @param server 服务端地址
     * @param cli 客户机id
     * @param usr 连接的账户
     * @param threadName 线程名
     * @param maxThreadNum 线程池最大数量
     * @param interval 间隔时间 (MU)
     * @param savePath 下载文件保存目录
     */
    public void startJobThread(String server, String cli, String usr, String threadName,
                               Integer maxThreadNum, Integer interval, String savePath) {
        Thread t = new Thread(new JobSearchThread(server, cli, usr, maxThreadNum, interval, savePath), threadName);
        t.start();
    }

    /**
     * 更新客户机 IP
     * @param interval 间隔时间 (h)
     * @param threadName 线程名
     */
    public void startCliIpThread(Integer interval, String threadName) {
        Thread t = new Thread(new CliIpThread(interval), threadName);
        t.start();
    }

    /**
     * 账户连接线程
     * @param name 账户名
     * @param passwd 密码
     * @param interval 间隔时间 (d)
     * @param threadName 线程名
     */
    public void startAccountConnect(String name, String passwd, Integer interval, String threadName) {
        Thread t = new Thread(new AccountConnectThread(CliKit.getServer(), name, passwd, interval), threadName);
        t.start();
    }

    /**
     * 客户机心跳线程
     * @param server 服务端地址
     * @param id 客户机id
     * @param ip 客户机ip
     * @param usr 连接账户
     * @param interval 间隔时间 (s)
     * @param threadName 线程名
     */
    public void startCliHeartbeatThread(String server, String id, String ip, String usr, Integer interval, String threadName) {
        Thread t = new Thread(new CliHeartbeatThread(server, id, ip, usr, interval), threadName);
        t.start();
    }

    public Boolean start() {
        try {
            this.startCliIpThread(this.conf.getInt("jget.interval.cli_ip", 1), "ClientIpThread");
            this.startAccountConnect(this.conf.get("jget.account.name"), this.conf.get("jget.account.passwd"),
                    this.conf.getInt("jget.interval.account_conn", 10), "AccountConnectThread");
            this.startCliHeartbeatThread(CliKit.getServer(), CliKit.getCliId(), CliKit.getIp(), CliKit.getUsr(),
                    this.conf.getInt("jget.interval.cli_heartbeat", 60), "CliHeartbeatThread");
            this.startJobThread(CliKit.getServer(), CliKit.getCliId(), CliKit.getUsr(), "JobThread",
                    this.conf.getInt("jget.thread.job.max_num", Runtime.getRuntime().availableProcessors()*2),
                    this.conf.getInt("jget.interval.job", 2), this.getSavePath());
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    private String getSavePath() {
        String path = this.conf.get("jget.download.path");
        if (StrKit.isBlank(path)) return PathKit.getRootClassPath();
        String usrHome = System.getProperties().getProperty("user.home");
        path = path.replaceAll("\\$\\{user\\.home\\}", usrHome);
        File file = new File(path);
        if (!file.exists()&&!file.mkdirs()) path = usrHome + "/" + Const.DEFAULTDOWNLOADPATH;
        return path;
    }


    public static void main(String[] args) {
        Jget jget = new Jget();
        if (!jget.initConf()) {
            log.error("开启配置信息失败");
            return;
        }
        if (!jget.start()) {
            log.error("下载服务开启失败");
            return;
        }
        log.info("下载服务开启成功");
    }

}
