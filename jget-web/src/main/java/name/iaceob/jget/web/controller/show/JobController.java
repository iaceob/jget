package name.iaceob.jget.web.controller.show;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import name.iaceob.jget.web.common.AccountKit;
import name.iaceob.jget.web.common.Const;
import name.iaceob.jget.web.common.JobStat;
import name.iaceob.jget.web.model.CliModel;
import name.iaceob.jget.web.model.JobModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by cox on 2015/9/22.
 */
public class JobController extends Controller {

    private static final Logger log = LoggerFactory.getLogger(JobController.class);
        String tit = "任务";

    public void index() {
        JobStat[] stats = JobStat.values();
        String[] sts = super.getParaValues("stat");
        if (sts!=null&&sts.length>0) {
            stats = new JobStat[sts.length];
            for (Integer i=sts.length; i-->0;)
                stats[i] = JobStat.getJobStat(sts[i]);
        }
        Page<Record> pr = JobModel.dao.getPageJob(stats, super.getParaToInt(Const.PAGEPARA, 1), Const.PAGESIZE);
        super.setAttr("p", pr);
        super.setAttr("tit", tit);
        super.render("/job/index.html");
    }

    public void create() {
        List<Record> clis = CliModel.dao.getUsabledClis(AccountKit.getId(), PropKit.use(Const.PROPFILE).getInt("pro.cli.time.expired"));
        super.setAttr("clis", clis);
        super.setAttr("tit", tit);
        super.render("/job/create.html");
    }

}
