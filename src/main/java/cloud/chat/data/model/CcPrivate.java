package cloud.chat.data.model;

import cloud.chat.common.DbPageParameter;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "cc_private")
public class CcPrivate extends DbPageParameter implements Serializable {
    @Id
    private Long id;

    /**
     * chatid即用户id
     */
    @Column(name = "chat_id")
    private Long chatId;

    /**
     * cc号
     */
    private String cc;

    /**
     * 个人名称或者群名称
     */
    private String name;

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
     * 获取chatid即用户id
     *
     * @return chat_id - chatid即用户id
     */
    public Long getChatId() {
        return chatId;
    }

    /**
     * 设置chatid即用户id
     *
     * @param chatId chatid即用户id
     */
    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    /**
     * 获取cc号
     *
     * @return cc - cc号
     */
    public String getCc() {
        return cc;
    }

    /**
     * 设置cc号
     *
     * @param cc cc号
     */
    public void setCc(String cc) {
        this.cc = cc;
    }

    /**
     * 获取个人名称或者群名称
     *
     * @return name - 个人名称或者群名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置个人名称或者群名称
     *
     * @param name 个人名称或者群名称
     */
    public void setName(String name) {
        this.name = name;
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
        sb.append(", cc=").append(cc);
        sb.append(", name=").append(name);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", flag=").append(flag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}