package cloud.chat.bots.methods.send;

import cloud.chat.bots.exceptions.BotApiRequestException;
import cloud.chat.bots.methods.BotApiMethod;
import cloud.chat.bots.objects.ChatMember;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;

/**
 * @author gx
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
public class GetChatAdministrators extends BotApiMethod<ArrayList<ChatMember>> {
    public static final String PATH = "getChatAdministrators";

    private static final String CHATID_FIELD = "chat_id";

    /**
     * 聊天室发送消息的唯一标识符（或频道的用户名）
     */
    @JsonProperty(CHATID_FIELD)
    @NonNull
    private Long chatId;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public ArrayList<ChatMember> deserializeResponse(String result) throws BotApiRequestException {
        return deserializeResponseArray(result, ChatMember.class);
    }
}
