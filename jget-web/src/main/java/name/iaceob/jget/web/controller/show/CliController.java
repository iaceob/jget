package name.iaceob.jget.web.controller.show;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import name.iaceob.jget.web.kit.Tool;
import name.iaceob.jget.web.model.CliModel;

/**
 * Created by cox on 2015/9/22.
 */
public class CliController extends Controller {


    public void register() {
        String name = super.getPara("name");
        String ip = super.getPara("ip");
        String id = CliModel.dao.registerCli(name, ip);
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