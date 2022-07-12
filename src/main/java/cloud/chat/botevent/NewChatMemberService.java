package cloud.chat.botevent;

import cloud.chat.bots.cc.CcLongPollingBot;
import cloud.chat.bots.exceptions.BotApiException;
import cloud.chat.bots.methods.send.GetChatMembersCount;
import cloud.chat.bots.objects.Message;
import cloud.chat.bots.objects.User;
import cloud.chat.data.model.GroupChannelIndex;
import cloud.chat.service.GroupChannelIndexService;
import cloud.chat.utils.CcUtil;
import cloud.chat.utils.IconUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 机器人事件之群组添加新成员
 *
 * @author gx
 */
@Service
public class NewChatMemberService {

    @Autowired
    private GroupChannelIndexService groupChannelIndexService;

    public void newChatMembers(CcLongPollingBot ccLongPollingBot, Message message ) {
        List<User> userList = message.getNewChatMembers();
        for (User user : userList) {
            //1.新成员是机器人，表示机器人加入了该群
            if (user.getUserName().equals(ccLongPollingBot.getBotUsername())) {

                //2.验证一下数据库是否已有该群，如果有则判断chatId是否存在了，不存在则更新(手动添加的群组)
                GroupChannelIndex index = new GroupChannelIndex();
                index.setUrl(CcUtil.getUrl(message.getChat().getUserName()));
                List<GroupChannelIndex> indexList = groupChannelIndexService.select(index).getData();
                if (!CollectionUtils.isEmpty(indexList)) {
                    if (StringUtils.isEmpty(indexList.get(0).getChatId())) {
                        index = indexList.get(0);
                        index.setChatId(message.getChatId());
                        index.setType(message.getChat().getType());
                        index.setWeights(1);
                        index.setMemberCount(getChatMembersCount(ccLongPollingBot,message.getChat().getUserName()));
                        groupChannelIndexService.update(index);
                    }
                    return;
                }

                //3.将该群组或频道加入索引库中
                index.setChatId(message.getChatId());
                index.setIcon(IconUtil.getAddGroupChannelIcon());
                index.setTitle(message.getChat().getTitle());
                index.setType(message.getChat().getType());
                index.setMemberCount(getChatMembersCount(ccLongPollingBot,message.getChat().getUserName()));
                index.setWeights(1);
                groupChannelIndexService.insert(index);
            }
        }
    }

    private int getChatMembersCount(CcLongPollingBot ccLongPollingBot, String userName) {
        try {
            GetChatMembersCount getChatMembersCount = GetChatMembersCount.builder().chatId(userName).build();
            return ccLongPollingBot.execute(getChatMembersCount);
        } catch (BotApiException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
