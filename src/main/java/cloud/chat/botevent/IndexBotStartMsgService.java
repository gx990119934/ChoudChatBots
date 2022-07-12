package cloud.chat.botevent;

import cloud.chat.bots.cc.CcLongPollingBot;
import cloud.chat.bots.exceptions.BotApiException;
import cloud.chat.bots.methods.send.EditMessageText;
import cloud.chat.bots.methods.send.SendTextMessage;
import cloud.chat.bots.objects.Message;
import cloud.chat.bots.objects.replykeyboard.InlineKeyboardMarkup;
import cloud.chat.bots.objects.replykeyboard.buttons.InlineKeyboardButton;
import cloud.chat.common.ApiConstants;
import cloud.chat.data.model.MsgRule;
import cloud.chat.service.MsgRuleService;
import cloud.chat.utils.CcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 索引机器人的欢迎语
 * @author gx
 */
@Service
public class IndexBotStartMsgService {

    @Autowired
    private MsgRuleService msgRuleService;

    public void startMsg(CcLongPollingBot ccLongPollingBot, Message message,String token){
        int chatType = message.getChat().getType().equals("private") ? 2 : 4;
        if (chatType==4){
            if (message.hasEntities()){
                if (!CcUtil.assignMe(message.getText(),ccLongPollingBot.getBotUsername())){
                    return;
                }
            }
        }

        //1.查询数据库规则
        MsgRule msgRule = new MsgRule();
        msgRule.setToken(token);
        msgRule.setMsgKey(ApiConstants.CMD_START);
        List<MsgRule> msgRuleList = msgRuleService.select(msgRule).getData();
        if (!CollectionUtils.isEmpty(msgRuleList)) {
            msgRule = msgRuleList.get(0);

            SendTextMessage sendTextMessage = SendTextMessage.builder()
                    .chatId(message.getChatId())
                    .text(msgRule.getMsgValue())
                    .chatType(chatType).build();

            Message callBack = null;
            try {
                callBack = ccLongPollingBot.execute(sendTextMessage);
            } catch (BotApiException e) {
                e.printStackTrace();
            }
            callBack.getMessageId();

            //构建按钮
            List<List<InlineKeyboardButton>> buttonList = new ArrayList<>();
            List<InlineKeyboardButton> row1 = new ArrayList<>();
            InlineKeyboardButton row1_button1 = InlineKeyboardButton.builder()
                    .text("客服")
                    .url("https://tel.ccc.sss").build();
            InlineKeyboardButton row1_button2 = InlineKeyboardButton.builder()
                    .text("加入我们")
                    .callbackData(ApiConstants.CALLBACK_QUERY_JOIN_US).build();
            row1.add(row1_button1);
            row1.add(row1_button2);
            buttonList.add(row1);

            InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder().keyboard(buttonList).build();
            EditMessageText editMessageText = EditMessageText.builder()
                    .chatId(callBack.getChatId())
                    .messageId(callBack.getMessageId())
                    .text(msgRule.getMsgValue())
                    .chatType(chatType)
                    .replyMarkup(inlineKeyboardMarkup)
                    .build();
            try {
                ccLongPollingBot.execute(editMessageText);
            } catch (BotApiException e) {
                e.printStackTrace();
            }
        }
    }
}
