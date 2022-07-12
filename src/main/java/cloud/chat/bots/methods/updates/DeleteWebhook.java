package cloud.chat.bots.methods.updates;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteWebhook extends BotApiMethod<String> {

    public static final String PATH = "deleteWebhook";

    private static final String DROPPENDINGUPDATES_FIELD = "drop_pending_updates";

    @JsonProperty(DROPPENDINGUPDATES_FIELD)
    private Boolean dropPendingUpdates;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public String deserializeResponse(String answer) throws BotApiRequestException {
        return deserializeResponse(answer, String.class);
    }
}
