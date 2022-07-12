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
@RequiredArgsConstructor
@NoArgsConstructor
public class ChatMember implements BotApiObject {

    private static final String USER_FIELD = "user";
    private static final String STATUS_FIELD = "status";

    @JsonProperty(USER_FIELD)
    @NonNull
    private User user;

    /**
     * 群之返回创建者：creator
     */
    @JsonProperty(STATUS_FIELD)
    @NonNull
    private String status;

}
