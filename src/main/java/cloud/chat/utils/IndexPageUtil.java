package cloud.chat.utils;

import cloud.chat.bots.objects.Message;
import cloud.chat.bots.objects.replykeyboard.buttons.InlineKeyboardButton;
import cloud.chat.common.ApiConstants;
import cloud.chat.data.model.GroupChannelIndex;

import java.util.ArrayList;
import java.util.List;

/**
 * 索引分页工具
 * @author gx
 */
public class IndexPageUtil {

    public static InlineKeyboardButton getButtonEntry(int i, GroupChannelIndex index){
        String type = index.getType().equals("group")?"群组":"频道";
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text(type+i)
                .url(index.getUrl()).build();
        return button;
    }

    /**
     * 操作页相关按钮
     * @param pageNum
     * @param keyWords
     * @param message
     * @return
     */
    public static List<InlineKeyboardButton> getButtonPageInfo(int pageNum, String keyWords, Message message){
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(getButtonPrePage(pageNum,keyWords,message));
        row.add(getButtonCurrentPage(pageNum,keyWords,message));
        row.add(getButtonNextPage(pageNum,keyWords,message));
        return row;
    }

    /**
     * 上一页按钮
     * @param pageNum
     * @param keyWords
     * @param message
     * @return
     */
    public static InlineKeyboardButton getButtonPrePage(int pageNum, String keyWords, Message message){
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text("上一页")
                .callbackData(ApiConstants.CALLBACK_QUERY_PRE_PAGE + (pageNum - 1) + "_" + keyWords + "_" + message.getMessageId()).build();
        return button;
    }

    /**
     * 当前页按钮
     * @param pageNum
     * @param keyWords
     * @param message
     * @return
     */
    public static InlineKeyboardButton getButtonCurrentPage(int pageNum, String keyWords, Message message){
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text("当前页:" + pageNum)
                .callbackData(ApiConstants.CALLBACK_QUERY_NEXT_PAGE + pageNum + "_" + keyWords + "_" + message.getMessageId()).build();
        return button;
    }

    /**
     * 下一页按钮
     * @param pageNum
     * @param keyWords
     * @param message
     * @return
     */
    public static InlineKeyboardButton getButtonNextPage(int pageNum, String keyWords, Message message){
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text("下一页")
                .callbackData(ApiConstants.CALLBACK_QUERY_NEXT_PAGE + (pageNum + 1) + "_" + keyWords + "_" + message.getMessageId()).build();
        return button;
    }
}
