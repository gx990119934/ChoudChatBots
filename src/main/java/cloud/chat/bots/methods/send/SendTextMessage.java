package cloud.chat.bots.methods.send;

import cloud.chat.bots.exceptions.BotApiRequestException;
import cloud.chat.bots.exceptions.BotApiValidationException;
import cloud.chat.bots.methods.BotApiMethod;
import cloud.chat.bots.objects.Message;
import cloud.chat.bots.objects.ParseMode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 发送文本消息
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
public class SendTextMessage extends BotApiMethod<Message> {
    public static final String PATH = "sendTextMessage";

    private static final String CHATID_FIELD = "chat_id";
    private static final String CHATTYPE_FIELD = "chat_type";
    private static final String TEXT_FIELD = "text";
    private static final String PARSEMODE_FIELD = "parse_mode";
    private static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";

    /**
     * 聊天室发送消息的唯一标识符（或频道的用户名）
     */
    @JsonProperty(CHATID_FIELD)
    @NonNull
    private Long chatId;

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
     * (可选)解析消息文本中实体的模式
     */
    @JsonProperty(PARSEMODE_FIELD)
    private String parseMode;

    /**
     * (可选)如果消息是回复，则原始消息的ID
     */
    @JsonProperty(REPLYTOMESSAGEID_FIELD)
    private Integer replyToMessageId;

    public void enableMarkdown(boolean enable) {
        if (enable) {
            this.parseMode = ParseMode.MARKDOWN;
        } else {
            this.parseMode = null;
        }
    }

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws BotApiValidationException {
        if (chatId==null||chatId==0) {
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
