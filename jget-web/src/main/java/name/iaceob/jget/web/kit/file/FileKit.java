package name.iaceob.jget.web.kit.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by iaceob on 2015/9/23.
 */
public class FileKit extends com.jfinal.kit.FileKit {

    private static final Logger log = LoggerFactory.getLogger(FileKit.class);


    /**
     * 返回远程文件大小, 如果返回的结果小于0表示获取失败
     * @param url
     * @return
     */
    public static Integer getFileLengthByUrl(String url) {
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            Integer size = conn.getContentLength();
            conn.getInputStream().close();
            return size;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return -1;
    }


}

