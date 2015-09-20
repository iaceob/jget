package name.iaceob.wget.common;


/**
 * Created by iaceob on 2015/9/21.
 */
public enum Method {
    POST("POST"),
    GET("GET"),
    PUT("PUT"),
    DELETE("DELETE");
    private final String name;
    private Method(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
