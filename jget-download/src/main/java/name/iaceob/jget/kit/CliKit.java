package name.iaceob.jget.kit;

import com.jfinal.kit.PropKit;
import name.iaceob.jget.common.Const;

/**
 * Created by iaceob on 2015/9/20.
 */
public class CliKit {


    public static String getCliName() {
        return PropKit.use(Const.PROPFILE).get("jget.cli.name");
    }




}
