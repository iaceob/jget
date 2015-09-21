package name.iaceob.jget.download.execute;

import com.github.axet.wget.WGet;
import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.ex.DownloadMultipartError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by iaceob on 2015/9/21.
 */
public class MultithreadDownload {

    private static final Logger log = LoggerFactory.getLogger(MultithreadDownload.class);


    private String file;
    private DownloadInfo info;
    private Long last = 0L;
    private AtomicBoolean stop = new AtomicBoolean(false);


    public MultithreadDownload(DownloadInfo info, String path, String fileName, String suffix) {
        this.info = info;
        path = path.endsWith("/")||path.endsWith("\\") ? path.substring(0, path.length()-1) : path;
        this.file = path + File.separator + fileName + "." + suffix;
    }



    public void start() {
        try {
            log.info("开始下载 URL: " + this.info.getSource().toString());

            Runnable notify = () -> {
                Logger log = LoggerFactory.getLogger(this.getClass());
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
                            if (!p.getState().equals(DownloadInfo.Part.States.DOWNLOADING)) break;
                            parts += String.format("Part#%d(%.2f) ", p.getNumber(), p.getCount() / (float) p.getLength());
                        }
                        log.info(String.format("%.2f %s", info.getCount() / (float) info.getLength(), parts));
                        break;
                    default:
                        break;

                }
            };

            this.info.extract(this.stop, notify);
            this.info.enableMultipart();
            File target = new File(this.file);
            WGet wget = new WGet(this.info, target);
            wget.download(this.stop, notify);
        } catch (DownloadMultipartError e) {
            for (DownloadInfo.Part p : e.getInfo().getParts()) {
                Throwable ee = p.getException();
                if (ee != null)
                    log.error(ee.getMessage(), ee);
            }
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


}
