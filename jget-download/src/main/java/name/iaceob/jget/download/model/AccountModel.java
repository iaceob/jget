package name.iaceob.jget.download.model;

import name.iaceob.jget.download.entity.JgetEntity;
import name.iaceob.jget.download.kit.CliKit;
import name.iaceob.jget.download.kit.JsonKit;
import name.iaceob.jget.download.kit.http.HttpEntity;
import name.iaceob.jget.download.kit.http.HttpKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iaceob on 2015/9/25.
 */
public class AccountModel {

    private static final Logger log = LoggerFactory.getLogger(AccountModel.class);

    public static final AccountModel dao = new AccountModel();

    public JgetEntity connectAccount(String name, String passwd) {
        String data = "name=" +name + "&passwd=" + passwd;
        HttpEntity he = HttpKit.post(CliKit.getServer() + "/post/account/signin", data);
        String json = he.getHtml();
        String cookie = he.getCookie();
        log.debug(json.trim());
        log.debug(cookie);
        JgetEntity je = JsonKit.fromJson(json, JgetEntity.class);
        je.set("cookie", cookie);
        return je;
    }

}
