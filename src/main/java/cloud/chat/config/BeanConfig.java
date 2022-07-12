package cloud.chat.config;

import cloud.chat.bots.cc.CcLongPollingBot;
import cloud.chat.common.ObjectIdGenerator;
import cloud.chat.runner.GroupManagerBotCc;
import cloud.chat.runner.SuperIndexBotCc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author gx
 */
@Configuration
public class BeanConfig {

    @Value("${cc.bot.superindex.token}")
    private String superIndexToken;

    @Value("${cc.bot.groupmanager.token}")
    private String groupManagerToken;

    @Bean
    public ObjectIdGenerator createObjectIdGenerator() {
        return new ObjectIdGenerator(1L);
    }


    @Bean(name = "superIndexBotCc")
    public CcLongPollingBot superIndexBotCc(){
        if (StringUtils.isEmpty(superIndexToken)){
            return null;
        }
        SuperIndexBotCc superIndexBotCc = new SuperIndexBotCc();
        superIndexBotCc.setToken(superIndexToken);
        return superIndexBotCc;
    }

    @Bean(name = "groupManagerBotCc")
    public CcLongPollingBot groupManagerBotCc(){
        if (StringUtils.isEmpty(groupManagerToken)){
            return null;
        }
        GroupManagerBotCc groupManagerBotCc = new GroupManagerBotCc();
        groupManagerBotCc.setToken(groupManagerToken);
        return groupManagerBotCc;
    }

}
