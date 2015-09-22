package name.iaceob.jget.web.core;

import com.alibaba.druid.filter.stat.StatFilter;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.plugin.xlxlme.SlxlmePlugin;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.render.ViewType;
import name.iaceob.jget.web.common.Const;
import name.iaceob.jget.web.controller.show.AccountController;
import name.iaceob.jget.web.controller.show.CliController;
import name.iaceob.jget.web.controller.show.IndexController;
import name.iaceob.jget.web.controller.show.JobController;
import name.iaceob.jget.web.factory.BeetlFactory;
import name.iaceob.jget.web.handler.BasePathHandler;
import name.iaceob.jget.web.handler.ParamsHandler;
import org.beetl.core.GroupTemplate;
import org.beetl.ext.fn.JsonFunction;
import org.beetl.ext.fn.NoteFunction;
import org.beetl.ext.fn.PaginationFunction;
import org.beetl.ext.tag.CompressorPageTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cox on 2015/9/21.
 */
public class AppConfig extends JFinalConfig {

    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    private Prop pro = PropKit.use(Const.PROPFILE);

    @Override
    public void configConstant(Constants constants) {
        Boolean projectDebug = this.pro.getBoolean("pro.debug");
        log.debug("正在进入" + (projectDebug ? "开发" : "生产") + "环境运行");
        constants.setDevMode(projectDebug);
        constants.setEncoding(this.pro.get("pri.charset"));
        constants.setMainRenderFactory(new BeetlFactory());
        constants.setViewType(ViewType.JSP);
        GroupTemplate gte = BeetlFactory.groupTemplate;
        gte.registerFunction("toJson", new JsonFunction());
        gte.registerFunction("note", new NoteFunction());
        gte.registerFunction("pagination", new PaginationFunction());
        gte.registerTag("compressor", CompressorPageTag.class);
    }

    @Override
    public void configRoute(Routes routes) {
        routes.add("/", IndexController.class);
        routes.add("/account", AccountController.class);
        routes.add("/cli", CliController.class);
        routes.add("/job", JobController.class);
    }

    @Override
    public void configPlugin(Plugins plugins) {
        DruidPlugin druidPlugin = new DruidPlugin(
            this.pro.get("pro.db.pg.url"),
            this.pro.get("pro.db.pg.uname"),
            this.pro.get("pro.db.pg.passwd"));
        druidPlugin.setInitialSize(3).setMaxActive(10);
        druidPlugin.addFilter(new StatFilter());

        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        arp.setShowSql(this.pro.getBoolean("pro.debug"));
        arp.setDialect(new PostgreSqlDialect());
        plugins.add(druidPlugin);
        plugins.add(arp);

        plugins.add(new SlxlmePlugin(PathKit.getRootClassPath()));
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {
    }

    @Override
    public void configHandler(Handlers handlers) {
        handlers.add(new DruidStatViewHandler(this.pro.get("pro.druid.statPath")));
        handlers.add(new BasePathHandler("resPath", "/well"));
        handlers.add(new ParamsHandler());
    }


    @Override
    public void afterJFinalStart() {
        log.info("在端口: " + this.pro.getInt("pro.dev.port") + " 成功开启 " + this.pro.get("pro.name") +
                " " + this.pro.get("pro.version"));
    }


    public static void main(String[] args) {
        AppConfig app = new AppConfig();
        JFinal.start(PathKit.getWebRootPath() + app.pro.get("pro.dev.webappPath"), app.pro.getInt("pro.dev.port"),
                app.pro.get("pro.dev.context"), app.pro.getInt("pro.dev.interval"));
    }

}
