# choudchatbot
## 贡献
请拉取dev分支，并对dev提取merge请求

请，**不要推送任何令牌或 API 密钥**，我永远不会接受包含该内容的拉取请求。

## 功能介绍
超级索引机器人
 - 群频道搜索 （已完成）
 - 群频道加入索引库 （已完成）
 - 展示分页 （已完成）

群频道管理机器人
 - 查看我管理的群频道 （待整理）
 - 一键转发消息到所有群频道 （待整理）
 - 置顶消息到所有群组频道 （未完成）
 - 取消置顶到所有群组频道 （未完成）
 - 制定规则，当触发规则将自动将某用户提出群聊 （未完成）
 
投票机器人 （未完成）

## 使用
 - 添加新的机器人

1.创建机器人类
 
```java

//如
   public class SuperIndexBotCc extends CcLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
    }

    @Override
    public String getBotUsername() {}

    @Override
    public String getBotName() {}

    @Override
    public String getBotToken() {}

}

```
2.加入spring管理

```java

//如
@Configuration
public class BeanConfig {

    @Value("${cc.bot.superindex.token}")
    private String superIndexToken;

    @Bean(name = "superIndexBotCc")
    public CcLongPollingBot superIndexBotCc(){
        if (StringUtils.isEmpty(superIndexToken)){
            return null;
        }
        SuperIndexBotCc superIndexBotCc = new SuperIndexBotCc();
        superIndexBotCc.setToken(superIndexToken);
        return superIndexBotCc;
    }

}

```
3.spring启动时，机器人加入session管理
```java
//如
@Component
@Order(1)
public class BotInitRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CcLongPollingBot superIndexBotCc = null;
        try {
            superIndexBotCc = (CcLongPollingBot) SpringContext.getBean("superIndexBotCc");
        } catch (Exception e) {
            superIndexBotCc = null;
        }
        try {
            BotsApi botsApi = new BotsApi(DefaultBotSession.class);
            if (superIndexBotCc != null) {
                botsApi.registerBot(superIndexBotCc);
            }
        } catch (BotApiException e) {
            e.printStackTrace();
        }
    }

}

```
4.如需修改或者添加机器人回复消息或者处理某类消息
在包cloud.chat.botevent中完成

