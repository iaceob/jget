package name.iaceob.jget.web.validator.account;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.validate.Validator;
import name.iaceob.jget.web.kit.Tool;

/**
 * Created by cox on 2015/9/24.
 */
public class SignInAccountValidator extends Validator {

    @Override
    protected void validate(Controller controller) {
        super.validateRequiredString("name", "null_name", "请输入账户");
        super.validateRequiredString("passwd", "null_passwd", "请输入密码");
    }

    @Override
    protected void handleError(Controller controller) {
        if (StrKit.notBlank(controller.getAttrForStr("null_name"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_name")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("null_passwd"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_passwd")));
            return;
        }
    }
}
