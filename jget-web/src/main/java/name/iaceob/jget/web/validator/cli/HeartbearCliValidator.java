package name.iaceob.jget.web.validator.cli;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.validate.Validator;
import name.iaceob.jget.web.kit.Tool;

/**
 * Created by iaceob on 2015/9/26.
 */
public class HeartbearCliValidator extends Validator {
    @Override
    protected void validate(Controller controller) {
        super.validateRequiredString("id", "null_id", "未找到当前客户机id ");
        super.validateRequiredString("ip", "null_ip", "未传递客户机ip");
    }

    @Override
    protected void handleError(Controller controller) {
        if (StrKit.notBlank(controller.getAttrForStr("null_id"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_id")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("null_ip"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_ip")));
            return;
        }
    }
}
