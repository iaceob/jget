package name.iaceob.jget.web.handler;

import com.jfinal.handler.Handler;
import com.jfinal.plugin.activerecord.Record;
import name.iaceob.jget.web.common.AccountKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by iaceob on 14-12-17.
 */
public class AccountHandler extends Handler {

    private static final Logger log = LoggerFactory.getLogger(AccountHandler.class);

    private String accountKey;

    public AccountHandler(){
        this.accountKey = "account";
    }
    public AccountHandler(String accountKey){
        this.accountKey = accountKey;
    }

    @Override
    public void handle(String target, HttpServletRequest request,
                       HttpServletResponse response, boolean[] isHandled) {
        Record f = AccountKit.getAccount();
        // log.debug("Account Info: " + JsonKit.toJson(f));
        request.setAttribute(this.accountKey, f);
        super.nextHandler.handle(target, request, response, isHandled);
    }
}
