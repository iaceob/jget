package name.iaceob.jget.web.controller.show;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;
import name.iaceob.jget.web.common.AccountKit;
import name.iaceob.jget.web.common.Const;
import name.iaceob.jget.web.kit.Tool;
import name.iaceob.jget.web.model.CliModel;
import name.iaceob.jget.web.validator.cli.HeartbearCliValidator;

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


}
