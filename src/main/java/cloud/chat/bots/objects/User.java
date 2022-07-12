package cloud.chat.bots.objects;

import cloud.chat.bots.common.BotApiObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * This object represents a user or bot
 * @author gx
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User implements BotApiObject {

    private static final String ID_FIELD = "id";
    private static final String ISBOT_FIELD = "is_bot";
    private static final String FIRSTNAME_FIELD = "first_name";
    private static final String LASTNAME_FIELD = "last_name";
    private static final String USERNAME_FIELD = "username";

    /**
     * Unique identifier for this user or bot
     */
    @JsonProperty(ID_FIELD)
    @NonNull
    private Long id;

    /**
     * True, if this user is a bot
     */
    @JsonProperty(ISBOT_FIELD)
    @NonNull
    private Boolean isBot;

    /**
     * 	User's or bot's first name
     */
    @JsonProperty(FIRSTNAME_FIELD)
    @NonNull
    private String firstName;

    /**
     * 	Optional. User's or bot's last name
     */
    @JsonProperty(LASTNAME_FIELD)
    private String lastName;

    /**
     * Optional. User's or bot's username
     */
    @JsonProperty(USERNAME_FIELD)
    private String userName;
}
