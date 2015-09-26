package name.iaceob.jget.download.model;

import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.URLInfo;
import com.jfinal.plugin.activerecord.Record;
import name.iaceob.jget.download.entity.JgetEntity;
import name.iaceob.jget.download.exception.JobException;
import name.iaceob.jget.download.kit.JsonKit;
import name.iaceob.jget.download.kit.http.HttpEntity;
import name.iaceob.jget.download.kit.http.HttpKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.resources.Messages_pt_BR;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iaceob on 2015/9/20.
 */
public class JobModel {

    private static final Logger log = LoggerFactory.getLogger(JobModel.class);
    public static final JobModel dao = new JobModel();


    public List<Record> getJobs(String server, String cli, String usr) {
        Map<String, String> header = new HashMap<>();
        header.put("Cookie", usr);
        String data = "cli=" + cli;
        HttpEntity he = HttpKit.post(server + "/post/job/job_list", data, header);
        Record r = JsonKit.fromJson(he.getHtml(), Record.class);
        if (r==null) return null;
        List<Record> jobs = r.get("jobs");
        if (jobs==null||jobs.isEmpty())  return null;
        return jobs;
    }


    public Boolean renewProgress(String server, String job, Float progress, String usr) {
        Map<String, String> header = new HashMap<>();
        Map<String, String> paras = new HashMap<>();
        header.put("Cookie", usr);
        paras.put("job", job);
        paras.put("progress", progress.toString());
        HttpEntity he = HttpKit.get(server + "/post/job/renew_progress", paras, header);
        JgetEntity je = JsonKit.fromJson(he.getHtml(), JgetEntity.class);
        log.debug(je.getMsg());
        return je.getStat()>0;
    }

    public Boolean updateJobStat(String server, String job, DownloadInfo.States stat, String msg, String usr) {
        Map<String, String> header = new HashMap<>();
        Map<String, String> paras = new HashMap<>();
        header.put("Cookie", usr);
        paras.put("job", job);
        paras.put("stat", stat.toString());
        HttpEntity he = HttpKit.get(server + "/post/job/update_stat", paras, header);
        JgetEntity je = JsonKit.fromJson(he.getHtml(), JgetEntity.class);
        log.debug(je.getMsg());
        return je.getStat()>0;
    }


    public Boolean updateJobStat(String server, String job, DownloadInfo.States stat, String usr) {
        return this.updateJobStat(server, job, stat, null, usr);
    }

    public Boolean completeDownload(String server, String job, String usr) {
        return this.updateJobStat(server, job, DownloadInfo.States.DONE, usr);
    }

    public Boolean errorDownload(String server, String job, String msg, String usr) {
        return this.updateJobStat(server, job, DownloadInfo.States.ERROR, msg, usr);
    }

    public Boolean updateJobSize(String server, String job, Long size, String usr) {
        Map<String, String> header = new HashMap<>();
        Map<String, String> paras = new HashMap<>();
        header.put("Cookie", usr);
        paras.put("job", job);
        paras.put("size", size.toString());
        HttpEntity he = HttpKit.get(server + "/post/job/update_job_size", paras, header);
        JgetEntity je = JsonKit.fromJson(he.getHtml(), JgetEntity.class);
        log.debug(je.getMsg());
        return je.getStat()>0;
    }

}
