package io.github.forezp.distributedlimitcore.constant;

/**
 * Email miles02@163.com
 *
 * @author fangzhipeng
 * create 2018-06-26
 **/
public class ConfigConstant {

    public static String LIMIT_TYPE = "limit.type";
    public static String LIMIT_TYPE_REDIS = "redis";
    public static String LIMIT_TYPE_LOCAL = "local";

    public static String LIMIT_FILTER="limit.filter";
    public static String LIMIT_FILTER_TRUE="true";


    public static final String HTTPCLIENT_CONNCT_TIMEOUT = "httpclient.connect.timeout";
    public static final String HTTPCLIENT_CONNCT_TIMEOUT_DEFAULT = "5000";
    public static final String HTTPCLIENT_CONNCT_REQUEST_TIMEOUT = "httpclient.connect.request.timeout";
    public static final String HTTPCLIENT_CONNCT_REQUEST_TIMEOUT_DEFAULT = "5000";
    public static final String HTTPCLIENT_SOCKET_TIMEOUT = "httpclient.socket.timeout";
    public static final String HTTPCLIENT_SOCKET_TIMEOUT_DEFAULT = "5000";
    public static final String HTTPCLIENT_SEDBUFSIZE = "httpclient.send.bufsize";
    public static final String HTTPCLIENT_SEDBUFSIZE_DEFAULT = "65536";
    public static final String HTTPCLIENT_RCV_BUFSIZE = "httpclient.rcv.bufsize";
    public static final String HTTPCLIENT_RCV_BUFSIZE_DEFAULT = "65536";
    public static final String HTTPCLIENT_BACK_LOG_SIZE = "httpclient.back.logszie";
    public static final String HTTPCLIENT_BACK_LOG_SIZE_DEFAULT = "128";
    public static final String HTTPCLIENT_MAX_TOTAL = "httpclient.max.total";
    public static final String HTTPCLIENT_MAX_TOTAL_DEFAULT = "64";
}
