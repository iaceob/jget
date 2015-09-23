package name.iaceob.jget.web.controller.post;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.StrKit;
import name.iaceob.jget.web.kit.Tool;
import name.iaceob.jget.web.model.CliModel;
import name.iaceob.jget.web.validator.cli.RegisterCliValidator;

/**
 * Created by iaceob on 2015/9/23.
 */
public class CliPostController extends Controller {

    @Before({POST.class, RegisterCliValidator.class})
    public void register() {
        String name = super.getPara("name");
        String ip = super.getPara("ip");
        String usr = "1";
        String id = CliModel.dao.registerCli(name, ip, usr);
        if (StrKit.isBlank(id)) {
            super.renderJson(Tool.pushResult(-1, "注册失败"));
            return;
        }
        super.renderJson(Tool.pushResult(1, "客户机注册成功", id));
    }

    public void heartbeat() {
        String id  = super.getPara("id");
        if (!CliModel.dao.heartbeatCli(id)) {
            super.renderJson(Tool.pushResult(-1, "心跳失败"));
            return;
        }
        super.renderJson(Tool.pushSuccess());
    }
}
