package name.iaceob.jget.web.validator.job;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.validate.Validator;
import name.iaceob.jget.web.kit.Tool;

/**
 * Created by iaceob on 2015/9/23.
 */
public class CreateJobValidator extends Validator {
    @Override
    protected void validate(Controller controller) {
        super.validateRequiredString("url", "null_url", "请输入url");
        super.validateRequiredString("name", "null_name", "请输入保存的文件名称");
        super.validateRequiredString("suffix", "null_suffix", "请输入保存的文件后缀名");
        super.validateRequiredString("path", "null_path", "请输入文件保存的路径");
        super.validateRequiredString("cli", "null_cli", "请选择要执行任务的客户机");
        super.validateInteger("type", 1, 1, "fail_type", "任务类型错误");
    }

    @Override
    protected void handleError(Controller controller) {
        if (StrKit.notBlank(controller.getAttrForStr("null_url"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_url")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("null_name"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_name")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("null_suffix"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_suffix")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("null_path"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_path")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("null_cli"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("null_cli")));
            return;
        }
        if (StrKit.notBlank(controller.getAttrForStr("fail_type"))) {
            controller.renderJson(Tool.pushResult(-1, controller.getAttrForStr("fail_type")));
            return;
        }
    }
}
