package cloud.chat.bots.objects;

import cloud.chat.bots.common.BotApiObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author gx
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CallbackQuery implements BotApiObject {

    private static final String ID_FIELD = "id";
    private static final String MESSAGE_FIELD = "message";
    private static final String DATA_FIELD = "data";

    /**
     * Unique identifier for this query
     */
    @JsonProperty(ID_FIELD)
    private String id;

    /**
     * Optional.
     * Message with the callback button that originated the query.
     * @apiNote  The message content and message date will not be available if the message is too old
     */
    @JsonProperty(MESSAGE_FIELD)
    private Message message;

    /**
     * Optional. Data associated with the callback button.
     * @apiNote  Be aware that a bad client can send arbitrary data in this field
     */
    @JsonProperty(DATA_FIELD)
    private String data;
}
