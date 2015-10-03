package name.iaceob.jget.web.controller.show;

import com.jfinal.core.Controller;

/**
 * Created by cox on 2015/9/22.
 */
public class IndexController extends Controller {
    String tit = "首页";

    public void index() {
        super.setAttr("tit", tit);
        super.render("/about/index.html");
    }

}
