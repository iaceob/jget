package name.iaceob.jget.web.controller.post;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import name.iaceob.jget.web.common.AccountKit;
import name.iaceob.jget.web.common.Const;
import name.iaceob.jget.web.kit.Tool;
import name.iaceob.jget.web.model.CliModel;
import name.iaceob.jget.web.validator.cli.HeartbearCliValidator;
import name.iaceob.jget.web.validator.cli.RegisterCliValidator;

/**
 * Created by iaceob on 2015/9/23.
 */
public class CliPostController extends Controller {

    @Before({POST.class, RegisterCliValidator.class})
    public void register() {
        String name = super.getPara("name");
        String ip = super.getPara("ip");
        String usr = AccountKit.getId();
        String existCli = CliModel.dao.existCli(name, usr);
        Long count = CliModel.dao.getCountCliByUsr(usr);
        // 如果当前账户下没有此名的客户机表示尚未注册过此机器, 这样才去验证是否超出上限, 否则这只是一个心跳而已, 当作客户机首次连接的心跳更改客户机信息
        if (StrKit.isBlank(existCli) && count >= PropKit.use(Const.PROPFILE).getInt("pro.cli.max")) {
            super.renderJson(Tool.pushResult(-1, "以达到客户机上限, 不可继续添加"));
            return;
        }
        String id = CliModel.dao.registerCli(name, ip, usr);
        if (StrKit.isBlank(id)) {
            super.renderJson(Tool.pushResult(-1, "注册失败, 未识别的客户机"));
            return;
        }
        super.renderJson(Tool.pushResult(1, "客户机注册成功", id));
    }



    @Before({POST.class, HeartbearCliValidator.class})
    public void heartbeat() {
        String id  = super.getPara("id");
        String ip = super.getPara("ip");
        if (!CliModel.dao.heartbeatCli(id, ip)) {
            super.renderJson(Tool.pushResult(-1, "心跳失败"));
            return;
        }
        super.renderJson(Tool.pushResult(1, "心跳成功"));
    }

}
