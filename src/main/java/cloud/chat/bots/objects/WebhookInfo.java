package cloud.chat.bots.objects;

import cloud.chat.bots.common.BotApiObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * @author gx
 * Contains information about the current state of the webhook
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WebhookInfo implements BotApiObject {

    private static final String URL_FIELD = "url";
    private static final String HASCUSTOMCERTIFICATE_FIELD = "has_custom_certificate";
    private static final String PENDINGUPDATECOUNT_FIELD = "pending_update_count";
    private static final String MAXCONNECTIONS_FIELD = "max_connections";
    private static final String ALLOWEDUPDATES_FIELD = "allowed_updates";
    private static final String LASTERRORDATE_FIELD = "last_error_date";
    private static final String LASTERRORMESSAGE_FIELD = "last_error_message";
    private static final String IPADDRESS_FIELD = "ip_address";
    private static final String LASTSYNCHRONIZATIONERRORDATE_FIELD = "last_synchronization_error_date";

    /**
     * Webhook URL may be empty if no webhook is set
     */
    @JsonProperty(URL_FIELD)
    private String url;

    /**
     * True if a custom certificate was provided for webhook certificate checking
     */
    @JsonProperty(HASCUSTOMCERTIFICATE_FIELD)
    private Boolean hasCustomCertificate;

    /**
     * Updates Awaiting Delivery
     */
    @JsonProperty(PENDINGUPDATECOUNT_FIELD)
    private Integer pendingUpdatesCount;

    /**
     * (Optional) The current webhook IP address in use
     */
    @JsonProperty(IPADDRESS_FIELD)
    private String ipAddress;

    /**
     * (Optional) The Unix time of the latest error that occurred when trying to deliver the update via the webhook
     */
    @JsonProperty(LASTERRORDATE_FIELD)
    private Integer lastErrorDate;

    /**
     * (Optional) Error message in human-readable format for the latest error that occurred when trying to deliver an update via the webhook
     */
    @JsonProperty(LASTERRORMESSAGE_FIELD)
    private String lastErrorMessage;

    /**
     * (Optional) The latest wrong Unix time that occurred when trying to sync with the datacenter for available updates
     */
    @JsonProperty(LASTSYNCHRONIZATIONERRORDATE_FIELD)
    private Integer lastSynchronizationErrorDate;

    /**
     * (Optional) Maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery
     */
    @JsonProperty(MAXCONNECTIONS_FIELD)
    private Integer maxConnections;

    /**
     * (Optional) A list of update types to subscribe to the bot. Defaults to all update types except chat_member
     */
    @JsonProperty(ALLOWEDUPDATES_FIELD)
    private List<String> allowedUpdates;

}