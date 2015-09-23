package name.iaceob.jget.web.controller.show;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;
import name.iaceob.jget.web.common.Const;
import name.iaceob.jget.web.model.CliModel;

import java.util.List;

/**
 * Created by cox on 2015/9/22.
 */
public class JobController extends Controller {

    public void index() {
        super.render("/job/index.html");
    }

    public void create() {
        List<Record> clis = CliModel.dao.getUsabledClis("1", PropKit.use(Const.PROPFILE).getInt("pro.cli.time.expired"));
        super.setAttr("clis", clis);
        super.render("/job/create.html");
    }

}
