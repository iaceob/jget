package name.iaceob.jget.web.handler;

import com.jfinal.handler.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cox on 2015/8/26.
 */
public class ParamsHandler extends Handler {

    private static final Logger log = LoggerFactory.getLogger(ParamsHandler.class);

    @Override
    public void handle(String target, HttpServletRequest request,
                       HttpServletResponse response, boolean[] isHandled) {
        Enumeration<String> names = request.getParameterNames();
        Map<String, Object> params = new HashMap<>();
        while(names.hasMoreElements()) {
            String key = names.nextElement();
            params.put(key, request.getParameter(key));
        }
        request.setAttribute("paras", params);
        // log.debug(request.getHeaders("Content-Type")+"");
        super.nextHandler.handle(target, request, response, isHandled);
    }
}
