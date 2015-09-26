package name.iaceob.jget.test.wget;

import com.github.axet.wget.WGet;
import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.ex.DownloadMultipartError;

import java.io.File;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by iaceob on 2015/9/21.
 */
public class testWget {




    AtomicBoolean stop = new AtomicBoolean(false);
    DownloadInfo info;
    long last;

    public void run() {
        try {
            // choise file
            URL url = new URL("http://releases.ubuntu.com/14.04.3/ubuntu-14.04.3-desktop-amd64.iso");
            // initialize url information object

            // Runnable notify = new FileDownloadMultithreading(info);

            Runnable notify = new Runnable() {
                @Override
                public void run() {
                    // notify app or save download state
                    // you can extract information from DownloadInfo info;
                    switch (info.getState()) {
                        case EXTRACTING:
                        case EXTRACTING_DONE:
                        case DONE:
                            System.out.println(info.getState());
                            break;
                        case RETRYING:
                            System.out.println(info.getState() + " " + info.getDelay());
                            break;
                        case DOWNLOADING:
                            long now = System.currentTimeMillis();
                            if (now - 1000 > last) {
                                last = now;

                                String parts = "";

                                for (DownloadInfo.Part p : info.getParts()) {
                                    if (p.getState().equals(DownloadInfo.Part.States.DOWNLOADING)) {
                                        parts += String.format("Part#%d(%.2f) ", p.getNumber(),
                                                p.getCount() / (float) p.getLength());
                                    }
                                }

                                System.out.println(String.format("%.2f %s", info.getCount() / (float) info.getLength(),
                                        parts));
                            }
                            break;
                        default:
                            break;
                    }
                }
            };

            info = new DownloadInfo(url);
            // extract infromation from the web
            info.extract(stop, notify);
            // enable multipart donwload
            info.enableMultipart();
            // Choise target file
            File target = new File("/home/iaceob/tmp/ubuntu-14.04.3-desktop-amd64.iso");
            // create wget downloader
            WGet w = new WGet(info, target);
            // will blocks until download finishes
            w.download(stop, notify);
        } catch (DownloadMultipartError e) {
            for (DownloadInfo.Part p : e.getInfo().getParts()) {
                Throwable ee = p.getException();
                if (ee != null)
                    ee.printStackTrace();
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        testWget e = new testWget();
        e.run();
    }


}
