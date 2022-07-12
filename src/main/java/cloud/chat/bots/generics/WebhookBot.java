package cloud.chat.bots.generics;

import cloud.chat.bots.exceptions.BotApiException;
import cloud.chat.bots.methods.BotApiMethod;
import cloud.chat.bots.methods.updates.SetWebhook;
import cloud.chat.bots.objects.Update;

/**
 * @author gx
 */
public interface WebhookBot extends Bot {
    /**
     * This method is called when an update is received via the webhook
     *
     * @param update Update received
     */
    BotApiMethod<?> onWebhookUpdateReceived(Update update);

    /**
     * Execute the setWebhook method to set the url of the webhook
     */
    void setWebhook(SetWebhook setWebhook) throws BotApiException;

    /**
     * Get the url of the webhook
     */
    String getBotPath();
}
