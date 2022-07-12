package cloud.chat.bots.cc;

import cloud.chat.bots.common.WebhookUtils;
import cloud.chat.bots.exceptions.BotApiRequestException;
import cloud.chat.bots.generics.LongPollingBot;

/**
 * @author gx
 */
public abstract class CcLongPollingBot extends DefaultExeRequest implements LongPollingBot {

    public CcLongPollingBot() {
        this(new DefaultBotOptions());
    }

    public CcLongPollingBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public void onClosing() {
        exe.shutdown();
    }

    @Override
    public void clearWebhook() throws BotApiRequestException {
        WebhookUtils.clearWebhook(this);
    }
}
