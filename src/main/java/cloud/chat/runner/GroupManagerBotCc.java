package cloud.chat.runner;

import cloud.chat.bots.cc.CcLongPollingBot;
import cloud.chat.common.ApiConstants;
import cloud.chat.bots.exceptions.BotApiException;
import cloud.chat.bots.methods.send.EditMessageText;
import cloud.chat.bots.methods.send.ForwardMessage;
import cloud.chat.bots.methods.send.GetChatMembersCount;
import cloud.chat.bots.methods.send.SendTextMessage;
import cloud.chat.bots.objects.Message;
import cloud.chat.bots.objects.ParseMode;
import cloud.chat.bots.objects.Update;
import cloud.chat.bots.objects.User;
import cloud.chat.bots.objects.replykeyboard.InlineKeyboardMarkup;
import cloud.chat.bots.objects.replykeyboard.buttons.InlineKeyboardButton;
import cloud.chat.data.model.GroupChannelIndex;
import cloud.chat.data.model.IndexPrivate;
import cloud.chat.data.model.MsgRule;
import cloud.chat.service.GroupChannelIndexService;
import cloud.chat.service.IndexPrivateService;
import cloud.chat.service.MsgRuleService;
import cloud.chat.utils.MsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gx
 */
public class GroupManagerBotCc extends CcLongPollingBot {

    private String userName;

    private String token;

    @Autowired
    private GroupChannelIndexService groupChannelIndexService;

    @Autowired
    private MsgRuleService msgRuleService;

