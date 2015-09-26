package name.iaceob.jget.download.thread;

import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.ex.DownloadMultipartError;
import name.iaceob.jget.download.common.JobType;
import name.iaceob.jget.download.exception.JobException;
import name.iaceob.jget.download.model.JobModel;
import name.iaceob.jget.download.thread.download.FileDownloadApi;
import name.iaceob.jget.download.thread.download.FileDownloadMultithreading;
import name.iaceob.jget.download.thread.download.FileDownloadThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by iaceob on 2015/9/26.
 */
public class DownloadThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(DownloadThread.class);

    private String job;
    private String file;
    private String cookie;
    private JobType type;
    private String url;
    private String server;
    private String usr;
    private String fileName;

    public DownloadThread(String server, String usr, String id, String url, String path,
                          String name, String suffix, String cookie, String savePath, JobType type) {
        path = savePath + File.separator + (path.endsWith("/")||path.endsWith("\\") ? path.substring(0, path.length()-1) : path);
        new File(path).mkdirs();
        this.job = id;
        this.type = type;
        this.url = url;
        this.cookie = cookie;
        this.server = server;
        this.usr = usr;
        this.fileName = name + "." + suffix;
        this.file = path + File.separator + this.fileName;
    }

    @Override
    public void run() {
        FileDownloadApi api;
        try {
            log.info("开始下载 {} , URL: {}", this.fileName, this.url);
            DownloadInfo info = new DownloadInfo(new URL(this.url));
            JobModel.dao.updateJobSize(this.server, this.job, info.getLength(), this.usr);
            File target = new File(this.file);
            try {
                api =  new FileDownloadMultithreading();
                api.start(this.server, this.usr, this.job, info, target, this.cookie, this.type);
            } catch (RuntimeException e) {
                log.error(e.getMessage(), e);
                log.info("服务器不支持多线程下载, 将使用单线程进行下载, URL: {}", this.url);
                try {
                    api = new FileDownloadThread();
                    api.start(this.server, this.usr, this.job, info, target, this.cookie, this.type);
                } catch (Exception e2) {
                    log.error(e2.getMessage(), e);
                    throw new JobException("文件下载失败");
                }
            }

            log.info("文件 {} 下载完成, 保存路径: {}, URL: {}", this.fileName, this.file, this.url);
            JobModel.dao.completeDownload(this.server, this.job, this.usr);

        } catch (MalformedURLException e) {
            log.error("获取 url 失败, {}", e.getMessage(), e);
            JobModel.dao.errorDownload(this.server, this.job, e.getMessage(), this.usr);
        } catch (DownloadMultipartError e) {
            for (DownloadInfo.Part p : e.getInfo().getParts()) {
                Throwable ee = p.getException();
                if (ee != null) {
                    log.error(e.getMessage(), e);
                    JobModel.dao.errorDownload(this.server, this.job, e.getMessage(), this.usr);
                }
            }
        } catch (JobException e) {
            log.error(e.getMessage(), e);
            JobModel.dao.errorDownload(this.server, this.job, e.getMessage(), this.usr);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            JobModel.dao.errorDownload(this.server, this.job, e.getMessage(), this.usr);
        }
    }
}
