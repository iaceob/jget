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

    private static String seedCrypt(String str, String seed) {
        // String seed = ConfModel.dao.getValToStr("conf.account.passwd_seed");
        String rc4e = Disgest.encodeRC4(str, seed);
        String md5e = Disgest.encodeMD5(rc4e);
        return md5e;
    }

    public static String encrypt(String str, String seed) {
        String bcre = BCrypt.hashpw(seedCrypt(str, seed), BCrypt.gensalt());
        log.debug("Encrypt: " + bcre);
        return bcre;
    }

    public static String encrypt(String str) {
        return encrypt(str, Const.PASSWDSEED);
    }

    public static Boolean checkpwd(String src, String seed, String hashed) {
        String pwd = seedCrypt(src, seed);
        log.debug("pwd:" + pwd);
        return BCrypt.checkpw(pwd, hashed);
    }

    public static Boolean checkpwd(String src, String hashed) {
        return checkpwd(src, Const.PASSWDSEED, hashed);
    }


}
