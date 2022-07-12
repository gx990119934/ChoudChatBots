package cloud.chat.bots.objects;

import cloud.chat.bots.common.BotApiObject;
import cloud.chat.bots.objects.replykeyboard.InlineKeyboardMarkup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.animation.Animation;
import lombok.*;
import org.apache.tomcat.jni.Poll;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 该对象表示一条消息。
 *
 * @author gx
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Message implements BotApiObject {
    private static final String MESSAGEID_FIELD = "message_id";
    private static final String FROM_FIELD = "from";
    private static final String DATE_FIELD = "date";
    private static final String CHAT_FIELD = "chat";
    private static final String FORWARDFROM_FIELD = "forward_from";
    private static final String FORWARDFROMCHAT_FIELD = "forward_from_chat";
    private static final String FORWARDDATE_FIELD = "forward_date";
    private static final String TEXT_FIELD = "text";
    private static final String ENTITIES_FIELD = "entities";
    private static final String CAPTIONENTITIES_FIELD = "caption_entities";
    private static final String AUDIO_FIELD = "audio";
    private static final String DOCUMENT_FIELD = "document";
    private static final String PHOTO_FIELD = "photo";
    private static final String STICKER_FIELD = "sticker";
    private static final String VIDEO_FIELD = "video";
    private static final String CONTACT_FIELD = "contact";
    private static final String LOCATION_FIELD = "location";
    private static final String VENUE_FIELD = "venue";
    private static final String ANIMATION_FIELD = "animation";
    private static final String PINNED_MESSAGE_FIELD = "pinned_message";
    private static final String NEWCHATMEMBERS_FIELD = "new_chat_members";
    private static final String LEFTCHATMEMBER_FIELD = "left_chat_member";
    private static final String NEWCHATTITLE_FIELD = "new_chat_title";
    private static final String NEWCHATPHOTO_FIELD = "new_chat_photo";
    private static final String DELETECHATPHOTO_FIELD = "delete_chat_photo";
    private static final String GROUPCHATCREATED_FIELD = "group_chat_created";
    private static final String REPLYTOMESSAGE_FIELD = "reply_to_message";
    private static final String VOICE_FIELD = "voice";
    private static final String CAPTION_FIELD = "caption";
    private static final String SUPERGROUPCREATED_FIELD = "supergroup_chat_created";
    private static final String CHANNELCHATCREATED_FIELD = "channel_chat_created";
    private static final String MIGRATETOCHAT_FIELD = "migrate_to_chat_id";
    private static final String MIGRATEFROMCHAT_FIELD = "migrate_from_chat_id";
    private static final String EDITDATE_FIELD = "edit_date";
    private static final String GAME_FIELD = "game";
    private static final String FORWARDFROMMESSAGEID_FIELD = "forward_from_message_id";
    private static final String INVOICE_FIELD = "invoice";
    private static final String SUCCESSFUL_PAYMENT_FIELD = "successful_payment";
    private static final String VIDEO_NOTE_FIELD = "video_note";
    private static final String AUTHORSIGNATURE_FIELD = "author_signature";
    private static final String FORWARDSIGNATURE_FIELD = "forward_signature";
    private static final String MEDIAGROUPID_FIELD = "media_group_id";
    private static final String CONNECTEDWEBSITE_FIELD = "connected_website";
    private static final String PASSPORTDATA_FIELD = "passport_data";
    private static final String FORWARDSENDERNAME_FIELD = "forward_sender_name";
    private static final String POLL_FIELD = "poll";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";
    private static final String DICE_FIELD = "dice";
    private static final String VIABOT_FIELD = "via_bot";
    private static final String SENDERCHAT_FIELD = "sender_chat";
    private static final String PROXIMITYALERTTRIGGERED_FIELD = "proximity_alert_triggered";
    private static final String MESSAGEAUTODELETETIMERCHANGED_FIELD = "message_auto_delete_timer_changed";
    private static final String ISAUTOMATICFORWARD_FIELD = "is_automatic_forward";
    private static final String HASPROTECTEDCONTENT_FIELD = "has_protected_content";
    private static final String WEBAPPDATA_FIELD = "web_app_data";
    private static final String VIDEOCHATSCHEDULED_FIELD = "video_chat_scheduled";
    private static final String VIDEOCHATSTARTED_FIELD = "video_chat_started";
    private static final String VIDEOCHATENDED_FIELD = "video_chat_ended";
    private static final String VIDEOCHATPARTICIPANTSINVITED_FIELD = "video_chat_participants_invited";

    @JsonProperty(MESSAGEID_FIELD)
    private Long messageId; ///< Integer	Unique message identifier
    @JsonProperty(FROM_FIELD)
    private User from; ///< Optional. Sender, can be empty for messages sent to channels
    @JsonProperty(DATE_FIELD)
    private Integer date; ///< Date the message was sent in Unix time
    @JsonProperty(CHAT_FIELD)
    private Chat chat; ///< Conversation the message belongs to
    @JsonProperty(FORWARDFROM_FIELD)
    private User forwardFrom; ///< Optional. For forwarded messages, sender of the original message
    /**
     * 可选的。
     * 对于从频道或匿名管理员转发的消息，有关原始发件人聊天的信息
     */
    @JsonProperty(FORWARDFROMCHAT_FIELD)
    private Chat forwardFromChat;
    @JsonProperty(FORWARDDATE_FIELD)
    private Integer forwardDate; ///< Optional. For forwarded messages, date the original message was sent
    @JsonProperty(TEXT_FIELD)
    private String text; ///< Optional. For text messages, the actual UTF-8 text of the message
    /**
     * 可选的。 对于短信，特殊实体，如用户名、URL、
     * 文本中出现的机器人命令等
     */
    @JsonProperty(ENTITIES_FIELD)
    private List<MessageEntity> entities;
    /**
     * 可选的。 对于带有标题的消息，用户名等特殊实体，
     * 出现在标题中的 URL、机器人命令等
     */
    @JsonProperty(CAPTIONENTITIES_FIELD)
    private List<MessageEntity> captionEntities;
    @JsonProperty(DOCUMENT_FIELD)
    private Document document; ///< Optional. Message is a general file, information about the file
    /**
     * 可选的。 Message是一个动画，关于动画的信息。
     * 为了向后兼容，当设置该字段时，文档字段也将被设置
     */
    @JsonProperty(ANIMATION_FIELD)
    private Animation animation;
    @JsonProperty(PINNED_MESSAGE_FIELD)
    private Message pinnedMessage; ///< Optional. Specified message was pinned. Note that the Message object in this field will not contain further reply_to_message fields even if it is itself a reply.
    @JsonProperty(NEWCHATMEMBERS_FIELD)
    private List<User> newChatMembers; ///< Optional. New members were added to the group or supergroup, information about them (the bot itself may be one of these members)
    @JsonProperty(LEFTCHATMEMBER_FIELD)
    private User leftChatMember; ///< Optional. A member was removed from the group, information about them (this member may be bot itself)
    @JsonProperty(NEWCHATTITLE_FIELD)
    private String newChatTitle; ///< Optional. A chat title was changed to this value
    @JsonProperty(DELETECHATPHOTO_FIELD)
    private Boolean deleteChatPhoto; ///< Optional. Informs that the chat photo was deleted
    @JsonProperty(GROUPCHATCREATED_FIELD)
    private Boolean groupchatCreated; ///< Optional. Informs that the group has been created
    @JsonProperty(REPLYTOMESSAGE_FIELD)
    private Message replyToMessage;
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Caption for the document, photo or video, 0-200 characters
    /**
     * 可选的。 服务消息：超级组已创建。
     * 此字段无法在通过更新的消息中接收，
     * 因为机器人在创建时不能是超级组的成员。
     * 只能在reply_to_message中找到
     * 如果有人回复了直接创建的超级组中的第一条消息。
     */
    @JsonProperty(SUPERGROUPCREATED_FIELD)
    private Boolean superGroupCreated;
    /**
     * 可选的。 服务消息：通道已创建。
     * 此字段无法在通过更新的消息中接收，
     * 因为机器人在创建时不能成为频道的成员。
     * 如果有人，它只能在 reply_to_message 中找到
     * 回复频道中的第一条消息。
     */
    @JsonProperty(CHANNELCHATCREATED_FIELD)
    private Boolean channelChatCreated;
    /**
     * 可选的。 该组已迁移到具有指定标识符的超组。
     * 这个数字可能大于 32 位和某些编程语言
     * 在解释它时可能有困难/无声的缺陷。
     * 但它小于 52 位，所以是有符号的 64 位整数或双精度
     * float 类型对于存储此标识符是安全的。
     */
    @JsonProperty(MIGRATETOCHAT_FIELD)
    private Long migrateToChatId; ///< Optional. The chat has been migrated to a chat with specified identifier, not exceeding 1e13 by absolute value
    /**
     * 可选的。 超组已从具有指定标识符的组迁移。
     * 这个数字可能大于 32 位和某些编程语言
     * 在解释它时可能有困难/无声的缺陷。
     * 但它小于 52 位，所以是有符号的 64 位整数或双精度
     * float 类型对于存储此标识符是安全的。
     */
    @JsonProperty(MIGRATEFROMCHAT_FIELD)
    private Long migrateFromChatId; ///< Optional. The chat has been migrated from a chat with specified identifier, not exceeding 1e13 by absolute value
    @JsonProperty(EDITDATE_FIELD)
    private Integer editDate; ///< Optional. Date the message was last edited in Unix time
    @JsonProperty(FORWARDFROMMESSAGEID_FIELD)
    private Integer forwardFromMessageId; ///< Optional. For forwarded channel posts, identifier of the original message in the channel
    /**
     * 可选的。
     * 频道内消息的帖子作者签名，或匿名组管理员的自定义标题
     */
    @JsonProperty(AUTHORSIGNATURE_FIELD)
    private String authorSignature;
    @JsonProperty(FORWARDSIGNATURE_FIELD)
    private String forwardSignature; ///< Optional. Post author signature for messages forwarded from channel chats
    @JsonProperty(MEDIAGROUPID_FIELD)
    private String mediaGroupId; ///< Optional. The unique identifier of a media message group this message belongs to
    @JsonProperty(CONNECTEDWEBSITE_FIELD)
    private String connectedWebsite; ///< Optional. The domain name of the website on which the user has logged in
    @JsonProperty(FORWARDSENDERNAME_FIELD)
    private String forwardSenderName; ///< Optional. Sender's name for messages forwarded from users who disallow adding a link to their account in forwarded messages.
    @JsonProperty(POLL_FIELD)
    private Poll poll; ///< Optional. Message is a native poll, information about the poll
    /**
     * 内嵌键盘附加到消息。
     *
     * @apiNote login_url 按钮表示为普通的 url 按钮。
     */
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup;
    @JsonProperty(VIABOT_FIELD)
    private User viaBot; // Optional. Bot through which the message was sent
    /**
     * 可选的。
     * 消息的发送者，代表聊天发送。 用于通道消息的通道本身。
     * 来自匿名组管理员的消息的超级组本身。
     * 自动转发到讨论组的消息链接频道
     */
    @JsonProperty(SENDERCHAT_FIELD)
    private Chat senderChat;
    /**
     * 可选的。
     * True，如果消息是自动转发到连接的讨论组的频道帖子
     */
    private Boolean isAutomaticForward;
    @JsonProperty(HASPROTECTEDCONTENT_FIELD)
    private Boolean hasProtectedContent; ///< Optional. True, if the message can't be forwarded

    public List<MessageEntity> getEntities() {
        if (entities != null) {
            entities.forEach(x -> x.computeText(text));
        }
        return entities;
    }

    public List<MessageEntity> getCaptionEntities() {
        if (captionEntities != null) {
            captionEntities.forEach(x -> x.computeText(caption));
        }
        return captionEntities;
    }

    @JsonIgnore
    public List<User> getNewChatMembers() {
        return newChatMembers == null ? new ArrayList<>() : newChatMembers;
    }

    @JsonIgnore
    public boolean isGroupMessage() {
        return chat.isGroupChat();
    }

    @JsonIgnore
    public boolean isUserMessage() {
        return chat.isUserChat();
    }

    @JsonIgnore
    public boolean isChannelMessage() {
        return chat.isChannelChat();
    }

    @JsonIgnore
    public boolean isSuperGroupMessage() {
        return chat.isSuperGroupChat();
    }

    @JsonIgnore
    public Long getChatId() {
        return chat.getId();
    }

    @JsonIgnore
    public boolean hasText() {
        return text != null && !text.isEmpty();
    }

    @JsonIgnore
    public boolean isCommand() {
        if (hasText() && entities != null) {
            for (MessageEntity entity : entities) {
                if (entity != null && entity.getOffset() == 0 &&
                        EntityType.BOTCOMMAND.equals(entity.getType())) {
                    return true;
                }
            }
        }
        return false;
    }

    @JsonIgnore
    public boolean hasNewChatMembers() {
        return !CollectionUtils.isEmpty(this.newChatMembers);
    }

    @JsonIgnore
    public boolean hasDocument() {
        return this.document != null;
    }

    @JsonIgnore
    public boolean isReply() {
        return this.replyToMessage != null;
    }

    @JsonIgnore
    public boolean hasEntities() {
        return entities != null && !entities.isEmpty();
    }

    @JsonIgnore
    public boolean hasAnimation() {
        return animation != null;
    }

    @JsonIgnore
    public boolean hasPoll() {
        return poll != null;
    }

    @JsonIgnore
    public boolean hasViaBot() {
        return viaBot != null;
    }

    @JsonIgnore
    public boolean hasReplyMarkup() {
        return replyMarkup != null;
    }

}
