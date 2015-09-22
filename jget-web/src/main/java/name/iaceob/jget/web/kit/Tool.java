package name.iaceob.jget.web.kit;

import com.jfinal.kit.JsonKit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by iaceob on 2015/9/22.
 */
public class Tool {



    public static String pushSuccess() {
        return Tool.pushResult(1, null);
    }

    public static String pushResult(Integer stat, String msg) {
        return Tool.pushResult(stat, msg, null);
    }

    public static String pushResult(Integer stat, String msg, Object extra) {
        return "{\"stat\": " + stat + ", \"msg\": \"" + msg + "\", \"extra\": " + JsonKit.toJson(extra) + "}";
    }

    public static Boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("\\d*");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
    public static Integer strToInt(String target) {
        return strToInt(target, 0);
    }

    public static Integer strToInt(String target, Integer def) {
        return isNumber(target) ? Integer.valueOf(target) : def;
    }
}
