package name.iaceob.jget.test.wget;

import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.ex.DownloadMultipartError;
import name.iaceob.jget.download.common.JobType;
import name.iaceob.jget.download.thread.download.FileDownloadApi;
import name.iaceob.jget.download.thread.download.FileDownloadMultithreading;
import name.iaceob.jget.download.thread.download.FileDownloadThread;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by iaceob on 2015/9/22.
 */
public class testw {

    private static final Logger log = LoggerFactory.getLogger(testw.class);


    @Test
    public void  tw() throws Exception {
        URL url = new URL("http://releases.ubuntu.com/14.04.3/ubuntu-14.04.3-desktop-amd64.iso");
        DownloadInfo di = new DownloadInfo(url);
        String path = "/home/iaceob/tmp";
        String fileName = "ubuntu-1404-amd64";
        String suffix = "iso";
        // FileDownloadMultithreading md = new FileDownloadMultithreading(di, path, fileName, suffix);
        // md.start();
    }


    @Test
    public void ttd2(){
        try {
            String url = "http://120.209.141.27/ws.cdn.baidupcs.com/file/8e18e6db280df98d42f3d7f00e0cf90f?bkt=p-4cb83b4a6e8aff8a78f6215c5904c321&xcode=2c21b4af45c6b96800f9c9a7bb81977fd76678b4f1dd38a6ae97ca166f54709c&fid=4130254330-250528-1033938810696872&time=1443246739&sign=FDTAXGERLBH-DCb740ccc5511e5e8fedcff06b081203-fsPLlvQPprRJlDCtomvEFzRD08Y%3D&to=hc&fm=Nin,B,M,mn&sta_dx=72&sta_cs=674&sta_ft=mp4&sta_ct=7&fm2=Ningbo,B,M,mn&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=1400ff5e20c2583ae69c337368ae01c3ac809191e3990000047f9516&sl=68354126&expires=8h&rt=pr&r=326518003&mlogid=6229082097013433751&vuk=4130254330&vbdid=3421910568&fin=%E9%93%B6%E9%AD%82-SP1%202005%5B480p%5D.mp4&fn=%E9%93%B6%E9%AD%82-SP1%202005%5B480p%5D.mp4&slt=pm&uta=0&rtype=1&iv=0&isw=0&dp-logid=6229082097013433751&dp-callid=0.1.1&wshc_tag=0&wsts_tag=56063293&wsid_tag=78d2a32c&wsiphost=ipdbm";
            String file = "/home/iaceob/tmp2/yingtamasp1.mp4";
            DownloadInfo info = new DownloadInfo(new URL(url));
            FileDownloadApi api;
            File target = new File(file);
            try {
                api = new FileDownloadMultithreading();
                api.start("", "", "1", info, target, null, JobType.TCP);
            } catch (RuntimeException e) {
                log.info("转为单线程下载");
                api = new FileDownloadThread();
                api.start("", "", "1", info, target, null, JobType.TCP);
            }
            log.info("Download Complete");
        }  catch (MalformedURLException e) {
            log.error("获取 url 失败, {}", e.getMessage(), e);
        } catch (DownloadMultipartError e) {
            log.debug(e.getInfo().getState().toString());
            for (DownloadInfo.Part p : e.getInfo().getParts()) {
                Throwable ee = p.getException();
                if (ee != null)
                    log.error(e.getMessage(), e);
            }
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
