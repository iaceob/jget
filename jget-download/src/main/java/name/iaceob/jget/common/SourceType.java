package name.iaceob.jget.common;

/**
 * Created by iaceob on 2015/9/20.
 */
public enum SourceType {

    PSQL("PSQL");

    private final String name;
    private SourceType(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

}
