package cloud.chat.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class Result<T> {

    private static final int SUCCESS_CODE = 600;

    private int statusCode = SUCCESS_CODE;

    private String message;

    private String errorStackTrace;

    private boolean isSuccess = false;

    private T data;

    public Result(int statusCode, String message, T data) {
        setStatusCode(statusCode);
        setMessage(message);
        setData(data);
        isSuccess();
    }

    public Result(int statusCode, String message) {
        this(statusCode, message, null);
    }

    public Result(int statusCode) {
        this(statusCode, null);
    }

    public Result(T data) {
        this(SUCCESS_CODE, null, data);
    }

    public Result() {
        this(SUCCESS_CODE, null);
    }

    public Result(String message) {
        this(600, message);
    }

    @JsonIgnore
    public boolean isSuccess() {
        this.isSuccess = getStatusCode() == 600;
        return isSuccess;
    }

    public boolean hasData() {
        return getData() != null;
    }


    public static Result success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(600, "操作成功！", data);
    }
}
