package cloud.chat.common;

import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

/**
 * @author gx
 */
@RegisterMapper
public interface DbInsertBatchMapper<T> {
    @InsertProvider(
            type = DbInsertBatchExecutor.class,
            method = "dynamicSQL"
    )
    int insertBatch(List<T> list);
}
