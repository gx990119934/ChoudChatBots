package cloud.chat.utils;

import java.util.Random;

public class IconUtil {

    //添加群组时赋的符号
    private final static String ADD_GROUP_CHANNEL = "\uD83C\uDF10,\uD83D\uDCE2,\uD83C\uDFC6,\uD83D\uDC65,\uD83D\uDFE9,\uD83D\uDD25";

    /**
     * 随机获取两个符号
     * @return
     */
    public static String getAddGroupChannelIcon(){
        String[] strs = ADD_GROUP_CHANNEL.split(",");
        Random random=new Random();
        int one = random.nextInt(strs.length);
        int two = random.nextInt(strs.length);
        return strs[one]+strs[two];
    }
}
