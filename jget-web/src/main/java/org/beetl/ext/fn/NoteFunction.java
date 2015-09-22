package org.beetl.ext.fn;

import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * Created by cox on 2015/8/17.
 */
public class NoteFunction implements Function {

    @Override
    public Object call(Object[] objects, Context context) {
        String category = objects[0].toString().toUpperCase();
        // if (StrKit.isBlank(category)) return ExtraKit.NOTE.get("UNKNOW");
        try {
            /*
            if ("PROXY".equals(category))
                return ExtraKit.NOTEPROXY.get(objects[1].toString());
            if ("INVITE.TYPE".equals(category))
                return ExtraKit.NOTEINVITETYPE.get(objects[1].toString());
            if ("INVITE.STAT".equals(category))
                return ExtraKit.NOTEINVITESTAT[Integer.valueOf(objects[1].toString())];
            return ExtraKit.NOTE.get("UNKNOW");
            */
            return "";
        } catch (Exception e) {
            // return ExtraKit.NOTE.get("UNKNOW");
            return "";
        }
    }
}
