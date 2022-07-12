package cloud.chat.bots.exceptions;

import cloud.chat.bots.common.BotApiObject;
import cloud.chat.bots.methods.BotApiMethod;

/**
 * @author gx
 */
public class BotApiValidationException extends BotApiException {

    private BotApiMethod method;
    private BotApiObject object;

    public BotApiValidationException(String message, BotApiMethod method) {
        super(message);
        this.method = method;
    }

    public BotApiValidationException(String message, BotApiObject object) {
        super(message);
        this.object = object;
    }

    public BotApiObject getObject() {
        return object;
    }

    public BotApiMethod getMethod() {
        return method;
    }

    @Override
    public String toString() {
        if (method != null) {
            return super.toString() + " in method: " + method.toString();
        }
        if (object != null) {
            return super.toString() + " in object: " + object.toString();
        }
        return super.toString();
    }
}
