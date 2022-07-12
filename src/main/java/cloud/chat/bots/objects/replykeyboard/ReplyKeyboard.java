package cloud.chat.bots.objects.replykeyboard;

import cloud.chat.bots.common.BotApiObject;
import cloud.chat.bots.common.Validable;
import cloud.chat.bots.objects.replykeyboard.serialization.KeyboardDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author gx
 * keyboard abstract type
 */
@JsonDeserialize(using = KeyboardDeserializer.class)
public interface ReplyKeyboard extends BotApiObject, Validable {
}
