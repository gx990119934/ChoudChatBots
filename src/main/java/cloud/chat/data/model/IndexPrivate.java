package cloud.chat.data.model;

import cloud.chat.common.DbPageParameter;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "index_private")
public class IndexPrivate extends DbPageParameter implements Serializable {
    @Id
    private Long id;

    @Column(name = "index_id")
    private Long indexId;

    @Column(name = "private_id")
    private Long privateId;

    /**
     * 用户id
     */
    @Column(name = "private_chat_id")
    private Long privateChatId;

    /**
     * creator:创建这，admin：管理员
     */
    private String status;

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
     * @return index_id
     */
    public Long getIndexId() {
        return indexId;
    }

    /**
     * @param indexId
     */
    public void setIndexId(Long indexId) {
        this.indexId = indexId;
    }

    /**
     * @return private_id
     */
    public Long getPrivateId() {
        return privateId;
    }

    /**
     * @param privateId
     */
    public void setPrivateId(Long privateId) {
        this.privateId = privateId;
    }

    /**
     * 获取用户id
     *
     * @return private_chat_id - 用户id
     */
    public Long getPrivateChatId() {
        return privateChatId;
    }

    /**
     * 设置用户id
     *
     * @param privateChatId 用户id
     */
    public void setPrivateChatId(Long privateChatId) {
        this.privateChatId = privateChatId;
    }

    /**
     * 获取creator:创建这，admin：管理员
     *
     * @return status - creator:创建这，admin：管理员
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置creator:创建这，admin：管理员
     *
     * @param status creator:创建这，admin：管理员
     */
    public void setStatus(String status) {
        this.status = status;
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
        sb.append(", indexId=").append(indexId);
        sb.append(", privateId=").append(privateId);
        sb.append(", privateChatId=").append(privateChatId);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", flag=").append(flag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}