package name.iaceob.jget.web.common;

import com.jfinal.plugin.activerecord.Record;

/**
 * Created by iaceob on 14-12-2.
 */
public class AccountKit {

    private static Record account = new Record();

    public static void clear(){
        account.clear();
    }
    public static Record getAccount(){
        return account;
    }

    public static String getId() {
        return account.getStr("id");
    }
    public static void setId(String id) {
        account.set("id", id);
    }
    public static String getName() {
        return account.getStr("name");
    }
    public static void setName(String name) {
        account.set("name", name);
    }
    public static String getDname() {
        return account.getStr("dname");
    }
    public static void setDname(String dname) {
        account.set("dname", dname);
    }
    public static String getEmail() {
        return account.getStr("email");
    }
    public static void setEmail(String email) {
        account.set("email", email);
    }
}
