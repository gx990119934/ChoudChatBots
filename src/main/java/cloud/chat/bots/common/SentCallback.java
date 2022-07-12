package cloud.chat.bots.common;

import cloud.chat.bots.exceptions.BotApiRequestException;
import cloud.chat.bots.methods.BotApiMethod;

import java.io.Serializable;

/**
 * @author gx
 * Asynchronously execute the callback of the api method
 */
public interface SentCallback<T extends Serializable> {

    /**
     * Called when the request is successful
     * @param method method of execution
     * @param response Answer from server
     */
    void onResult(BotApiMethod<T> method, T response);

    /**
     * Called when the request fails
     * @param method
     * @param apiException Reply from server (with error message)
     */
    void onError(BotApiMethod<T> method, BotApiRequestException apiException);

    /**
     * Called when the http request throws an exception
     * @param method
     * @param exception
     */
    void onException(BotApiMethod<T> method, Exception exception);
}
