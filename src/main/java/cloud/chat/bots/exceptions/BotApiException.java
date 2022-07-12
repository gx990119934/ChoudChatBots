package cloud.chat.bots.exceptions;

/**
 * @author gx
 * Bot api exception base class
 */
public class BotApiException extends Exception {

    public BotApiException() {
        super();
    }

    public BotApiException(String message) {
        super(message);
    }

    public BotApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public BotApiException(Throwable cause) {
        super(cause);
    }

    protected BotApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
