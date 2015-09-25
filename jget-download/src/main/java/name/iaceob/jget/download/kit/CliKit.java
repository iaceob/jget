package name.iaceob.jget.download.kit;

/**
 * Created by iaceob on 2015/9/20.
 */
public class CliKit {


    private static String cliName;
    private static String ip;
    private static String cliId;
    private static String server;
    private static String usr;

    public static void setCliName(String cliName) {
        CliKit.cliName = cliName;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        CliKit.ip = ip;
    }

    public static String getCliName() {
        return CliKit.cliName;
    }

    public static String getCliId() {
        return cliId;
    }

    public static String getServer() {
        return server;
    }

    public static void setServer(String server) {
        CliKit.server = server;
    }

    public static void setCliId(String cliId) {
        CliKit.cliId = cliId;
    }

    public static String getUsr() {
        return usr;
    }

    public static void setUsr(String usr) {
        CliKit.usr = usr;
    }


}
