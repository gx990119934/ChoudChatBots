package cloud.chat.botevent;

import cloud.chat.bots.cc.CcLongPollingBot;
import cloud.chat.bots.exceptions.BotApiException;
import cloud.chat.bots.methods.send.SendTextMessage;
import cloud.chat.bots.objects.Message;
import cloud.chat.utils.MsgUtil;
import org.springframework.stereotype.Service;

/**
 * 加入我们
 * @author gx
 */
@Service
public class IndexBotJoinUsService {

    public void joinUs(CcLongPollingBot longPollingBot,Message message) {
        StringBuffer text = new StringBuffer();
        text.append("加入我们有两种方式");
        text = MsgUtil.nextLine(text);
        text.append("加入我们之后，你的群组或频道将有更多计划被其他人从该机器人搜索到的你群组或频道");
        text = MsgUtil.nextLine(text);
        text.append("1.将该机器人添加至你的群组或频道");
        text = MsgUtil.nextLine(text);
        text.append("2.联系客服人员将群或频道名称，分享链接，群或频道人数发给客服即可！");
        text = MsgUtil.nextLine(text);
        text.append("将有机会获取更多权重，权重高群组或频道在搜索中将优先展示");
        int chatType = message.getChat().getType().equals("private") ? 2 : 4;
        SendTextMessage sendTextMessage = SendTextMessage.builder()
                .chatId(message.getChatId())
                .text(text.toString())
                .chatType(chatType).build();

        Message callBack = null;
        try {
            callBack = longPollingBot.execute(sendTextMessage);
        } catch (BotApiException e) {
            e.printStackTrace();
        }
        callBack.getMessageId();
    }

}
