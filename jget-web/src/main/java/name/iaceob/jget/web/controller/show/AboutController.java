package name.iaceob.jget.web.controller.show;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import name.iaceob.jget.web.interceptor.AccountInterceptor;

/**
 * Created by iaceob on 2015/9/26.
 */
public class AboutController extends Controller {
    String tit = "说明";

    @Clear({AccountInterceptor.class})
    public void index() {
        super.setAttr("tit",tit);
        super.render("/about/index.html");
    }

}
