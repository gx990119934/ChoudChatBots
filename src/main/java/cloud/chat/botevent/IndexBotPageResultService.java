package cloud.chat.botevent;

import cloud.chat.bots.cc.CcLongPollingBot;
import cloud.chat.bots.exceptions.BotApiException;
import cloud.chat.bots.methods.send.EditMessageText;
import cloud.chat.bots.methods.send.SendTextMessage;
import cloud.chat.bots.objects.Message;
import cloud.chat.bots.objects.ParseMode;
import cloud.chat.bots.objects.replykeyboard.InlineKeyboardMarkup;
import cloud.chat.bots.objects.replykeyboard.buttons.InlineKeyboardButton;
import cloud.chat.common.ApiConstants;
import cloud.chat.data.model.GroupChannelIndex;
import cloud.chat.service.GroupChannelIndexService;
import cloud.chat.utils.CcUtil;
import cloud.chat.utils.IndexPageUtil;
import cloud.chat.utils.MsgUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * 索引结果分页展示
 *
 * @author gx
 */
@Service
public class IndexBotPageResultService {

    @Autowired
    private GroupChannelIndexService groupChannelIndexService;

    public void indexResult(CcLongPollingBot longPollingBot, Message message) {
        int chatType = message.getChat().getType().equals("private") ? 2 : 4;
        if (chatType == 4) {
            if (message.hasEntities()) {
                if (CcUtil.assignMe(message.getText(), longPollingBot.getBotName())) {
                    message.setText(CcUtil.getTextExcludeAssign(message.getText(), longPollingBot.getBotName()));
                    pageGroupChannelResult(longPollingBot, message);
                }
            }
        } else {
            pagePrivateResult(longPollingBot, message, 1, true);
        }
    }

    public void indexCallBackResult(CcLongPollingBot longPollingBot, Message message, String userName, String callData) {
        callData = callData.replace(ApiConstants.CALLBACK_QUERY_NEXT_PAGE, "");
        callData = callData.replace(ApiConstants.CALLBACK_QUERY_PRE_PAGE, "");

        int pageNum = Integer.valueOf(callData.split("_")[0]);
        String text = callData.split("_")[1];
        Long messageId = Long.valueOf(callData.split("_")[2]);
        message.setText(text);
        message.setMessageId(messageId);

        if (pageNum == 0) {
            return;
        }
        pagePrivateResult(longPollingBot, message, pageNum, false);
    }


    public void pagePrivateResult(CcLongPollingBot longPollingBot, Message message, int pageNum, boolean starter) {
        int chatType = message.getChat().getType().equals("private") ? 2 : 4;
        if (chatType == 4) {
            if (message.hasEntities()) {
                if (!CcUtil.assignMe(message.getText(), longPollingBot.getBotName())) {
                    return;
                } else {
                    message.setText(CcUtil.getTextExcludeAssign(message.getText(), longPollingBot.getBotName()));
                }
            } else {
                return;
            }
        }

        String keyWords = message.getText();
        //数据库查找相关
        Weekend<GroupChannelIndex> weekend = new Weekend<>(GroupChannelIndex.class);
        WeekendCriteria<GroupChannelIndex, Object> weekendCriteria = weekend.weekendCriteria();
        weekendCriteria.orLike(GroupChannelIndex::getTitle, "%" + keyWords + "%");
        weekend.orderBy("weights").desc();
        weekend.orderBy("memberCount").desc();
        PageInfo<GroupChannelIndex> pageInfo = groupChannelIndexService.selectPage(weekend, pageNum, ApiConstants.QUERY_PRIVATE_PAGE_SIZE).getData();
        List<GroupChannelIndex> indexList = pageInfo.getList();

        if (starter) {
            message = sendEmpty(longPollingBot, message);
        }

        //构建EditMessageText
        List<List<InlineKeyboardButton>> allButton = new ArrayList<>();

        StringBuffer text = new StringBuffer(ApiConstants.FIRST_ADVERTISE_DESCRIPTION);
        text = MsgUtil.nextLine(text);
        text = MsgUtil.nextLine(text);

        if (!CollectionUtils.isEmpty(indexList)) {

            List<InlineKeyboardButton> row1 = new ArrayList<>();
            for (int i = 0; i < indexList.size(); i++) {

                GroupChannelIndex index = indexList.get(i);
                text.append(String.format("%s.%s%s-%s", i + 1, index.getIcon(), index.getTitle(), index.getMemberCount()));
                text = MsgUtil.nextLine(text);
                InlineKeyboardButton button = IndexPageUtil.getButtonEntry(i + 1, index);
                row1.add(button);
            }

            text = MsgUtil.nextLine(text);
            text.append("提示\uD83D\uDCE2：点击对应序号按钮，进入该频道或组\uD83D\uDC47");
            allButton.add(row1);
        } else {
            text.append("\uD83D\uDE2D\uD83D\uDE2D暂没有收录该关键词,请尝试其他关键词");
        }

        allButton.add(IndexPageUtil.getButtonPageInfo(pageNum, keyWords, message));

        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder().keyboard(allButton).build();
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(message.getChatId())
                .messageId(message.getMessageId())
                .text(text.toString())
                .chatType(chatType)
                .replyMarkup(inlineKeyboardMarkup)
                .build();

        try {
            longPollingBot.execute(editMessageText);
        } catch (BotApiException e) {
            e.printStackTrace();
        }
    }

