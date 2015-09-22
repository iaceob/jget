package name.iaceob.jget.web.common;

/**
 * Created by cox on 2015/9/22.
 */
public enum AccountStat {

    NORMAL(1, "正常"),
    DISABLED(2, "停用");
    private final Integer index;
    private final String name;
    private AccountStat(Integer index, String name) {
        this.index = index;
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public Integer getIndex() {
        return this.index;
    }

}
