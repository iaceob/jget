package name.iaceob.jget.web.validator.account;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.validate.Validator;
import name.iaceob.jget.web.common.Const;
import name.iaceob.jget.web.kit.Tool;

/**
 * Created by cox on 2015/9/24.
 */
public class RegAccountValidator extends Validator {
    @Override
    protected void validate(Controller controller) {
        System.out.println(controller.getRequest().getRequestURL());
        super.validateRequiredString("name", "null_name", "请输入账户");
        super.validateRequiredString("email", "null_email", "请输入邮箱");
        super.validateRequiredString("passwd", "null_passwd", "请输入密码");
        super.validateEmail("email", "fail_email", "请输入正确的邮箱");
        String name = controller.getPara("name");
        if (StrKit.isBlank(name)) {
            super.addError("null_name", "请输入账户");
            return;
        }
        for (char cr : Const.ILLEGALTEXT) {
            for (Integer i=name.length(); i-->0;) {
                char c = name.charAt(i);
                if (cr != c) continue;
                super.addError("fail_name", "账户中存在非法字符");
                return;
            }
        }
    }

    @Override
    protected void handleError(Controller controller) {
        if (StrKit.notBlank(controller.getAttrForStr("null_name"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_name")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("null_email"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_email")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("null_passwd"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_passwd")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("fail_name"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("fail_name")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("fail_email"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("fail_email")));
            return;
        }
    }
}