    public void pageGroupChannelResult(CcLongPollingBot longPollingBot, Message message) {
        int chatType = message.getChat().getType().equals("private") ? 2 : 4;

        String keyWords = message.getText();
        //数据库查找相关
        Weekend<GroupChannelIndex> weekend = new Weekend<>(GroupChannelIndex.class);
        WeekendCriteria<GroupChannelIndex, Object> weekendCriteria = weekend.weekendCriteria();
        weekendCriteria.orLike(GroupChannelIndex::getTitle, "%" + keyWords + "%");
        weekend.orderBy("weights").desc();
        weekend.orderBy("memberCount").desc();
        PageInfo<GroupChannelIndex> pageInfo = groupChannelIndexService.selectPage(weekend, 1, ApiConstants.QUERY_GROUP_CHANNEL_PAGE_SIZE).getData();
        List<GroupChannelIndex> indexList = pageInfo.getList();

        StringBuffer text = new StringBuffer(ApiConstants.FIRST_ADVERTISE_DESCRIPTION);
        text = MsgUtil.nextLineMD(text);
        text = MsgUtil.nextLineMD(text);

        if (!CollectionUtils.isEmpty(indexList)) {
            for (int i = 0; i < indexList.size(); i++) {
                GroupChannelIndex index = indexList.get(i);
                String urlName = String.format("%s.%s%s-%s", i + 1, index.getIcon(), index.getTitle(), index.getMemberCount());
                text.append(MsgUtil.mkUrl(urlName,index.getUrl()));
                text = MsgUtil.nextLineMD(text);
            }
            text = MsgUtil.nextLineMD(text);
            text.append("提示\uD83D\uDCE2：点击对应序号按钮，进入该频道或组\uD83D\uDC47");
        } else {
            text.append("\uD83D\uDE2D\uD83D\uDE2D暂没有收录该关键词,请尝试其他关键词");
        }

        text = MsgUtil.nextLineMD(text);
        text = MsgUtil.nextLineMD(text);
        text.append(MsgUtil.mkUrl("\uD83D\uDD25\uD83D\uDD25想要获取更多数据，请点击进入我!",ApiConstants.BASE_USERNAME_URL+longPollingBot.getBotUsername()));

        SendTextMessage sendTextMessage = SendTextMessage.builder()
                .chatId(message.getChatId())
                .chatType(chatType)
                .text(text.toString())
                .parseMode(ParseMode.MARKDOWN)
                .build();
        try {
            longPollingBot.execute(sendTextMessage);
        } catch (BotApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送空消息
     */
    private Message sendEmpty(CcLongPollingBot pollingBot, Message message) {
        int chatType = message.getChat().getType().equals("private") ? 2 : 4;
        Message sendBackMsg = null;
        SendTextMessage sendTextMessage = SendTextMessage.builder()
                .chatId(message.getChatId())
                .chatType(chatType)
                .text(" ").build();
        try {
            sendBackMsg = pollingBot.execute(sendTextMessage);
        } catch (BotApiException e) {
            e.printStackTrace();
        }
        return sendBackMsg;
    }
}
