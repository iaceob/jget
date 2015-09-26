package name.iaceob.jget.download.thread.download;

import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.ex.DownloadMultipartError;
import name.iaceob.jget.download.common.JobType;

import java.io.File;

/**
 * Created by iaceob on 2015/9/26.
 */
public interface FileDownloadApi {


    void start(String server, String usr, String job, DownloadInfo info, File file, String cookie, JobType type) throws DownloadMultipartError;

}
