package name.iaceob.jget.web.model;

import com.jfinal.ext.plugin.xlxlme.SqlKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db2;
import com.jfinal.plugin.activerecord.Record;
import name.iaceob.jget.web.kit.id.IdKit;

import java.util.List;

/**
 * Created by cox on 2015/9/22.
 */
public class CliModel {

    public static final CliModel dao = new CliModel();

    public String existCli(String name, String usr) {
        String sql = SqlKit.getSql("Cli.existCli");
        Record r = Db2.findFirst(sql, name, usr);
        if (r==null) return null;
        return r.getStr("id");
    }

    public String registerCli(String name, String ip, String usr) {
        String id = this.existCli(name, usr);
        if (StrKit.notBlank(id))
            return this.heartbeatCli(id, ip) ? id : null;
        id = IdKit.run.genId();
        String sql = SqlKit.getSql("Cli.registerCli");
        Integer res = Db2.update(sql, id, name, ip, usr);
        return res!=0 ? id : null;
    }

    public Boolean heartbeatCli(String id, String ip) {
        String sql = SqlKit.getSql("Cli.heartbeatCli");
        return Db2.update(sql, ip, id)!=0;
    }

    public List<Record> getUsabledClis(String usr, Integer expired) {
        String sql = SqlKit.getSql("Cli.getUsableClis");
        return Db2.find(sql, usr, expired);
    }

    public List<Record> getClis(String usr, Integer expired) {
        String sql = SqlKit.getSql("Cli.getClis");
        return Db2.find(sql, expired, usr);
    }

    public Long getCountCliByUsr(String usr) {
        String sql = SqlKit.getSql("Cli.getCountCliByUsr");
        return Db2.findFirst(sql, usr).getLong("c");
    }


}
