package name.iaceob.jget.test.wget;

import com.github.axet.wget.info.DownloadInfo;
import name.iaceob.jget.download.execute.MultithreadDownload;
import org.junit.Test;

import java.net.URL;

/**
 * Created by iaceob on 2015/9/22.
 */
public class testw {



    @Test
    public void  tw() throws Exception {
        URL url = new URL("http://releases.ubuntu.com/14.04.3/ubuntu-14.04.3-desktop-amd64.iso");
        DownloadInfo di = new DownloadInfo(url);
        String path = "/home/iaceob/tmp";
        String fileName = "ubuntu-1404-amd64";
        String suffix = "iso";
        MultithreadDownload md = new MultithreadDownload(di, path, fileName, suffix);
        md.start();
    }


}
