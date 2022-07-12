package cloud.chat.bots.methods;

import cloud.chat.bots.exceptions.BotApiRequestException;
import cloud.chat.bots.common.Validable;
import cloud.chat.bots.objects.ApiResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author gx
 * bot request method base class
 * Get the request path
 * Request result deserialization
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BotApiMethod<T extends Serializable> implements Validable {

    @JsonIgnore
    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    protected static final String METHOD_FIELD = "method";

    @JsonProperty(METHOD_FIELD)
    public abstract String getMethod();

    public abstract T deserializeResponse(String result) throws BotApiRequestException;

    public T deserializeResponse(String result, Class<T> returnClass) throws BotApiRequestException {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructType(returnClass);
        return deserializeResponseInternal(result, type);
    }

    public <K extends Serializable> T deserializeResponseArray(String result, Class<K> returnClass) throws BotApiRequestException {
        CollectionType collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, returnClass);
        return deserializeResponseInternal(result, collectionType);
    }

    protected <K extends Serializable> T deserializeResponseSerializable(String result, Class<K> returnClass) throws BotApiRequestException {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructType(returnClass);
        return deserializeResponseInternal(result, type);
    }

    private T deserializeResponseInternal(String result, JavaType type) throws BotApiRequestException {
        try {
            JavaType responseType = OBJECT_MAPPER.getTypeFactory().constructParametricType(ApiResponse.class, type);
            ApiResponse<T> response = OBJECT_MAPPER.readValue(result, responseType);
            if (response.getOk()) {
                return response.getResult();
            } else {
                throw new BotApiRequestException(String.format("Request %s method failed", this.getClass().getName()), response);
            }
        } catch (IOException e) {
            throw new BotApiRequestException("Unable to deserialize response", e);
        }
    }
}
