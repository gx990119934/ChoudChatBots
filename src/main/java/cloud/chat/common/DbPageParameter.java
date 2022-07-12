package cloud.chat.common;

import lombok.Data;
import tk.mybatis.mapper.entity.IDynamicTableName;

import javax.persistence.Transient;

/**
 * @author gx
 */
@Data
public class DbPageParameter implements IDynamicTableName {

    @Transient
    private Integer pageNum;

    @Transient
    private Integer pageSize;

    @Transient
    private String dynamicTableName;

    @Transient
    private Integer beginIndex;

    @Transient
    private Integer total;

    @Transient
    private Integer maxSize = 2000;

}
