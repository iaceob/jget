package name.iaceob.jget.web.validator.job;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.validate.Validator;
import name.iaceob.jget.web.kit.Tool;

/**
 * Created by iaceob on 2015/9/26.
 */
public class RenewProgressValidator extends Validator {
    @Override
    protected void validate(Controller controller) {
        super.validateRequiredString("job", "null_job", "未知的任务");
        super.validateDouble("progress", "fail_progress", "错误的进度");
    }

    @Override
    protected void handleError(Controller controller) {
        if (StrKit.notBlank(controller.getAttrForStr("null_job"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_job")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("fail_progress"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("fail_progress")));
            return;
        }
    }
}
