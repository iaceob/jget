package name.iaceob.jget.common;

/**
 * Created by iaceob on 2015/9/20.
 */
public enum  JobStat {

    UNDEFINED(0),
    CREATE(1),
    DOWNLOAD(2);

    private final Integer index;
    private JobStat(Integer index) {
        this.index = index;
    }
    public Integer getIndex() {
        return this.index;
    }

}
