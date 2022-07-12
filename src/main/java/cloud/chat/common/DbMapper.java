package cloud.chat.common;

import tk.mybatis.mapper.common.Mapper;

/**
 * Description: Mapper基类
 */
public interface DbMapper<T> extends Mapper<T>,DbUpdateByPrimaryKeySelectiveMapper<T>,DbInsertBatchMapper<T>, DbUpdateBatchMapper<T>  {
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}
