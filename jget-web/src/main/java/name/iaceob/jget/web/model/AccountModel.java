package name.iaceob.jget.web.model;

import com.jfinal.ext.plugin.xlxlme.SqlKit;
import com.jfinal.plugin.activerecord.Db2;
import com.jfinal.plugin.activerecord.Record;
import name.iaceob.jget.web.common.AccountStat;

/**
 * Created by cox on 2015/9/22.
 */
public class AccountModel {

    public static final AccountModel dao = new AccountModel();


    public Boolean exit(String name, String email) {
        String sql = SqlKit.getSql("Account.exist");
        return Db2.findFirst(sql, name, email)!=null;
    }

    public Boolean saveAccount(String id, String name, String email, String passwd) {
        if (this.exit(name, email)) return false;
        String sql = SqlKit.getSql("Account.saveAccount");
        return Db2.update(sql, id, name, email, passwd)!=0;
    }

    public Record getAccountByName(String name) {
        String sql = SqlKit.getSql("Account.getAccountByName");
        return Db2.findFirst(sql, name);
    }

    public Boolean disabledAccount(String id) {
        String sql = SqlKit.getSql("Account.disabledAccount");
        return Db2.update(sql, AccountStat.DISABLED.getIndex(), id)!=0;
    }

}
