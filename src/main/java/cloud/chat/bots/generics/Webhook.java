package cloud.chat.bots.generics;


import cloud.chat.bots.exceptions.BotApiException;

/**
 * @author gx
 */
public interface Webhook {

    void startServer() throws BotApiException;

    void registerWebhook(WebhookBot callback);

    void setInternalUrl(String internalUrl);

    void setKeyStore(String keyStore, String keyStorePassword) throws BotApiException;
}
