package name.iaceob.jget.web.controller.post;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Record;
import name.iaceob.jget.web.common.JobType;
import name.iaceob.jget.web.kit.Tool;
import name.iaceob.jget.web.kit.file.FileKit;
import name.iaceob.jget.web.kit.id.IdKit;
import name.iaceob.jget.web.model.JobModel;
import name.iaceob.jget.web.validator.job.CreateJobValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        String usr = "1";
        if (!JobModel.dao.createJob(id, name, suffix, size, path, url, type, cli, usr, cookie)) {
            super.renderJson(Tool.pushResult(-1, "创建任务失败 "));
            return;
        }
        super.renderJson(Tool.pushResult(1, "任务添加成功", id));
        /*
        Record res = new Record();
        res.set("id", id).set("size", size);
        log.info("任务创建成功, JOB INFO: {}", JsonKit.toJson(res));
        super.renderJson(Tool.pushResult(size<0 ? 0 : 1, size<0 ? "任务添加成功, 但是获取文件尺寸失败, 若客户机获取文件信息成功将会修正" : "任务添加成功", res));
        */
    }

}
