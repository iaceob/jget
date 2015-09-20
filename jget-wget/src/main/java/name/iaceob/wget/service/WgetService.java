package name.iaceob.wget.service;

import com.jfinal.kit.StrKit;
import name.iaceob.wget.common.Charset;
import name.iaceob.wget.common.Const;
import name.iaceob.wget.common.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by iaceob on 2015/9/21.
 */
public class WgetService {

    private static final Logger log = LoggerFactory.getLogger(WgetService.class);

    private String obtainContentFromURLConnection(HttpURLConnection connection, Charset charset) throws IOException{
        String encoding = connection.getContentEncoding();
        encoding = StrKit.isBlank(encoding) ? charset.getName() : encoding;
        InputStream in = connection.getInputStream();
        byte[] buffer = new byte[8192];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        while ((len = in.read(buffer)) != -1)
            baos.write(buffer, 0, len);
        in.close();
        try {
            return baos.toString(encoding);
        } catch (UnsupportedEncodingException e) {
            return baos.toString();
        }
    }

    public String fetchContent(String url, Charset charset, Method method, String ua) throws IOException {
        URL u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        try {
            connection.setConnectTimeout(Const.DEFAULT_CONNECT_TIMEOUT_MS);
            connection.setRequestMethod(method.getName());
            connection.setRequestProperty("User-Agent", ua);
            connection.connect();

            return this.obtainContentFromURLConnection(connection, charset);
        } finally {
            connection.disconnect();
        }
    }


}
