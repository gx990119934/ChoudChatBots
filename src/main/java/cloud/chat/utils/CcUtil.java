package cloud.chat.utils;

import cloud.chat.common.ApiConstants;

/**
 * cc账户相关的工具类
 * @author gx
 */
public class CcUtil {

    /**
     * 通过账户名获取url
     * @param userName
     * @return
     */
    public static String getUrl(String userName){
        return ApiConstants.BASE_USERNAME_URL+userName;
    }

    /**
     * 通过url用户用户名
     * @param url
     * @return
     */
    public static String getUserName(String url){
        return url.replace(ApiConstants.BASE_USERNAME_URL,"");
    }

    /**
     * 是否有人@我
     */
    public static boolean assignMe(String text,String userName){
        return text.contains(userName);
    }

    /**
     * 获取去除@名称的内容
     * @param text
     * @param userName
     * @return
     */
    public static String getTextExcludeAssign(String text,String userName){
        return text.replace("@"+userName+" ","");
    }

}
