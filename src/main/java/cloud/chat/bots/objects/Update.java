package cloud.chat.bots.objects;

import cloud.chat.bots.common.BotApiObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author gx
 * This object represents the incoming update
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Update implements BotApiObject {

    private static final String UPDATEID_FIELD = "update_id";
    private static final String MESSAGE_FIELD = "message";
    private static final String CALLBACKQUERY_FIELD = "callback_query";

    @JsonProperty(UPDATEID_FIELD)
    private Integer updateId;

    /**
     * Optional. New incoming message of any kind — text, photo, sticker, etc
     */
    @JsonProperty(MESSAGE_FIELD)
    private Message message;

    /**
     * Optional. New incoming callback query
     */
    @JsonProperty(CALLBACKQUERY_FIELD)
    private CallbackQuery callbackQuery;

    public boolean hasMessage() {
        return message != null;
    }

    public boolean hasCallbackQuery() {
        return callbackQuery != null;
    }

}
