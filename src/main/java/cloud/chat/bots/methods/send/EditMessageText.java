package cloud.chat.bots.methods.send;

import cloud.chat.bots.exceptions.BotApiRequestException;
import cloud.chat.bots.exceptions.BotApiValidationException;
import cloud.chat.bots.methods.BotApiMethod;
import cloud.chat.bots.objects.Message;
import cloud.chat.bots.objects.replykeyboard.InlineKeyboardMarkup;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 编辑消息
 *
 * @author gx
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditMessageText extends BotApiMethod<Message> {
    public static final String PATH = "editMessageText";

    private static final String CHATID_FIELD = "chat_id";
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String CHATTYPE_FIELD = "chat_type";
    private static final String TEXT_FIELD = "text";
    private static final String REPLYTOMESSAGEID_FIELD = "reply_markup";

    /**
     * (可选)聊天室发送消息的唯一标识符（或频道的用户名）
     */
    @JsonProperty(CHATID_FIELD)
    @NonNull
    private Long chatId;

    /**
     * 要编辑的消息的标识符
     */
    @JsonProperty(MESSAGEID_FIELD)
    private Long messageId;

    /**
     * 键入目标聊天
     * 2:用户到用户，4：群组或频道
     */
    @JsonProperty(CHATTYPE_FIELD)
    private Integer chatType;

    /**
     * 发送的文本内容
     */
    @JsonProperty(TEXT_FIELD)
    @NonNull
    private String text;

    /**
     * (可选)用于内联键盘的 JSON 序列化对象
     */
    @JsonProperty(REPLYTOMESSAGEID_FIELD)
    private InlineKeyboardMarkup replyMarkup;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws BotApiValidationException {
        if (chatId == null || chatId == 0) {
            throw new BotApiValidationException("ChatId parameter can't be empty", this);
        }
        if (text.isEmpty()) {
            throw new BotApiValidationException("Text parameter can't be empty", this);
        }
    }

    @Override
    public Message deserializeResponse(String answer) throws BotApiRequestException {
        return deserializeResponse(answer, Message.class);
    }

}
