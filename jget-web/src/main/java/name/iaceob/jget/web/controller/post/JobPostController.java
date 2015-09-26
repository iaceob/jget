package name.iaceob.jget.web.controller.post;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Record;
import name.iaceob.jget.web.common.AccountKit;
import name.iaceob.jget.web.common.JobStat;
import name.iaceob.jget.web.common.JobType;
import name.iaceob.jget.web.kit.Tool;
import name.iaceob.jget.web.kit.id.IdKit;
import name.iaceob.jget.web.model.JobModel;
import name.iaceob.jget.web.validator.job.CreateJobValidator;
import name.iaceob.jget.web.validator.job.RenewProgressValidator;
import name.iaceob.jget.web.validator.job.UpdateJobSizeValidator;
import name.iaceob.jget.web.validator.job.UpdateJobStatValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by iaceob on 2015/9/23.
 */
public class JobPostController extends Controller {

    private static final Logger log = LoggerFactory.getLogger(JobPostController.class);


    @Before({POST.class, CreateJobValidator.class})
    public void create() {
        String url = super.getPara("url");
        String name = super.getPara("name");
        String suffix = super.getPara("suffix");
        String path = super.getPara("path");
        String cli = super.getPara("cli");
        JobType type = JobType.getJobType(super.getParaToInt("type", 1));
        String cookie = super.getPara("cookie", null);
        String id = IdKit.run.genId();
        // 对于 web 来说获取远程文件长度太耗时, 这个数量由客户机进行修改
        // Integer size = FileKit.getFileLengthByUrl(url);
        Integer size = 0;
        String usr = AccountKit.getId();
        if (!JobModel.dao.createJob(id, name, suffix, size, path, url, type, cli, usr, cookie)) {
            super.renderJson(Tool.pushResult(-1, "创建任务失败 "));
            return;
        }
        log.info("任务创建成功, JobID: {}", id);
        super.renderJson(Tool.pushResult(1, "任务添加成功", id));
        /*
        Record res = new Record();
        res.set("id", id).set("size", size);
        super.renderJson(Tool.pushResult(size<0 ? 0 : 1, size<0 ? "任务添加成功, 但是获取文件尺寸失败, 若客户机获取文件信息成功将会修正" : "任务添加成功", res));
        */
    }



    @Before({POST.class})
    public void job_list() {
        String cli = super.getPara("cli");
        List<Record> list = JobModel.dao.getToBeExecJobByCli(cli);
        super.renderJson("jobs", list);
    }


    @Before({RenewProgressValidator.class})
    public void renew_progress() {
        String job = super.getPara("job");
        Double progress = Double.parseDouble(super.getPara("progress", "0.0"));
        if (!JobModel.dao.renewProgress(job, progress)) {
            super.renderJson(Tool.pushResult(-1, "更新进度失败"));
            return;
        }
        super.renderJson(Tool.pushResult(1, "下载进度更新成功"));
    }

    @Before({UpdateJobStatValidator.class})
    public void update_stat() {
        String job = super.getPara("job");
        JobStat stat = JobStat.getJobStat(super.getPara("stat"));
        String msg = super.getPara("msg", null);
        if (!JobModel.dao.saveJobStat(job, stat, msg)) {
            super.renderJson(Tool.pushResult(-1, "保存状态失败"));
            return;
        }
        super.renderJson(Tool.pushResult(1, "状态保存成功"));
    }


    @Before({UpdateJobSizeValidator.class})
    public void update_job_size() {
        String job = super.getPara("job");
        Long size = super.getParaToLong("size");
        if (!JobModel.dao.updateJobSize(job, size)) {
            super.renderJson(Tool.pushResult(-1, "修改下载文件尺寸失败"));
            return;
        }
        super.renderJson(Tool.pushResult(1, "修改下载文件尺寸成功 "));
    }

}
