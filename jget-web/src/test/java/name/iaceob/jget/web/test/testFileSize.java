package name.iaceob.jget.web.test;

import com.jfinal.kit.FileKit;
import org.junit.Test;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by iaceob on 2015/9/23.
 */
public class testFileSize {


    @Test
    public void tfs() throws Exception{
        int size;
        URL url = new URL("http://120.192.87.62/ws.cdn.baidupcs.com/file/534314ec312e8407cfdc6ef1ff21b804?bkt=p2-qd-832&xcode=5223d56f4ddf3990ef43fdda69e859062b13df7c4dd4252eed03e924080ece4b&fid=4130254330-250528-988707842402964&time=1443015804&sign=FDTAXGERLBH-DCb740ccc5511e5e8fedcff06b081203-i0W1R8hOiYHbR2Pzfr6FaW0HbJE%3D&to=hc&fm=Nan,B,M,mn&sta_dx=601&sta_cs=376&sta_ft=iso&sta_ct=6&fm2=Nanjing,B,M,mn&newver=1&newfm=1&secfm=1&flow_ver=3&pkey=1400534314ec312e8407cfdc6ef1ff21b804ffffffff00002590b000&sl=68485198&expires=8h&rt=pr&r=697708141&mlogid=6167090979138043960&vuk=4130254330&vbdid=3421910568&fin=xpx86.iso&fn=xpx86.iso&slt=pm&uta=0&rtype=1&iv=0&isw=0&dp-logid=6167090979138043960&dp-callid=0.1.1&wshc_tag=0&wsts_tag=5602ac81&wsid_tag=6444014c&wsiphost=ipdbm");
        URLConnection conn = url.openConnection();
        size = conn.getContentLength();
        if (size < 0)
            System.out.println("无法获取文件大小。");
        else
            System.out.println("文件大小为：" +
                    + size + " bytes");
        conn.getInputStream().close();
    }

}
