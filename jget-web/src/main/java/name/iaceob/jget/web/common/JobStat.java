package name.iaceob.jget.web.common;

/**
 * Created by iaceob on 2015/9/22.
 */
public enum JobStat {
    EXTRACTING,
    EXTRACTING_DONE,
    DOWNLOADING,
    RETRYING,
    STOP,
    ERROR,
    DONE,
    WAIT,
    UNDEFINED;

    public static JobStat getJobStat(String stat) {
        JobStat[] j = JobStat.values();
        for (JobStat js : j)
            if (js.toString().equals(stat))
                return js;
        return WAIT;
    }
}
