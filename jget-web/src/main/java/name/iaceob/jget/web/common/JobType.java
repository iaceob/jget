package name.iaceob.jget.web.common;

/**
 * Created by iaceob on 2015/9/22.
 */
public enum JobType {

    TCP(1),
    TORRENT(2),
    MAGNETIC(3);
    private final Integer index;
    private JobType(Integer index) {
        this.index = index;
    }
    public Integer getIndex() {
        return this.index;
    }

}
