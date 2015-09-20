package name.iaceob.wget.common;

/**
 * Created by iaceob on 2015/9/21.
 */
public enum Charset {

    UTF8("UTF-8"),
    GBK("GBK");

    private final String name;
    private Charset(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }


}
