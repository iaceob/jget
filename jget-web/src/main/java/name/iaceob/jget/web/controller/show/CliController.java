package name.iaceob.jget.web.controller.show;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;
import name.iaceob.jget.web.common.AccountKit;
import name.iaceob.jget.web.common.Const;
import name.iaceob.jget.web.kit.Tool;
import name.iaceob.jget.web.model.CliModel;

import java.util.List;

/**
 * Created by cox on 2015/9/22.
 */
public class CliController extends Controller {


    public void index() {
        List<Record> clis = CliModel.dao.getClis(AccountKit.getId(), PropKit.use(Const.PROPFILE).getInt("pro.cli.time.expired"));
        super.setAttr("clis", clis);
        super.render("/cli/index.html");
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
