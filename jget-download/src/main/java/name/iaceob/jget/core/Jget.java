package name.iaceob.jget.core;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import name.iaceob.jget.common.Const;
import name.iaceob.jget.common.SourceType;
import name.iaceob.jget.thread.JobSearchThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iaceob on 2015/9/19.
 */
public class Jget {

    private static final Logger log = LoggerFactory.getLogger(Jget.class);
    private Prop conf = PropKit.use(Const.PROPFILE);


    public Boolean initConf() {
        try {
            DruidPlugin dp = new DruidPlugin(
                this.conf.get("db.psql.url"),
                this.conf.get("db.psql.username"),
                this.conf.get("db.psql.passwd")
            );
            dp.addFilter(new StatFilter());
            WallFilter wo = new WallFilter();
            wo.setDbType("postgresql");
            dp.addFilter(wo);
            dp.setMaxActive(this.conf.getInt("db.psql.max_active"));

            ActiveRecordPlugin arp = new ActiveRecordPlugin(SourceType.PSQL.getName(), dp);
            arp.setDialect(new PostgreSqlDialect());
            // arpo.setContainerFactory(new CaseInsensitiveContainerFactory());
            arp.setShowSql(this.conf.getBoolean("jget.dev"));

            if (!dp.start()||!arp.start()) {
                log.error("连接数据库: " + this.conf.get("db.psql.url") + " 失败");
                return false;
            }
            log.info("连接数据库: " + this.conf.get("db.psql.url") + " 成功");

            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public void startJobThread() {
        Thread jt = new Thread(new JobSearchThread());
        jt.start();
    }


    public Boolean start() {
        try {
            this.startJobThread();
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }


    public static void main(String[] args) {
        Jget jget = new Jget();
        if (!jget.initConf()) {
            log.error("开启配置信息失败");
            return;
        }
        if (!jget.start()) {
            log.error("下载服务开启失败");
            return;
        }
        log.info("下载服务开启成功");
    }

}
