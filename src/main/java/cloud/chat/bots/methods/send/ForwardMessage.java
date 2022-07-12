package cloud.chat.bots.methods.send;

import cloud.chat.bots.exceptions.BotApiRequestException;
import cloud.chat.bots.exceptions.BotApiValidationException;
import cloud.chat.bots.methods.BotApiMethod;
import cloud.chat.bots.objects.Message;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 转发消息
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
public class ForwardMessage extends BotApiMethod<Message> {
    public static final String PATH = "forwardMessage";

    private static final String CHATID_FIELD = "chat_id";
    private static final String FROMCHATID_FIELD = "from_chat_id";
    private static final String MESSAGEID_FIELD = "message_id";

    /**
     * 聊天室发送消息的唯一标识符（或频道的用户名）
     */
    @JsonProperty(CHATID_FIELD)
    @NonNull
    private Long chatId;

    /**
     * 消息来自的chatId
     */
    @JsonProperty(FROMCHATID_FIELD)
    private Long fromChatId;

    /**
     * 消息Id
     */
    @JsonProperty(MESSAGEID_FIELD)
    @NonNull
    private Long messageId;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws BotApiValidationException {
        if (chatId==null||chatId==0) {
            throw new BotApiValidationException("ChatId parameter can't be empty", this);
        }
    }

    @Override
    public Message deserializeResponse(String answer) throws BotApiRequestException {
        return deserializeResponse(answer, Message.class);
    }

}
