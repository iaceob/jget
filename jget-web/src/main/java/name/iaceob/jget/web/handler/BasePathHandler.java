package name.iaceob.jget.web.handler;

import com.jfinal.handler.Handler;
import com.jfinal.kit.StrKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BasePathHandler extends Handler {

    private String baseName = "basePath";
    private String resName = "resPath";
    private String resPath = "/resources";
    
    public BasePathHandler() { }
    
    public BasePathHandler(String resName, String resPath) {
        if (StrKit.isBlank(resName) || StrKit.isBlank(resPath)) return;
        this.resName = resName;
        this.resPath = resPath;
    }
    

    public BasePathHandler(String baseName, String resName, String resPath) {
        if (StrKit.isBlank(baseName) || StrKit.isBlank(resPath) || StrKit.isBlank(resName)) return;
        this.baseName = baseName;
        this.resName = resName;
        this.resPath = resPath;
    }
    
    public void handle(String target, HttpServletRequest request,
            HttpServletResponse response, boolean[] isHandled) {
        request.setAttribute(this.baseName, request.getContextPath());
        request.setAttribute(this.resName, request.getContextPath() + this.resPath);
        super.nextHandler.handle(target, request, response, isHandled);
    }

}