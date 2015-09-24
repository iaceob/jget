package name.iaceob.jget.web.kit;

import name.iaceob.jget.web.common.Const;
import name.iaceob.jget.web.kit.disgest.Disgest;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cox on 2015/9/24.
 */
public class PasswdKit {

    private static final Logger log = LoggerFactory.getLogger(PasswdKit.class);

    private static String seedCrypt(String str) {
        // String seed = ConfModel.dao.getValToStr("conf.account.passwd_seed");
        String rc4e = Disgest.encodeRC4(str, Const.PASSWDSEED);
        String md5e = Disgest.encodeMD5(rc4e);
        return md5e;
    }

    public static String encrypt(String str) {
        String bcre = BCrypt.hashpw(seedCrypt(str), BCrypt.gensalt());
        log.debug("Encrypt: " + bcre);
        return bcre;
    }

    public static Boolean checkpwd(String src, String hashed) {
        String pwd = seedCrypt(src);
        log.debug("pwd:" + pwd);
        return BCrypt.checkpw(pwd, hashed);
    }


}
