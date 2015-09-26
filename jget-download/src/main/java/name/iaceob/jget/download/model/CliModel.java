package name.iaceob.jget.download.model;

import name.iaceob.jget.download.entity.JgetEntity;
import name.iaceob.jget.download.kit.JsonKit;
import name.iaceob.jget.download.kit.http.HttpEntity;
import name.iaceob.jget.download.kit.http.HttpKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iaceob on 2015/9/25.
 */
public class CliModel {


    private static final Logger log = LoggerFactory.getLogger(CliModel.class);

    public static final CliModel dao = new CliModel();

    public JgetEntity registerCli(String server, String ip, String name, String usr) {
        Map<String, String> header = new HashMap<>();
        header.put("Cookie", usr);
        String data = "ip=" + ip + "&name=" + name;
        HttpEntity he = HttpKit.post(server + "/post/cli/register", data, header);
        log.debug(he.getHtml().trim());
        JgetEntity je = JsonKit.fromJson(he.getHtml(), JgetEntity.class);
        log.debug(je.getMsg());
        return je;
    }

    public JgetEntity heartbeatCli(String server, String id, String ip, String usr) {
        Map<String, String> header = new HashMap<>();
        header.put("Cookie", usr);
        String data = "ip=" + ip + "&id=" + id;
        HttpEntity he = HttpKit.post(server + "/post/cli/heartbeat", data, header);
        log.debug(he.getHtml().trim());
        JgetEntity je = JsonKit.fromJson(he.getHtml(), JgetEntity.class);
        log.debug(je.getMsg());
        return je;
    }


}
