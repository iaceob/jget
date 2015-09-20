package name.iaceob.jget.test.serv;

import name.iaceob.jget.kit.disgest.Disgest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 一个封装好的Wget服务
 *
 * @author Arbow
 */
public class SimpleWgetService {

    private static final int DEFAULT_CONNECT_TIMEOUT_MS = 5000;
    private static final int DEFAULT_READ_TIMEOUT_MS = 10000;
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 直接取URL的页面内容，使用默认的超时和编码
     */
    public String fetchContent(String url) throws Exception {
        return fetchContent(url, DEFAULT_READ_TIMEOUT_MS, DEFAULT_CHARSET);
    }

    /**
     * 直接取URL的页面内容，使用给定的超时和编码
     */
    public String fetchContent(String url, int readTimeoutMs, String charset) throws Exception {
        URL u = new URL(url);
        HttpURLConnection urlConn = (HttpURLConnection) u.openConnection();
        try {
            urlConn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT_MS);
            urlConn.setReadTimeout(readTimeoutMs);
            urlConn.connect();

            return readContentFromURLConnection(urlConn, charset);
        } finally {
            urlConn.disconnect();
        }
    }

    private String readContentFromURLConnection(HttpURLConnection urlConn, String charset) throws IOException {
        String encoding = urlConn.getContentEncoding();
        if (encoding == null)
            encoding = charset;
        InputStream in = urlConn.getInputStream();
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

    /**
     * 直接取需要HTTP BASIC认证的URL的页面内容，使用给定的超时和编码
     */
    public String fetchAuthContent(String url, String username, String password) throws Exception {
        return fetchAuthContent(url, username, password, DEFAULT_READ_TIMEOUT_MS, DEFAULT_CHARSET);
    }

    /**
     * 直接取需要HTTP BASIC认证的URL的页面内容，使用给定的超时和编码
     */
    public String fetchAuthContent(String url, String username, String password, int readTimeoutMs, String charset)
            throws Exception {
        URL u = new URL(url);
        HttpURLConnection urlConn = (HttpURLConnection) u.openConnection();
        try {
            urlConn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT_MS);
            urlConn.setReadTimeout(readTimeoutMs);

            // byte[] encoded = Disgest.encodeBase64((username + ':' + password));
            String authString = "Basic " + Disgest.encodeBase64((username + ':' + password));
            urlConn.setRequestProperty("Authorization", authString);
            urlConn.connect();

            return readContentFromURLConnection(urlConn, charset);
        } finally {
            urlConn.disconnect();
        }
    }

    /**
     * 直接取URL页面的输入流，使用默认的超时。使用完毕后务必关闭输入流
     */
    public InputStream fetchStream(String url) throws Exception {
        return fetchStream(url, DEFAULT_READ_TIMEOUT_MS);
    }

    /**
     * 直接取URL页面的输入流，使用指定的超时。使用完毕后务必关闭输入流
     */
    public InputStream fetchStream(String url, int readTimeoutMs) throws Exception {
        URL u = new URL(url);
        HttpURLConnection urlConn = (HttpURLConnection) u.openConnection();
        urlConn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT_MS);
        urlConn.setReadTimeout(readTimeoutMs);
        urlConn.connect();
        return new WrapperInputStream(urlConn.getInputStream(), urlConn);
    }

    private static class WrapperInputStream extends InputStream {

        private InputStream delegateIn;
        private HttpURLConnection conn;

        public WrapperInputStream(InputStream delegateInputStream, HttpURLConnection connection) {
            this.delegateIn = delegateInputStream;
            this.conn = connection;
        }

        @Override
        public int read() throws IOException {
            return delegateIn.read();
        }

        @Override
        public int available() throws IOException {
            return delegateIn.available();
        }

        @Override
        public void close() throws IOException {
            delegateIn.close();
            conn.disconnect();
        }

        @Override
        public synchronized void mark(int readlimit) {
            delegateIn.mark(readlimit);
        }

        @Override
        public synchronized void reset() throws IOException {
            delegateIn.reset();
        }

        @Override
        public boolean markSupported() {
            return delegateIn.markSupported();
        }

    }
}
