package cloud.chat.bots.exceptions;

import cloud.chat.bots.objects.ApiResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;


/**
 * @author gx
 */
@Slf4j
public class BotApiRequestException extends BotApiException {

    private static final String ERRORDESCRIPTIONFIELD = "description";
    private static final String ERRORCODEFIELD = "error_code";

    private String apiResponse = null;
    private Integer errorCode = 0;

    public BotApiRequestException(String message) {
        super(message);
    }

    @SneakyThrows
    public BotApiRequestException(String message, JSONObject object) {
        super(message);
        apiResponse = object.getString(ERRORDESCRIPTIONFIELD);
        errorCode = object.getInt(ERRORCODEFIELD);
    }

    public BotApiRequestException(String message, ApiResponse response) {
        super(message);
        apiResponse = response.getErrorDescription();
        errorCode = response.getErrorCode();
    }

    public BotApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        if (apiResponse == null) {
            return super.getMessage();
        } else if (errorCode == null) {
            return super.getMessage() + ": " + apiResponse;
        } else {
            return super.getMessage() + ": [" + errorCode + "] " + apiResponse;
        }
    }

    @Override
    public String toString() {
        return getMessage();
    }
    
}
