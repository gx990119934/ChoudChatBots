package cloud.chat.bots.objects.replykeyboard.buttons;

import cloud.chat.bots.exceptions.BotApiValidationException;
import cloud.chat.bots.common.BotApiObject;
import cloud.chat.bots.common.Validable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author gx
 * This object represents a button of the inline keyboard
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class InlineKeyboardButton implements Validable, BotApiObject {

    private static final String TEXT_FIELD = "text";
    private static final String URL_FIELD = "url";
    private static final String CALLBACK_DATA_FIELD = "callback_data";

    /**
     * button text
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private String text;

    /**
     * Optional.
     * The HTTP or cc:// url to open when the button is pressed.
     * The link cc://user?id=<user_id> can be used to mention a user by ID without username,
     */
    @JsonProperty(URL_FIELD)
    private String url;

    /**
     * Optional data to send to bot in callback query when button is pressed, 1-64 bytes
     */
    @JsonProperty(CALLBACK_DATA_FIELD)
    private String callbackData;

    @Override
    public void validate() throws BotApiValidationException {
        if (text.isEmpty()) {
            throw new BotApiValidationException("Text parameter can't be empty", this);
        }
    }
}
