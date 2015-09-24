package name.iaceob.jget.web.common;

/**
 * Created by iaceob on 2015/9/22.
 */
public enum JobStat {
    EXTRACTING("提取"),
    EXTRACTING_DONE("提取结束"),
    DOWNLOADING("下载中"),
    RETRYING("重试"),
    STOP("停止"),
    ERROR("失败"),
    DONE("完成"),
    WAIT("等待"),
    UNDEFINED("未知");

    private final String name;
    private JobStat(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public String getStat() {
        return this.toString();
    }

    public static JobStat getJobStat(String stat) {
        JobStat[] j = JobStat.values();
        for (JobStat js : j)
            if (js.getStat().equals(stat.toUpperCase()))
                return js;
        return UNDEFINED;
    }
}
