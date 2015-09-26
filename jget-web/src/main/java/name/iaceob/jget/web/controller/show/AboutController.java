package name.iaceob.jget.web.controller.show;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import name.iaceob.jget.web.interceptor.AccountInterceptor;

/**
 * Created by iaceob on 2015/9/26.
 */
public class AboutController extends Controller {

    @Clear({AccountInterceptor.class})
    public void index() {
        super.render("/about/index.html");
    }

}
