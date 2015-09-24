package name.iaceob.jget.web.controller.show;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import name.iaceob.jget.web.common.AccountKit;
import name.iaceob.jget.web.common.Const;
import name.iaceob.jget.web.interceptor.AccountInterceptor;

/**
 * Created by cox on 2015/9/22.
 */
public class AccountController extends Controller {

    @Clear({AccountInterceptor.class})
    public void reg() {
        super.render("/account/reg.html");
    }

    @Clear({AccountInterceptor.class})
    public void signin() {
        super.render("/account/signin.html");
    }

    public void out() {
        String id = super.getPara("i");
        String name = super.getPara("u");
        super.removeCookie(Const.HEARTKEY);
        AccountKit.clear();
        super.redirect("/");
    }
}
