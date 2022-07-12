package cloud.chat.common;

/**
 * @author gx
 * Bot api constants
 */
public class ApiConstants {

    /**
     * cc服务器相关信息
     */
    public static final String BASE_URL = "https://api.cloudchat.com/";

    public static final String BASE_USERNAME_URL = "https://cccc.tel/";

    /**
     * cc的http相关信息
     */
    public static final int GETUPDATES_TIMEOUT = 50;

    public static final int SOCKET_TIMEOUT = 75 * 1000;

    public static final String WEBHOOK_URL_PATH = "callback";

    /**
     * 命令
     */
    public static final String CMD_START = "/start";

    public static final String CALLBACK_QUERY_JOIN_US = "join_us";

    //索引机器人每页数量
    public static final Integer QUERY_PRIVATE_PAGE_SIZE = 1;

    public static final Integer QUERY_GROUP_CHANNEL_PAGE_SIZE = 15;

    /**
     * callBack相关信息
     */
    //上一页
    public static final String CALLBACK_QUERY_PRE_PAGE = "pre_page_";
    //下一页
    public static final String CALLBACK_QUERY_NEXT_PAGE = "next_page_";
    //规则踢人
    public static final String CALLBACK_RULE_KILL_MEMBER = "rule_kill_member";
    //我的群组和频道
    public static final String CALLBACK_MY_GROUP_CHANNEL = "my_group_channel";
    //群组和频道消息转发
    public static final String CALLBACK_GROUP_CHANNEL_FORWARD = "group_channel_forward";


    /**
     * cc相关账号信息
     */
    //客服账户名
    public static final String CHAT_CUSTOMER_SERVICE = "sads";
    //客服url
    public static final String CHAT_CUSTOMER_SERVICE_URL =BASE_USERNAME_URL+CHAT_CUSTOMER_SERVICE;
    //首条广告账户
    public static final String FIRST_ADVERTISE_DESCRIPTION ="[广告]\uD83D\uDCE2群管理机器人正式上线\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25，欢迎添加@newsBot机器人\uD83D\uDC65";
    //首条广告url
    public static final String FIRST_ADVERTISE_URL ="";

}
