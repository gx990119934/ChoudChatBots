package cloud.chat.bots.common;


import cloud.chat.bots.exceptions.BotApiException;
import cloud.chat.bots.methods.BotApiMethod;
import cloud.chat.bots.methods.GetMe;
import cloud.chat.bots.methods.updates.GetWebhookInfo;
import cloud.chat.bots.objects.User;
import cloud.chat.bots.objects.WebhookInfo;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * @author gx
 * execute send request
 */
public abstract class ExeRequest {

    protected ExeRequest() {
    }

    public final User getMe() throws BotApiException {
        return sendApiMethod(new GetMe());
    }

    public final WebhookInfo getWebhookInfo() throws BotApiException {
        GetWebhookInfo getWebhookInfo = new GetWebhookInfo();
        return sendApiMethod(getWebhookInfo);
    }

    public final CompletableFuture<User> getMeAsync() {
        return sendApiMethodAsync(new GetMe());
    }

    public final CompletableFuture<WebhookInfo> getWebhookInfoAsync() {
        return sendApiMethodAsync(new GetWebhookInfo());
    }

    public final void getMeAsync(SentCallback<User> sentCallback) throws BotApiException {
        if (sentCallback == null) {
            throw new BotApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(new GetMe(), sentCallback);
    }

    public final void getWebhookInfoAsync(SentCallback<WebhookInfo> sentCallback) throws BotApiException {
        if (sentCallback == null) {
            throw new BotApiException("Parameter sentCallback can not be null");
        }
        sendApiMethodAsync(new GetWebhookInfo(), sentCallback);
    }

    public <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void executeAsync(Method method, Callback callback) throws BotApiException {
        if (method == null) {
            throw new BotApiException("Parameter method can not be null");
        }
        if (callback == null) {
            throw new BotApiException("Parameter callback can not be null");
        }
        sendApiMethodAsync(method, callback);
    }

    public <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> executeAsync(Method method) throws BotApiException {
        if (method == null) {
            throw new BotApiException("Parameter method can not be null");
        }
        return sendApiMethodAsync(method);
    }

    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws BotApiException {
        if (method == null) {
            throw new BotApiException("Parameter method can not be null");
        }
        return sendApiMethod(method);
    }

    protected abstract <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void sendApiMethodAsync(Method method, Callback callback);

    protected abstract <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> sendApiMethodAsync(Method method);

    protected abstract <T extends Serializable, Method extends BotApiMethod<T>> T sendApiMethod(Method method) throws BotApiException;
}
