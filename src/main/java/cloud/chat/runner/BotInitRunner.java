package cloud.chat.runner;

import cloud.chat.bots.cc.CcLongPollingBot;
import cloud.chat.bots.cc.DefaultBotSession;
import cloud.chat.bots.common.BotsApi;
import cloud.chat.bots.exceptions.BotApiException;
import cloud.chat.common.SpringContext;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author gx
 */
@Component
@Order(1)
public class BotInitRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CcLongPollingBot superIndexBotCc = null;
        CcLongPollingBot groupManagerBotCc = null;

        try {
            superIndexBotCc = (CcLongPollingBot) SpringContext.getBean("superIndexBotCc");
        } catch (Exception e) {
            superIndexBotCc = null;
        }
        try {
            groupManagerBotCc = (CcLongPollingBot) SpringContext.getBean("groupManagerBotCc");
        } catch (Exception e) {
            groupManagerBotCc = null;
        }

        try {
            BotsApi botsApi = new BotsApi(DefaultBotSession.class);
            if (superIndexBotCc != null) {
                botsApi.registerBot(superIndexBotCc);
            }
            if (groupManagerBotCc != null) {
                botsApi.registerBot(groupManagerBotCc);
            }
        } catch (BotApiException e) {
            e.printStackTrace();
        }
    }

}
