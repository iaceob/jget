package org.beetl.ext.fn;

import com.jfinal.kit.JsonKit;
import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * Created by cox on 2015/8/17.
 */
public class JsonFunction implements Function {
    @Override
    public Object call(Object[] objects, Context context) {
        try {
            String json = JsonKit.toJson(objects[0]);
            if (objects.length<2) return json;
            return json.replaceAll("&", "&amp;").replaceAll("\"", "&quot;");
        } catch (Exception e) {
            return "";
        }
    }
}