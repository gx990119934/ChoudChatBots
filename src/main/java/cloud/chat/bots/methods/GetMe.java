package cloud.chat.bots.methods;

import cloud.chat.bots.exceptions.BotApiRequestException;
import cloud.chat.bots.objects.User;
import lombok.*;

/**
 * 一种测试您的机器人身份验证令牌的简单方法。 不需要参数。
 * 以用户对象的形式返回有关机器人的基本信息
 * @author gx
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class GetMe extends BotApiMethod<User> {
    public static final String PATH = "getMe";

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public User deserializeResponse(String answer) throws BotApiRequestException {
        return deserializeResponse(answer, User.class);
    }
}
