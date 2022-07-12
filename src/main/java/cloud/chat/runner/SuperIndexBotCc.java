package cloud.chat.runner;

import cloud.chat.botevent.IndexBotJoinUsService;
import cloud.chat.botevent.IndexBotPageResultService;
import cloud.chat.botevent.IndexBotStartMsgService;
import cloud.chat.botevent.NewChatMemberService;
import cloud.chat.bots.cc.CcLongPollingBot;
import cloud.chat.bots.objects.Update;
import cloud.chat.bots.objects.User;
import cloud.chat.common.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * @author gx
 */
public class SuperIndexBotCc extends CcLongPollingBot {

    private String userName;

    private String name;

    private String token;

    @Autowired
    private NewChatMemberService newChatMemberService;

    @Autowired
    private IndexBotStartMsgService startMsgService;

    @Autowired
    private IndexBotPageResultService pageResultService;

    @Autowired
    private IndexBotJoinUsService joinUsService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();

            if (ApiConstants.CALLBACK_QUERY_JOIN_US.equals(data)) {
                joinUsService.joinUs(this,update.getCallbackQuery().getMessage());

            } else if (data.startsWith(ApiConstants.CALLBACK_QUERY_NEXT_PAGE) || data.startsWith(ApiConstants.CALLBACK_QUERY_PRE_PAGE)) {
                pageResultService.indexCallBackResult(this, update.getMessage(), getBotUsername(), data);

            } else {
                pageResultService.indexResult(this, update.getMessage());
            }
            return;
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case ApiConstants.CMD_START:
                    startMsgService.startMsg(this, update.getMessage(), getBotToken());
                    break;
                default:
                    pageResultService.indexResult(this, update.getMessage());
                    break;
            }
        }

        if (update.hasMessage() && update.getMessage().hasNewChatMembers()) {
            newChatMemberService.newChatMembers(this, update.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        if (StringUtils.isEmpty(userName)){
            try {
                User user = getMe();
                name = user.getFirstName();
                userName = user.getUserName();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return userName;
    }

    @Override
    public String getBotName() {
        if (StringUtils.isEmpty(userName)){
            try {
                User user = getMe();
                name = user.getFirstName();
                userName = user.getUserName();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
