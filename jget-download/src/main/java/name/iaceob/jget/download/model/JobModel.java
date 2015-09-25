package name.iaceob.jget.download.model;

import com.jfinal.plugin.activerecord.Record;
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
        return null;
    }


}
