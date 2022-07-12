package cloud.chat.bots.methods.updates;

import cloud.chat.bots.exceptions.BotApiRequestException;
import cloud.chat.bots.methods.BotApiMethod;
import cloud.chat.bots.objects.Update;
import lombok.*;

import java.util.ArrayList;

/**
 * 使用此方法通过长轮询 (wiki) 接收传入的更新。 更新数组
 * @author gx
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class GetUpdates extends BotApiMethod<ArrayList<Update>> {

    public static final String PATH = "getUpdates";

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public ArrayList<Update> deserializeResponse(String answer) throws BotApiRequestException {
        return deserializeResponseArray(answer, Update.class);
    }
}
