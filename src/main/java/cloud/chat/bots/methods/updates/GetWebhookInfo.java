package cloud.chat.bots.methods.updates;

import cloud.chat.bots.exceptions.BotApiRequestException;
import cloud.chat.bots.methods.BotApiMethod;
import cloud.chat.bots.objects.WebhookInfo;
import lombok.*;


/**
 * 使用此方法获取当前 webhook 状态。
 * 不需要参数。
 * 成功时，返回一个 WebhookInfo 对象。
 * 如果机器人正在使用 getUpdates，则会抛出错误。
 * @author gx
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class GetWebhookInfo extends BotApiMethod<WebhookInfo> {
    public static final String PATH = "getWebhookInfo";

    @Override
    public String getMethod() {
        return PATH;
    }


    @Override
    public WebhookInfo deserializeResponse(String answer) throws BotApiRequestException {
        return deserializeResponse(answer, WebhookInfo.class);
    }
}
