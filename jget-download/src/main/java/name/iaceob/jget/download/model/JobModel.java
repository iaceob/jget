package name.iaceob.jget.download.model;

import com.jfinal.plugin.activerecord.Db2;
import com.jfinal.plugin.activerecord.Record;
import name.iaceob.jget.download.common.JobStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by iaceob on 2015/9/20.
 */
public class JobModel {

    private static final Logger log = LoggerFactory.getLogger(JobModel.class);
    public static final JobModel dao = new JobModel();


    public List<Record> getJobs() {
        String sql = "select j.id, j.name, j.suffix, j.sz, j.ctime from j_job as j left join v_job_stat as vjs on j.id=vjs.job where vjs.stat=?";
        List<Record> jobs = Db2.find(sql, JobStat.CREATE.getIndex());
        return jobs;
    }


}
