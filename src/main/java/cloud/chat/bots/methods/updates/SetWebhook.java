package cloud.chat.bots.methods.updates;

import cloud.chat.bots.exceptions.BotApiRequestException;
import cloud.chat.bots.exceptions.BotApiValidationException;
import cloud.chat.bots.methods.BotApiMethod;
import cloud.chat.bots.objects.InputFile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 *使用此方法可以指定URL并通过传出webhook接收传入更新。
 *无论何时更新机器人，我们都会向指定的URL发送HTTPS POST请求，
 *包含JSON序列化更新。如果请求不成功，
 *经过合理的尝试，我们将放弃。成功时返回True。
 *如果指定，请求将包含一个标题“X-Bot-Api-Secret-Token”，其内容为秘密令牌。
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetWebhook extends BotApiMethod<Boolean> {
    public static final String PATH = "setWebhook";

    public static final String URL_FIELD = "url";
    public static final String CERTIFICATE_FIELD = "certificate";
    public static final String MAXCONNECTIONS_FIELD = "max_connections";
    public static final String DROPPENDINGUPDATES_FIELD = "drop_pending_updates";

    /**
     * 发送更新的HTTPS url。使用空字符串删除webhook集成。由于GFW的限制，中国大陆的用户应该使用中国大陆以外的URL地址。
     */
    @JsonProperty(URL_FIELD)
    @NonNull
    private String url;

    /**
     * 上载公钥证书，以便可以检查正在使用的根证书。有关详细信息，请参阅我们的自签名指南。
     */
    @JsonProperty(CERTIFICATE_FIELD)
    private InputFile certificate;

    /**
     * (可选)
     *用于更新传递的webhook同时HTTPS连接的最大允许数量为1-100。
     *默认值为40。使用较低的值来限制bot服务器上的负载，
     *和更高的值来增加机器人的吞吐量。
     */
    @JsonProperty(MAXCONNECTIONS_FIELD)
    private Integer maxConnections;

    /**
     * 传递True以删除所有挂起的更新
     */
    @JsonProperty(DROPPENDINGUPDATES_FIELD)
    private Boolean dropPendingUpdates;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public void validate() throws BotApiValidationException {
        if (url.isEmpty()) {
            throw new BotApiValidationException("URL parameter can't be empty", this);
        }
        if (certificate != null) {
            if (!certificate.isNew()) {
                throw new BotApiValidationException("Certificate parameter must be a new file to upload", this);
            }
        }
    }

    @Override
    public Boolean deserializeResponse(String answer) throws BotApiRequestException {
        return deserializeResponse(answer, Boolean.class);
    }
}
