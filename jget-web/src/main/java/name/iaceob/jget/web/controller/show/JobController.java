package name.iaceob.jget.web.controller.show;

import com.jfinal.core.Controller;

/**
 * Created by cox on 2015/9/22.
 */
public class JobController extends Controller {

    public void index() {
        super.render("/job/index.html");
    }

}
