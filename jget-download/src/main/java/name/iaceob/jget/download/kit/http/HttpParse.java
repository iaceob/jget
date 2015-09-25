package name.iaceob.jget.download.kit.http;

import java.util.Date;
import java.util.List;

/**
 * Created by cox on 2015/9/25.
 */
public class HttpParse {


    public static String parseCookie(String cookie) {
        String[] cks = cookie.split(";");
        return cks[0];
    }

    public static String parseCookie(List<String> cookie) {
        StringBuilder sb = new StringBuilder();
        for (String ck : cookie)
            sb.append(parseCookie(ck)).append(";");
        return sb.toString();
    }

    public static Date parseDate(String dateStr) {
        return null;
    }

    public static String parseReponse(String msg) {
        return null;
    }

}
