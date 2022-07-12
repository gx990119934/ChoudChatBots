package cloud.chat.bots.common;

import cloud.chat.bots.exceptions.BotApiException;
import cloud.chat.bots.generics.*;
import cloud.chat.bots.methods.updates.SetWebhook;
import com.google.common.base.Strings;

import java.lang.reflect.InvocationTargetException;

/**
 * 机器人管理器
 *
 * @author gx
 */
public class BotsApi {

    Class<? extends BotSession> botSessionClass;
    /**
     * True以启用webhook使用
     */
    private boolean useWebhook;
    private Webhook webhook;

    public BotsApi(Class<? extends BotSession> botSessionClass) throws BotApiException {
        if (botSessionClass == null) {
            throw new BotApiException("Parameter botSessionClass can not be null or empty");
        }
        this.botSessionClass = botSessionClass;
    }

    /**
     * 创建HTTP服务器以接收webhook请求
     *
     * @param webhook webhook类
     * @implSpec此选项可能需要外部处理的HTTPS支持（即通过代理）
     */
    public BotsApi(Class<? extends BotSession> botSessionClass, Webhook webhook) throws BotApiException {
        if (botSessionClass == null) {
            throw new BotApiException("Parameter botSessionClass can not be null or empty");
        }
        if (webhook == null) {
            throw new BotApiException("Parameter webhook can not be null or empty");
        }
        this.botSessionClass = botSessionClass;
        this.useWebhook = true;
        this.webhook = webhook;
        this.webhook.startServer();
    }

    /**
     * 注册机器人。Bot会话立即启动，可以通过调用close断开连接
     *
     * @param bot 要注册的bot
     */
    public BotSession registerBot(LongPollingBot bot) throws BotApiException {
        if (bot == null) {
            throw new BotApiException("Parameter bot can not be null");
        }
        if (!validateBotUsernameAndToken(bot)) {
            throw new BotApiException("Bot token and username can't be empty");
        }
        bot.onRegister();
        bot.clearWebhook();
        BotSession session;
        try {
            session = botSessionClass.getConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new BotApiException(e);
        }
        session.setToken(bot.getBotToken());
        session.setOptions(bot.getOptions());
        session.setCallback(bot);
        session.start();
        return session;
    }

    /**
     * 在api中注册一个机器人，该机器人将使用webhook方法接收更新
     *
     * @param bot        Bot to register
     * @param setWebhook Set webhook request to initialize the bot
     * @apiNote The webhook url will be appended with `/callback/bot.getBotPath()` at the end
     */
    public void registerBot(WebhookBot bot, SetWebhook setWebhook) throws BotApiException {
        if (setWebhook == null) {
            throw new BotApiException("Parameter setWebhook can not be null");
        }
        if (useWebhook) {
            if (webhook == null) {
                throw new BotApiException("This instance doesn't support Webhook bot, use correct constructor");
            }
            if (!validateBotUsernameAndToken(bot)) {
                throw new BotApiException("Bot token and username can't be empty");
            }
            bot.onRegister();
            webhook.registerWebhook(bot);
            bot.setWebhook(setWebhook);
        }
    }

    /**
     * 检查是否显示用户名和令牌
     *
     * @param bot bot to register
     * @return 如果用户名或令牌为空或null，则为False，否则为true
     */
    private boolean validateBotUsernameAndToken(Bot bot) {
        return !Strings.isNullOrEmpty(bot.getBotToken()) &&
                !Strings.isNullOrEmpty(bot.getBotUsername());
    }
}
