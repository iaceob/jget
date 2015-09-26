package name.iaceob.jget.web.validator.job;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.validate.Validator;
import name.iaceob.jget.web.kit.Tool;

/**
 * Created by iaceob on 2015/9/26.
 */
public class UpdateJobSizeValidator extends Validator {
    @Override
    protected void validate(Controller controller) {

        super.validateRequiredString("job", "null_job", "未知的任务");
        super.validateLong("size", "fail_size", "错误的尺寸");
    }

    @Override
    protected void handleError(Controller controller) {
        if (StrKit.notBlank(controller.getAttrForStr("null_job"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_job")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("fail_size"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("fail_size")));
            return;
        }
    }
}
