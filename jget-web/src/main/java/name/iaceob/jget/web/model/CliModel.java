package name.iaceob.jget.web.model;

import com.jfinal.ext.plugin.xlxlme.SqlKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db2;
import com.jfinal.plugin.activerecord.Record;
import name.iaceob.jget.web.kit.id.IdKit;

/**
 * Created by cox on 2015/9/22.
 */
public class CliModel {

    public static final CliModel dao = new CliModel();

    private String existCli(String name) {
        String sql = SqlKit.getSql("Cli.existCli");
        Record r = Db2.findFirst(sql, name);
        if (r==null) return null;
        return r.getStr("id");
    }

    public String registerCli(String name, String ip) {
        String id = this.existCli(name);
        if (StrKit.notBlank(id))
            return this.heartbeatCli(id) ? id : null;
        id = IdKit.run.genId();
        String sql = SqlKit.getSql("Cli.registerCli");
        Integer res = Db2.update(sql, id, name, ip);
        return res!=0 ? id : null;
    }

    public Boolean heartbeatCli(String id) {
        String sql = SqlKit.getSql("Cli.heartbeatCli");
        return Db2.update(sql, id)!=0;
    }

}
