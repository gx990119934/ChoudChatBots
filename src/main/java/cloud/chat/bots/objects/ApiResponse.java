package cloud.chat.bots.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author gx
 * server response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {

    private static final String CODE_FIELD = "code";
    private static final String RESULT_FIELD = "result";

    @JsonProperty(CODE_FIELD)
    private Integer code;

    @JsonProperty(RESULT_FIELD)
    private T result;

    public Boolean getOk() {
        return code == 0;
    }

    public Integer getErrorCode() {
        return code;
    }

    public String getErrorDescription() {
        if (getOk()) {
            return "request succeeded";
        } else {
            return result.toString();
        }
    }

    public T getResult() {
        return result;
    }

    @Override
    public String toString() {
        if (getOk()) {
            return "ApiResponse{" +
                    "code=" + code +
                    ", result=" + result +
                    '}';
        } else {
            return "ApiResponse{" +
                    ", errorCode=" + code +
                    ", errorDescription='" + getErrorDescription() + '\'' +
                    '}';
        }
    }
}
