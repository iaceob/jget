package name.iaceob.jget.web.validator.cli;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.validate.Validator;
import name.iaceob.jget.web.kit.Tool;

/**
 * Created by iaceob on 2015/9/23.
 */
public class RegisterCliValidator extends Validator {
    @Override
    protected void validate(Controller controller) {
        super.validateRequiredString("ip", "null_ip", "请输入客户机ip");
        super.validateRequiredString("name", "null_name", "请输入客户机名称");
    }

    @Override
    protected void handleError(Controller controller) {
        if (StrKit.notBlank(controller.getAttrForStr("null_ip"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_ip")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("null_name"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_name")));
            return;
        }
    }
}
