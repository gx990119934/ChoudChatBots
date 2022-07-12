package cloud.chat.bots.methods.send;

import cloud.chat.bots.exceptions.BotApiRequestException;
import cloud.chat.bots.methods.BotApiMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
public class GetChatMembersCount extends BotApiMethod<Integer> {

    public static final String PATH = "getChatMembersCount";

    private static final String CHATID_FIELD = "chat_id";

    /**
     * 群组或频道的用户名
     */
    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Integer deserializeResponse(String result) throws BotApiRequestException {
        return deserializeResponse(result, Integer.class);
    }
}
