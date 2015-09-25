package name.iaceob.jget.web.handler;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by iaceob on 2015/9/25.
 */
public class CharsetHandler extends Handler {

    private String charset;
    public CharsetHandler() {
        this("UTF-8");
    }
    public CharsetHandler(String charset) {
        this.charset = charset;
    }

    @Override
    public void handle(String target, HttpServletRequest request,
                       HttpServletResponse response, boolean[] isHandled) {
        response.setCharacterEncoding(this.charset);
        super.nextHandler.handle(target, request, response, isHandled);
    }
}
