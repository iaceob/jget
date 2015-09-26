package name.iaceob.jget.download.thread.download;

import com.github.axet.wget.WGet;
import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.URLInfo;
import com.github.axet.wget.info.ex.DownloadMultipartError;
import name.iaceob.jget.download.common.JobType;
import name.iaceob.jget.download.kit.CliKit;
import name.iaceob.jget.download.model.JobModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by iaceob on 2015/9/21.
 */
public class FileDownloadMultithreading implements FileDownloadApi {

    private static final Logger log = LoggerFactory.getLogger(FileDownloadMultithreading.class);

    private Long last = 0L;
    private AtomicBoolean stop = new AtomicBoolean(false);



    @Override
    public void start(String server, String usr, String job, DownloadInfo info, File file, String cookie, JobType type) throws DownloadMultipartError{

            log.info("开始下载 URL: {}", info.getSource().toString());

            Runnable notify = () -> {
                Logger log = LoggerFactory.getLogger(this.getClass());
                JobModel.dao.updateJobStat(server, job, info.getState(), usr);
                switch (info.getState()) {
                    case EXTRACTING:
                        log.info("Extracting file: " + info.getSource().toString());
                        break;
                    case EXTRACTING_DONE:
                        log.info("Extracting file done");
                        break;
                    case DONE:
                        log.info("Download don, file: " + info.getSource().toString());
                        break;
                    case RETRYING:
                        log.info("Retrying file: " + info.getSource().toString());
                        break;
                    case DOWNLOADING:
                        Long now = System.currentTimeMillis();
                        if (now - 1000L < last) break;
                        last = now;
                        String parts = "";

                        for (DownloadInfo.Part p : info.getParts()) {
                            if (!p.getState().equals(DownloadInfo.Part.States.DOWNLOADING)) continue;
                            parts += String.format("Part#%d(%.2f) ", p.getNumber(), p.getCount() / (float) p.getLength());
                        }
                        log.debug("线程进度: {}", parts);
                        // 实时更新下载进度
                        JobModel.dao.renewProgress(server, job, info.getCount() / (float) info.getLength(), usr);
                        break;
                    default:
                        break;
                }
            };

            info.extract(this.stop, notify);
            info.enableMultipart();
            WGet wget = new WGet(info, file);
            wget.download(this.stop, notify);

    }


}
