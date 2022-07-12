package cloud.chat.task;

import cloud.chat.bots.cc.CcLongPollingBot;
import cloud.chat.bots.exceptions.BotApiException;
import cloud.chat.bots.methods.send.GetChatAdministrators;
import cloud.chat.bots.objects.ChatMember;
import cloud.chat.common.SpringContext;
import cloud.chat.data.model.CcPrivate;
import cloud.chat.data.model.GroupChannelIndex;
import cloud.chat.data.model.IndexPrivate;
import cloud.chat.service.CcPrivateService;
import cloud.chat.service.GroupChannelIndexService;
import cloud.chat.service.IndexPrivateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 每隔1分钟,检查数据库的群组是否还存在，并同步管理员
 */
@Component
@Slf4j
public class BotTask {

    @Autowired
    private GroupChannelIndexService indexService;

    @Autowired
    private CcPrivateService ccPrivateService;

    @Autowired
    private IndexPrivateService indexPrivateService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void groupChannelCheck() {
        GroupChannelIndex index = new GroupChannelIndex();
        index.setFlag(1);
        List<GroupChannelIndex> indexList = indexService.select(index).getData();

        if (indexList.isEmpty()) {
            //删除中间表所有数据
            IndexPrivate indexPrivate = new IndexPrivate();
            indexPrivate.setFlag(1);
            indexPrivateService.delete(indexPrivate);
        }

        //获取所有管理员信息
        CcLongPollingBot ccLongPollingBot = getLongPollingBot();
        if (ccLongPollingBot!=null){

            indexList.stream().forEach(index1->{
                GetChatAdministrators administrators = GetChatAdministrators.builder().chatId(index1.getChatId()).build();
                List<ChatMember> chatMemberList = null;
                try {
                    chatMemberList  = ccLongPollingBot.execute(administrators);
                } catch (BotApiException e) {
                    e.printStackTrace();
                }

                //将创建者和管理员插入用户表和中间表
                if (!CollectionUtils.isEmpty(chatMemberList)){
                    //1.提取数据库中的CcPrivate
                    List<Long> chatIdList = chatMemberList.stream().map(chatMember -> chatMember.getUser().getId()).collect(Collectors.toList());
                    Weekend<CcPrivate> weekend = new Weekend(CcPrivate.class);
                    WeekendCriteria<CcPrivate, Object> criteria= weekend.weekendCriteria();
                    criteria.andIn(CcPrivate::getChatId,chatIdList);
                    List<CcPrivate> dbCcPrivateList = ccPrivateService.select(weekend).getData();

                    //2.获取需要插入CcPrivate中的对象
                    List<CcPrivate> ccPrivateList = new ArrayList<>();
                    chatMemberList.stream().forEach(chatMember -> {

                        boolean isInsert = true;

                        if (!CollectionUtils.isEmpty(dbCcPrivateList)){
                            for (CcPrivate ccPrivate: dbCcPrivateList) {
                                if (ccPrivate.getChatId().equals(chatMember.getUser().getId())){
                                    isInsert = false;
                                }
                            }
                        }

                        if (isInsert){
                            CcPrivate ccPrivate = new CcPrivate();
                            ccPrivate.setChatId(chatMember.getUser().getId());
                            ccPrivate.setCc(chatMember.getUser().getUserName());
                            ccPrivate.setName(chatMember.getUser().getFirstName());
                            ccPrivateList.add(ccPrivate);
                        }
                    });

                    //3.执行插入
                    if (!CollectionUtils.isEmpty(ccPrivateList)){
                        ccPrivateService.insertBatch(ccPrivateList);
                    }

                    //4.提取数据库中的IndexPrivate
                    Weekend<IndexPrivate> indexPrivateWeekend = new Weekend(IndexPrivate.class);
                    WeekendCriteria<IndexPrivate, Object> indexPrivateObjectWeekendCriteria= indexPrivateWeekend.weekendCriteria();
                    indexPrivateObjectWeekendCriteria.andIn(IndexPrivate::getPrivateChatId,chatIdList);
                    indexPrivateObjectWeekendCriteria.andEqualTo(IndexPrivate::getIndexId,index1.getId());
                    List<IndexPrivate> dbIndexPrivateList = indexPrivateService.select(indexPrivateWeekend).getData();


                    //5.获取需要插入IndexPrivate中的对象
                    List<IndexPrivate> indexPrivateList = new ArrayList<>();
                    chatMemberList.stream().forEach(chatMember -> {

                        boolean isInsert = true;

                        if (!CollectionUtils.isEmpty(dbIndexPrivateList)){
                            for (IndexPrivate indexPrivate: dbIndexPrivateList) {
                                if (indexPrivate.getPrivateChatId().equals(chatMember.getUser().getId())){
                                    isInsert = false;
                                }
                            }
                        }

                        if (isInsert){
                            IndexPrivate indexPrivate = new IndexPrivate();
                            indexPrivate.setIndexId(index1.getId());
                            //查询CcPrivate对象
                            CcPrivate tmp = new CcPrivate();
                            tmp.setChatId(chatMember.getUser().getId());
                            tmp = ccPrivateService.select(tmp).getData().get(0);
                            indexPrivate.setPrivateId(tmp.getId());
                            indexPrivate.setPrivateChatId(chatMember.getUser().getId());
                            indexPrivate.setStatus(chatMember.getStatus());
                            indexPrivateList.add(indexPrivate);
                        }
                    });

                    //6.执行插入
                    if (!CollectionUtils.isEmpty(indexPrivateList)){
                        indexPrivateService.insertBatch(indexPrivateList);
                    }

                }

            });

        }
    }


    private CcLongPollingBot getLongPollingBot(){
        CcLongPollingBot ccLongPollingBot = null;
        try {
            ccLongPollingBot = (CcLongPollingBot) SpringContext.getBean("superIndexBotCc");
        } catch (Exception e) {
            ccLongPollingBot = null;
        }
        if (ccLongPollingBot==null){
            try {
                ccLongPollingBot = (CcLongPollingBot) SpringContext.getBean("groupManagerBotCc");
            } catch (Exception e) {
                ccLongPollingBot = null;
            }
        }
        return ccLongPollingBot;
    }
}
