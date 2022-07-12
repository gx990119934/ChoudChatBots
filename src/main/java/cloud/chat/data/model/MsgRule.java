package cloud.chat.data.model;

import cloud.chat.common.DbPageParameter;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "msg_rule")
public class MsgRule extends DbPageParameter implements Serializable {
    @Id
    private Long id;

    @Column(name = "msg_key")
    private String msgKey;

    @Column(name = "parse_mode")
    private String parseMode;

    private String token;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private Integer flag;

    @Column(name = "msg_value")
    private String msgValue;

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
     * @return msg_key
     */
    public String getMsgKey() {
        return msgKey;
    }

    /**
     * @param msgKey
     */
    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }

    /**
     * @return parse_mode
     */
    public String getParseMode() {
        return parseMode;
    }

    /**
     * @param parseMode
     */
    public void setParseMode(String parseMode) {
        this.parseMode = parseMode;
    }

    /**
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
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

    /**
     * @return msg_value
     */
    public String getMsgValue() {
        return msgValue;
    }

    /**
     * @param msgValue
     */
    public void setMsgValue(String msgValue) {
        this.msgValue = msgValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", msgKey=").append(msgKey);
        sb.append(", parseMode=").append(parseMode);
        sb.append(", token=").append(token);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", flag=").append(flag);
        sb.append(", msgValue=").append(msgValue);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}