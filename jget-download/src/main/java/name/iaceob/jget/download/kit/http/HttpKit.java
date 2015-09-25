package name.iaceob.jget.download.kit.http;


import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpKit {
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String CHARSET = "UTF-8";
    private static final SSLSocketFactory sslSocketFactory = initSSLSocketFactory();
    private static final HttpKit.TrustAnyHostnameVerifier trustAnyHostnameVerifier = new HttpKit().new TrustAnyHostnameVerifier();

    private HttpKit() {
    }

    private static SSLSocketFactory initSSLSocketFactory() {
        try {
            TrustManager[] e = new TrustManager[]{new HttpKit().new TrustAnyTrustManager()};
            SSLContext sslContext = SSLContext.getInstance("TLS", "SunJSSE");
            sslContext.init((KeyManager[])null, e, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    private static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        URL _url = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)_url.openConnection();
        if(conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection)conn).setSSLSocketFactory(sslSocketFactory);
            ((HttpsURLConnection)conn).setHostnameVerifier(trustAnyHostnameVerifier);
        }

        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setConnectTimeout(19000);
        conn.setReadTimeout(19000);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
        if(headers != null && !headers.isEmpty()) {
            Iterator i$ = headers.entrySet().iterator();

            while(i$.hasNext()) {
                Entry entry = (Entry)i$.next();
                conn.setRequestProperty((String)entry.getKey(), (String)entry.getValue());
            }
        }

        return conn;
    }

//    public static String get(String url, Map<String, String> queryParas, Map<String, String> headers) {
//        HttpURLConnection conn = null;
//
//        String e;
//        try {
//            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), "GET", headers);
//            conn.connect();
//            e = readResponseString(conn);
//        } catch (Exception var8) {
//            throw new RuntimeException(var8);
//        } finally {
//            if(conn != null) {
//                conn.disconnect();
//            }
//
//        }
//
//        return e;
//    }

    public static HttpEntity get(String url, Map<String, String> queryParas, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), "GET", headers);
            conn.connect();
            HttpEntity he = new HttpEntity();
            he.setHtml(readResponseString(conn));
            Map<String, List<String>> hfs = conn.getHeaderFields();
            Set<String> set = hfs.keySet();
            Iterator<String> it = set.iterator();
            Record hs = new Record();
            System.out.println(JsonKit.toJson(hfs));
            while (it.hasNext()) {
                String key = it.next();
                // it.remove();
                List<String> contents = hfs.get(key);
                if (contents.size()==1) {
                    hs.set(key, contents.get(0));
                    continue;
                }
                hs.set(key, contents);
            }
            he.setHeader(hs);
            return he;
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        } finally {
            if(conn != null) {
                conn.disconnect();
            }

        }
    }



//    public static String get(String url, Map<String, String> queryParas) {
//        return get(url, queryParas, (Map)null);
//    }
//
//    public static String get(String url) {
//        return get(url, (Map)null, (Map)null);
//    }

    public static String post(String url, Map<String, String> queryParas, String data, Map<String, String> headers) {
        HttpURLConnection conn = null;

        String var6;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), "POST", headers);
            conn.connect();
            OutputStream e = conn.getOutputStream();
            e.write(data.getBytes("UTF-8"));
            e.flush();
            e.close();
            var6 = readResponseString(conn);
        } catch (Exception var10) {
            throw new RuntimeException(var10);
        } finally {
            if(conn != null) {
                conn.disconnect();
            }

        }

        return var6;
    }

    public static String post(String url, Map<String, String> queryParas, String data) {
        return post(url, queryParas, data, (Map)null);
    }

    public static String post(String url, String data, Map<String, String> headers) {
        return post(url, (Map)null, data, headers);
    }

    public static String post(String url, String data) {
        return post(url, (Map)null, data, (Map)null);
    }

    private static String readResponseString(HttpURLConnection conn) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;

        try {
            inputStream = conn.getInputStream();
            BufferedReader e = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = null;

            while((line = e.readLine()) != null) {
                sb.append(line).append("\n");
            }

            String var5 = sb.toString();
            return var5;
        } catch (Exception var14) {
            throw new RuntimeException(var14);
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException var13) {
                    var13.printStackTrace();
                }
            }

        }
    }

    private static String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
        if(queryParas != null && !queryParas.isEmpty()) {
            StringBuilder sb = new StringBuilder(url);
            boolean isFirst;
            if(url.indexOf("?") == -1) {
                isFirst = true;
                sb.append("?");
            } else {
                isFirst = false;
            }

            String key;
            String value;
            for(Iterator i$ = queryParas.entrySet().iterator(); i$.hasNext(); sb.append(key).append("=").append(value)) {
                Entry entry = (Entry)i$.next();
                if(isFirst) {
                    isFirst = false;
                } else {
                    sb.append("&");
                }

                key = (String)entry.getKey();
                value = (String)entry.getValue();
                if(StrKit.notBlank(value)) {
                    try {
                        value = URLEncoder.encode(value, "UTF-8");
                    } catch (UnsupportedEncodingException var9) {
                        throw new RuntimeException(var9);
                    }
                }
            }

            return sb.toString();
        } else {
            return url;
        }
    }

//    public static String readIncommingRequestData(HttpServletRequest request) {
//        BufferedReader br = null;
//
//        try {
//            StringBuilder e = new StringBuilder();
//            br = request.getReader();
//            String line = null;
//
//            while((line = br.readLine()) != null) {
//                e.append(line).append("\n");
//            }
//
//            line = e.toString();
//            return line;
//        } catch (IOException var12) {
//            throw new RuntimeException(var12);
//        } finally {
//            if(br != null) {
//                try {
//                    br.close();
//                } catch (IOException var11) {
//                    var11.printStackTrace();
//                }
//            }
//
//        }
//    }

    private class TrustAnyTrustManager implements X509TrustManager {
        private TrustAnyTrustManager() {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    private class TrustAnyHostnameVerifier implements HostnameVerifier {
        private TrustAnyHostnameVerifier() {
        }

        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
