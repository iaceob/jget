package name.iaceob.jget.web.model;

import com.jfinal.ext.plugin.xlxlme.SqlKit;
import com.jfinal.plugin.activerecord.Db2;
import name.iaceob.jget.web.common.JobStat;
import name.iaceob.jget.web.common.JobType;

/**
 * Created by cox on 2015/9/22.
 */
public class JobModel {

    public static final JobModel dao = new JobModel();

    private Boolean saveJob(String id, String name, String suffix, Integer size,
                          String path, String url, JobType type, String usr, String cookie) {
        String sql = SqlKit.getSql("Job.createJob");
        return Db2.update(sql, id, name, suffix.toLowerCase(), size, path, url, type.getIndex(), usr, cookie)!=0;
    }

    private Boolean startProgress(String job) {
        String sql = SqlKit.getSql("Job.saveProgress");
        return Db2.update(sql, job, 0D)!=0;
    }

    private Boolean saveJobCli(String job, String cli) {
        String sql = SqlKit.getSql("Job.saveJobCli");
        return Db2.update(sql, job, cli)!=0;
    }

    public Boolean saveJobStat(String job, JobStat stat) {
        String sql = SqlKit.getSql("Job.saveStat");
        return Db2.update(sql, job, stat.toString())!=0;
    }

    public Boolean renewProgress(String job, Double progress) {
        String sql = SqlKit.getSql("Job.renewProgress");
        return Db2.update(sql, job, progress)!=0;
    }

    public Boolean createJob(String id, String name, String suffix, Integer size,
                             String path, String url, JobType type, String cli,
                             String usr, String cookie) {
        Boolean res = Db2.tx(()->{
            if (!JobModel.dao.saveJob(id, name, suffix, size, path, url, type, usr, cookie)) return false;
            if (!JobModel.dao.saveJobCli(id, cli)) return false;
            if (!JobModel.dao.saveJobStat(id, JobStat.WAIT)) return false;
            return JobModel.dao.startProgress(id);
        });
        return res;
    }

}
