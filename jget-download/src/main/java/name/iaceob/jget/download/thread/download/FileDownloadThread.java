package name.iaceob.jget.download.thread.download;

import com.github.axet.wget.WGet;
import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.ex.DownloadMultipartError;
import name.iaceob.jget.download.common.JobType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by iaceob on 2015/9/26.
 */
public class FileDownloadThread implements FileDownloadApi {

    private static final Logger log = LoggerFactory.getLogger(FileDownloadThread.class);

    private Long last = 0L;
    // private AtomicBoolean stop = new AtomicBoolean(false);

    @Override
    public void start(String server, String usr, String job, DownloadInfo info, File file, String cookie, JobType type) throws DownloadMultipartError {

        info.extract();
        WGet w = new WGet(info, file);
        w.download();

    }
}
