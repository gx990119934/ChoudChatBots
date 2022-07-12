package cloud.chat.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * cc消息处理
 *
 * @author gx
 */
public class MsgUtil {

    public static ConcurrentHashMap<Long, Boolean> msgForwardMap = new ConcurrentHashMap();

    public static void setForard(Long chatId) {
        msgForwardMap.put(chatId, true);
    }

    public static boolean getForward(Long chatId) {
        boolean result = false;
        try {
            result = msgForwardMap.get(chatId);
        } catch (Exception e) {
            result = false;
        }
        msgForwardMap.remove(chatId);
        return result;
    }

    /**
     * 普通换行
     * @param sb
     * @return
     */
    public static StringBuffer nextLine(StringBuffer sb) {
        return sb.append("\n");
    }

    public static String nextLine(String str) {
        return str+"\n";
    }

    /**
     * mkdown换行
     * @param sb
     * @return
     */
    public static StringBuffer nextLineMD(StringBuffer sb) {
        return sb.append("\\\\n");
    }

    public static String nextLineMD(String str) {
        return str+"\\\\n";
    }

    /**
     * 获取mkdown的链接
     * @return
     */
    public static String mkUrl(String name,String url) {
        return String.format("[%s](%s)",name,url);
    }

}