    @Autowired
    private IndexPrivateService indexPrivateService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            switch (data) {
                case ApiConstants.CALLBACK_RULE_KILL_MEMBER:
                    starMsg(update.getMessage());
                    break;
                case ApiConstants.CALLBACK_MY_GROUP_CHANNEL:
                    myGroupAndChannel(update.getMessage());
                    break;
                case ApiConstants.CALLBACK_GROUP_CHANNEL_FORWARD:
                    groupChannelForward(update.getMessage());
                    break;
                default:
                    starMsg(update.getMessage());
                    break;
            }
            return;
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            //是否是群转发消息
            if (MsgUtil.getForward(update.getMessage().getChatId())){
                forwardMsg(update.getMessage());
                return;
            }
            switch (update.getMessage().getText()) {
                case ApiConstants.CMD_START:
                    starMsg(update.getMessage());
                    break;
                default:
                    starMsg(update.getMessage());
                    break;
            }
        }
        if (update.hasMessage() && update.getMessage().hasNewChatMembers()) {
            newChatMembers(update.getMessage());
        }
    }

    private void starMsg(Message message) {
        MsgRule msgRule = new MsgRule();
        msgRule.setToken(getBotToken());
        msgRule.setMsgKey(ApiConstants.CMD_START);
        List<MsgRule> msgRuleList = msgRuleService.select(msgRule).getData();
        if (!CollectionUtils.isEmpty(msgRuleList)) {
            msgRule = msgRuleList.get(0);

            int chatType = message.getChat().getType().equals("private") ? 2 : 4;
            SendTextMessage sendTextMessage = SendTextMessage.builder()
                    .chatId(message.getChatId())
                    .text(msgRule.getMsgValue())
                    .chatType(chatType).build();

            Message callBack = null;
            try {
                callBack = execute(sendTextMessage);
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
                    .text("踢人规则")
                    .callbackData(ApiConstants.CALLBACK_RULE_KILL_MEMBER).build();
            InlineKeyboardButton row1_button3 = InlineKeyboardButton.builder()
                    .text("我的群组")
                    .callbackData(ApiConstants.CALLBACK_MY_GROUP_CHANNEL).build();
            row1.add(row1_button1);
            row1.add(row1_button2);
            row1.add(row1_button3);
            buttonList.add(row1);
            List<InlineKeyboardButton> row2 = new ArrayList<>();
            InlineKeyboardButton row2_button1 = InlineKeyboardButton.builder()
                    .text("群发")
                    .callbackData(ApiConstants.CALLBACK_GROUP_CHANNEL_FORWARD).build();
            InlineKeyboardButton row2_button2 = InlineKeyboardButton.builder()
                    .text("置顶消息")
                    .callbackData(ApiConstants.CALLBACK_RULE_KILL_MEMBER).build();
            InlineKeyboardButton row2_button3 = InlineKeyboardButton.builder()
                    .text("取消置顶")
                    .callbackData(ApiConstants.CALLBACK_RULE_KILL_MEMBER).build();
            row2.add(row2_button1);
            row2.add(row2_button2);
            row2.add(row2_button3);
            buttonList.add(row2);

            InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder().keyboard(buttonList).build();
            EditMessageText editMessageText = EditMessageText.builder()
                    .chatId(callBack.getChatId())
                    .messageId(callBack.getMessageId())
                    .text(msgRule.getMsgValue())
                    .chatType(chatType)
                    .replyMarkup(inlineKeyboardMarkup)
                    .build();
            try {
                execute(editMessageText);
            } catch (BotApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void myGroupAndChannel(Message message) {
        int chatType = message.getChat().getType().equals("private") ? 2 : 4;
        IndexPrivate indexPrivate = new IndexPrivate();
        indexPrivate.setPrivateChatId(message.getChatId());
        List<IndexPrivate> indexPrivateList = indexPrivateService.select(indexPrivate).getData();
        if (CollectionUtils.isEmpty(indexPrivateList)) {
            String text = "暂时没找到你管理的群和频道，请将机器人加入你的群和频道！";
            SendTextMessage sendTextMessage = SendTextMessage.builder()
                    .chatId(message.getChatId())
                    .text(text)
                    .chatType(chatType).build();

            try {
                execute(sendTextMessage);
            } catch (BotApiException e) {
                e.printStackTrace();
            }
            return;
        }

        StringBuffer text = new StringBuffer();
        List<Long> indexIdList = indexPrivateList.stream().map(indexPrivate1 -> indexPrivate1.getIndexId()).collect(Collectors.toList());
        Weekend<GroupChannelIndex> weekend = new Weekend<>(GroupChannelIndex.class);
        WeekendCriteria<GroupChannelIndex, Object> criteria = weekend.weekendCriteria();
        criteria.andIn(GroupChannelIndex::getId, indexIdList);
        List<GroupChannelIndex> indexList = groupChannelIndexService.select(weekend).getData();
        List<GroupChannelIndex> groupList = indexList.stream().filter(index -> index.getType().equals("group")).collect(Collectors.toList());
        List<GroupChannelIndex> channelList = indexList.stream().filter(index -> index.getType().equals("channel")).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(groupList)) {
            text.append("你暂时没有群管理");
        } else {
            text.append("你管理的群");
            text.append("\\\\n");
            for (int i = 0; i < groupList.size(); i++) {
                String tmp = String.format("[%s](%s)", (i + 1) + groupList.get(i).getTitle() + groupList.get(i).getMemberCount(), groupList.get(i).getUrl());
                text.append(tmp);
                text.append("\\\\n");
            }
        }
        if (CollectionUtils.isEmpty(channelList)) {
            text.append("你暂时没有频道管理");
        } else {
            text.append("你管理的频道");
            text.append("\\\\n");
            for (int i = 0; i < channelList.size(); i++) {
                String tmp = String.format("[%s](%s)", (i + 1) + groupList.get(i).getTitle() + groupList.get(i).getMemberCount(), groupList.get(i).getUrl());
                text.append(tmp);
                text.append("\\\\n");
            }
        }

        SendTextMessage sendTextMessage = SendTextMessage.builder()
                .chatId(message.getChatId())
                .text(text.toString())
                .parseMode(ParseMode.MARKDOWN)
                .chatType(chatType).build();
        try {
            execute(sendTextMessage);
        } catch (BotApiException e) {
            e.printStackTrace();
        }
    }

    private void groupChannelForward(Message message) {
        int chatType = message.getChat().getType().equals("private") ? 2 : 4;
        String text = "请输入你要群转发的消息！我将替你转发至所有群组";
        SendTextMessage sendTextMessage = SendTextMessage.builder()
                .chatId(message.getChatId())
                .text(text)
                .chatType(chatType).build();
        try {
            execute(sendTextMessage);
            MsgUtil.setForard(message.getChatId());
        } catch (BotApiException e) {
            e.printStackTrace();
        }
    }

    private void forwardMsg(Message message) {
        int chatType = message.getChat().getType().equals("private") ? 2 : 4;
        IndexPrivate indexPrivate = new IndexPrivate();
        indexPrivate.setPrivateChatId(message.getChatId());
        List<IndexPrivate> indexPrivateList =  indexPrivateService.select(indexPrivate).getData();
        if (CollectionUtils.isEmpty(indexPrivateList)){
            String text = "你暂时还没有管理群组和频道！";
            SendTextMessage sendTextMessage = SendTextMessage.builder()
                    .chatId(message.getChatId())
                    .text(text)
                    .chatType(chatType).build();
            try {
                execute(sendTextMessage);
                MsgUtil.setForard(message.getChatId());
            } catch (BotApiException e) {
                e.printStackTrace();
            }
            return;
        }

        List<Long> indexIdList = indexPrivateList.stream().map(indexPrivate1 -> indexPrivate1.getIndexId()).collect(Collectors.toList());
        List<GroupChannelIndex> indexList =  groupChannelIndexService.selectIn(indexIdList).getData();
        indexList.stream().forEach(index->{
            ForwardMessage forwardMessage = ForwardMessage.builder()
                    .messageId(message.getMessageId()-28)
                    .chatId(index.getChatId())
                    .fromChatId(message.getFrom().getId()).build();
            try {
                execute(forwardMessage);
            } catch (BotApiException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 群组或频道添加新成员
     *
     * @param message
     */
    private void newChatMembers(Message message) {
        List<User> userList = message.getNewChatMembers();
        for (User user : userList) {
            if (user.getUserName().equals(getBotUsername())) {
                GroupChannelIndex index = new GroupChannelIndex();
                index.setUrl(ApiConstants.BASE_USERNAME_URL + message.getChat().getUserName());
                List<GroupChannelIndex> indexList = groupChannelIndexService.select(index).getData();
                if (!CollectionUtils.isEmpty(indexList)) {
                    return;
                }

                //获取成员数量
                Integer counts = 0;
                try {
                    GetChatMembersCount getChatMembersCount = GetChatMembersCount.builder().chatId(message.getChat().getUserName()).build();
                    counts = execute(getChatMembersCount);
                } catch (BotApiException e) {
                    e.printStackTrace();
                }

                //将该群组或频道加入索引库中
                index.setChatId(message.getChatId());
                index.setIcon("\uD83D\uDCE2\uD83D\uDCE2");
                index.setTitle(message.getChat().getTitle());
                index.setType(message.getChat().getType());
                index.setMemberCount(counts);
                index.setWeights(1);
                groupChannelIndexService.insert(index);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotName() {
        return null;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
