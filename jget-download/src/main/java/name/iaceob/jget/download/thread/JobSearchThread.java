package name.iaceob.jget.download.thread;

import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Record;
import name.iaceob.jget.download.common.JobType;
import name.iaceob.jget.download.exception.JobException;
import name.iaceob.jget.download.model.JobModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by iaceob on 2015/9/20.
 */
public class JobSearchThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(JobSearchThread.class);

    private String server;
    private String cli;
    private String usr;
    private ExecutorService pool;
    private Integer interval;
    private String savePath;

    public JobSearchThread(String server, String cli, String usr, Integer maxThreadNum, Integer interval, String savePath) {
        this.server = server;
        this.cli = cli;
        this.usr = usr;
        this.interval = interval;
        this.savePath = savePath;
        this.pool = Executors.newFixedThreadPool(maxThreadNum);
    }

    public void run() {
        Integer i=0;
        while (true) {
            try {
                if (i==100) {
                    log.info("获取任务错误次数达到 100 次, 将暂停 {} 分钟后再次尝试", this.interval);
                    TimeUnit.MINUTES.sleep(this.interval);
                    continue;
                }
                List<Record> jobs = JobModel.dao.getJobs(this.server, this.cli, this.usr);
                if (jobs==null || jobs.isEmpty()) {
                    log.info("未发现任务, 将会在 {} 分钟后再次获取", this.interval);
                    TimeUnit.MINUTES.sleep(this.interval);
                    continue;
                }
                for (Record job : jobs) {
                    log.debug(JsonKit.toJson(job));
                    this.pool.execute(new DownloadThread(this.server, this.usr, job.getStr("id"), job.getStr("url"), job.getStr("path"),
                            job.getStr("name"), job.getStr("suffix"), job.getStr("cookie"),
                            this.savePath, JobType.getJobType(job.getInt("type"))));
                }

                TimeUnit.MINUTES.sleep(this.interval);
            } catch (InterruptedException e) {
                i+=1;
                log.error("任务获取线程休眠失败", e);
            } catch (RuntimeException e) {
                i+=1;
                log.error("获取 url 失败", e);
            } catch (Exception e) {
                i+=1;
                log.error("获取任务失败; " + e.getMessage(), e);
            }
        }
    }
}
