package cloud.chat.bots.objects;

import cloud.chat.bots.common.BotApiObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author gx
 * This object represents a chat with a user or group
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Chat implements BotApiObject {

    private static final String ID_FIELD = "id";
    private static final String TYPE_FIELD = "type";
    private static final String TITLE_FIELD = "title";
    private static final String USERNAME_FIELD = "username";
    private static final String FIRSTNAME_FIELD = "first_name";
    private static final String LASTNAME_FIELD = "last_name";
    private static final String DESCRIPTION_FIELD = "description";

    private static final String USERCHATTYPE = "private";
    private static final String GROUPCHATTYPE = "group";
    private static final String CHANNELCHATTYPE = "channel";
    private static final String SUPERGROUPCHATTYPE = "supergroup";

    @JsonProperty(ID_FIELD)
    @NonNull
    private Long id;

    /**
     * Type of chat, can be either “private”, “group”, “supergroup” or “channel”
     */
    @JsonProperty(TYPE_FIELD)
    @NonNull
    private String type;

    /**
     * Optional. Title, for supergroups, channels and group chats
     */
    @JsonProperty(TITLE_FIELD)
    private String title;

    /**
     * Optional. Username, for private chats, supergroups and channels if available
     */
    @JsonProperty(USERNAME_FIELD)
    private String userName;

    /**
     * Optional. First name of the other party in a private chat
     */
    @JsonProperty(FIRSTNAME_FIELD)
    private String firstName;

    /**
     * Optional. Last name of the other party in a private chat
     */
    @JsonProperty(LASTNAME_FIELD)
    private String lastName;

    /**
     * Optional. Description, for groups, supergroups and channel chats.
     */
    @JsonProperty(DESCRIPTION_FIELD)
    private String description;

    @JsonIgnore
    public Boolean isGroupChat() {
        return GROUPCHATTYPE.equals(type);
    }

    @JsonIgnore
    public Boolean isChannelChat() {
        return CHANNELCHATTYPE.equals(type);
    }

    @JsonIgnore
    public Boolean isUserChat() {
        return USERCHATTYPE.equals(type);
    }

    @JsonIgnore
    public Boolean isSuperGroupChat() {
        return SUPERGROUPCHATTYPE.equals(type);
    }
}
