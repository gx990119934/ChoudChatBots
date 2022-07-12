package cloud.chat.bots.objects.replykeyboard;

import cloud.chat.bots.exceptions.BotApiValidationException;
import cloud.chat.bots.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.List;

/**
 * @author gx
 * This object represents the inline keyboard that appears next to its message
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InlineKeyboardMarkup implements ReplyKeyboard {

    private static final String KEYBOARD_FIELD = "inline_keyboard";

    /**
     * Array of button rows, each row represented by an array of strings
     */
    @JsonProperty(KEYBOARD_FIELD)
    @NonNull
    @Singular(value = "keyboardRow")
    private List<List<InlineKeyboardButton>> keyboard;

    @Override
    public void validate() throws BotApiValidationException {
        if (keyboard == null) {
            throw new BotApiValidationException("Keyboard parameter can't be null", this);
        }
        for (List<InlineKeyboardButton> inlineKeyboardButtons : keyboard) {
            for (InlineKeyboardButton inlineKeyboardButton : inlineKeyboardButtons) {
                inlineKeyboardButton.validate();
            }
        }
    }
}
