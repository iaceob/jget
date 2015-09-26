package name.iaceob.jget.web.validator.job;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.validate.Validator;
import name.iaceob.jget.web.common.JobStat;
import name.iaceob.jget.web.kit.Tool;

/**
 * Created by iaceob on 2015/9/26.
 */
public class UpdateJobStatValidator extends Validator {
    @Override
    protected void validate(Controller controller) {
        super.validateRequiredString("job", "null_job", "未知的任务");
        String stat = controller.getPara("stat");
        if (StrKit.isBlank(stat)) {
            super.addError("null_stat", "未找到状态");
            return;
        }
        Boolean res = false;
        for (JobStat s : JobStat.values()) {
            if (!s.getStat().equals(stat)) continue;
            res = true;
        }
        if (!res)
            super.addError("fail_stat", "未知的状态");
    }

    @Override
    protected void handleError(Controller controller) {
        if (StrKit.notBlank(controller.getAttrForStr("null_job"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_job")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("null_stat"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_stat")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("fail_stat"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("fail_stat")));
            return;
        }
    }
}
