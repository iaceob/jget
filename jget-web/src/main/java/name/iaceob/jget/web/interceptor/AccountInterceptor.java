package name.iaceob.jget.web.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import name.iaceob.jget.web.common.AccountKit;
import name.iaceob.jget.web.common.Const;
import name.iaceob.jget.web.kit.disgest.Disgest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;

public class AccountInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(AccountInterceptor.class);

    @Override
    public void intercept(Invocation invocation) {
        Controller c = invocation.getController();
        // String basePath = c.getRequest().getContextPath();
        Cookie heart = c.getCookieObject(Const.HEARTKEY);
        if (heart==null) {
            AccountKit.clear();
            c.redirect("/account/signin");
            return;
        }
        try {
            String heartContent = Disgest.decodeRC4(heart.getValue(), Const.HEARTCRYSEED);
            String[] accountInfo = heartContent.split(Const.HEARTESPLIT);
            Long cookieTime = Long.parseLong(accountInfo[3]);
            /*
            if (cookieTime+1000l*60l*60l*24l*30l< System.currentTimeMillis()) {
                AccountKit.clear();
                c.redirect("/account/signin");
                return;
            }
            */
            AccountKit.setId(accountInfo[0]);
            AccountKit.setName(accountInfo[1]);
            AccountKit.setEmail(accountInfo[2]);
            invocation.invoke();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }


}
