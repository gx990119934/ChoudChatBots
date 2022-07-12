package cloud.chat.common;

import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.Map;

/**
 * @author gx
 */
@RegisterMapper
public interface DbUpdateBatchMapper<T> {
    @UpdateProvider(
            type = DbUpdateBatchExecutor.class,
            method = "dynamicSQL"
    )
    int updateBatch(Map<String, Object> map);
}
