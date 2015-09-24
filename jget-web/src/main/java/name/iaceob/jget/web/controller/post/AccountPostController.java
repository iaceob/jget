package name.iaceob.jget.web.controller.post;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Record;
import name.iaceob.jget.web.common.Const;
import name.iaceob.jget.web.interceptor.AccountInterceptor;
import name.iaceob.jget.web.kit.PasswdKit;
import name.iaceob.jget.web.kit.Tool;
import name.iaceob.jget.web.kit.disgest.Disgest;
import name.iaceob.jget.web.kit.id.IdKit;
import name.iaceob.jget.web.model.AccountModel;
import name.iaceob.jget.web.validator.account.RegAccountValidator;
import name.iaceob.jget.web.validator.account.SignInAccountValidator;

/**
 * Created by cox on 2015/9/24.
 */
public class AccountPostController extends Controller {


    @Clear({AccountInterceptor.class})
    @Before({POST.class, SignInAccountValidator.class})
    public void signin() {
        String name = super.getPara("name");
        String passwd = super.getPara("passwd");
        Record a = AccountModel.dao.getAccountByName(name);
        if (a==null) {
            super.renderJson(Tool.pushResult(-1, "账户或者密码错误"));
            return;
        }
        if (!PasswdKit.checkpwd(passwd, a.getStr("passwd"))) {
            super.renderJson(Tool.pushResult(-1, "账户或者密码错误"));
            return;
        }
        String cookieVal = a.getStr("id") + Const.HEARTESPLIT
                + a.getStr("name") + Const.HEARTESPLIT
                + a.getStr("email") + Const.HEARTESPLIT
                + System.currentTimeMillis();
        super.setCookie(Const.HEARTKEY, Disgest.encodeRC4(cookieVal, Const.HEARTCRYSEED), 60*60*24*30);
        super.renderJson(Tool.pushResult(1, "登入成功"));
    }

    @Clear({AccountInterceptor.class})
    @Before({POST.class, RegAccountValidator.class})
    public void reg() {
        String name = super.getPara("name");
        String email = super.getPara("email");
        String passwd = super.getPara("passwd");
        String id = IdKit.run.genId();
        // passwd = passwd.replaceAll(Const.HEARTESPLIT, "****");
        name = name.replaceAll("\\s*|\\t|\\r|\\n|\\r\\n", "").trim();
        email = email.replaceAll("\\s*|\\t|\\r|\\n|\\r\\n", "").trim();
        if (AccountModel.dao.exit(name, email)) {
            super.renderJson(Tool.pushResult(-1, "该账户或者邮箱已经被使用"));
            return;
        }
        if (!AccountModel.dao.saveAccount(id, name, email, PasswdKit.encrypt(passwd))) {
            super.renderJson(Tool.pushResult(-1, "注册失败"));
            return;
        }
        super.renderJson(Tool.pushResult(1, "注册成功"));
    }



}
