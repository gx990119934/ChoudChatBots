package cloud.chat.data.model;

import cloud.chat.common.DbPageParameter;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "group_channel_index")
public class GroupChannelIndex extends DbPageParameter implements Serializable {
    @Id
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    /**
     * 类型，如channel，group，supergroup
     */
    private String type;

    /**
     * 图标
     */
    private String icon;

    /**
     * 群名称
     */
    private String title;

    /**
     * 链接
     */
    private String url;

    /**
     * 成员数
     */
    @Column(name = "member_count")
    private Integer memberCount;

    /**
     * 权重，1-7，7为最高
     */
    private Integer weights;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private Integer flag;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return chat_id
     */
    public Long getChatId() {
        return chatId;
    }

    /**
     * @param chatId
     */
    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    /**
     * 获取类型，如channel，group，supergroup
     *
     * @return type - 类型，如channel，group，supergroup
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型，如channel，group，supergroup
     *
     * @param type 类型，如channel，group，supergroup
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取图标
     *
     * @return icon - 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标
     *
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取群名称
     *
     * @return title - 群名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置群名称
     *
     * @param title 群名称
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取链接
     *
     * @return url - 链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置链接
     *
     * @param url 链接
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取成员数
     *
     * @return member_count - 成员数
     */
    public Integer getMemberCount() {
        return memberCount;
    }

    /**
     * 设置成员数
     *
     * @param memberCount 成员数
     */
    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    /**
     * 获取权重，1-7，7为最高
     *
     * @return weights - 权重，1-7，7为最高
     */
    public Integer getWeights() {
        return weights;
    }

    /**
     * 设置权重，1-7，7为最高
     *
     * @param weights 权重，1-7，7为最高
     */
    public void setWeights(Integer weights) {
        this.weights = weights;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return flag
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     * @param flag
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", chatId=").append(chatId);
        sb.append(", type=").append(type);
        sb.append(", icon=").append(icon);
        sb.append(", title=").append(title);
        sb.append(", url=").append(url);
        sb.append(", memberCount=").append(memberCount);
        sb.append(", weights=").append(weights);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", flag=").append(flag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}