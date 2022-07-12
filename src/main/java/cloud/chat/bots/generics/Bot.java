package cloud.chat.bots.generics;

/**
 * 机器人的主入口
 */
public interface Bot {

    /**
     * 返回机器人用户名
     */
    String getBotUsername();

    /**
     * 返回机器人名称
     */
    String getBotName();

    /**
     *返回机器人的token
     */
    String getBotToken();

    /**
     * 机器人注册时调用
     */
    default void onRegister() {
    }

}
