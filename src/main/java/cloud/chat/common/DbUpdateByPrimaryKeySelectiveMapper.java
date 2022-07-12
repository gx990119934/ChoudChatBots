package cloud.chat.common;

import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface DbUpdateByPrimaryKeySelectiveMapper<T> {

    /**
     * 根据主键更新属性不为null的值，带有AllowPostEmpty注解的字段会更新null值
     *
     * @param record
     * @return
     */
    @UpdateProvider(type = DbUpdateByPrimaryKeySelectiveProvider.class, method = "dynamicSQL")
    int updateByPrimaryKeySelectiveField(T record);
}
