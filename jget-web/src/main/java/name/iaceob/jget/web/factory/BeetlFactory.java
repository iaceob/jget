package name.iaceob.jget.web.factory;

import com.jfinal.render.Render;
import org.beetl.ext.jfinal.BeetlRender;
import org.beetl.ext.jfinal.BeetlRenderFactory;

/**
 * Created by cox on 2015/8/17.
 */
public class BeetlFactory extends BeetlRenderFactory {

    public Render getRender(String view) {
        BeetlRender render = new BeetlRender(super.groupTemplate, view);
        return render;
    }

    public String getViewExtension() {
        return ".html";
    }

}