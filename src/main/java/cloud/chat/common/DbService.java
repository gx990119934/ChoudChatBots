package cloud.chat.common;

import com.github.pagehelper.PageInfo;
import lombok.NonNull;
import tk.mybatis.mapper.weekend.Weekend;

import java.util.Collection;
import java.util.List;

/***
 * 数据库服务接口：基于数据库访问的服务，必须要实现的接口。
 * @author gx
 * @param <T> 类型：数据模型类型，即数据实体类型。
 */
public interface DbService<T> {

    /***
     * 插入：不需要设置主键值。
     * @param t 数据模型对象。
     * @return 含有id的数据模型对象。
     */
    Result<T> insert(@NonNull T t);

    /***
     * 插入一条：需要设置主键值。
     * @param t 数据模型对象。
     * @return
     */
    Result<Long> insertOne(@NonNull T t);

    /***
     * 批量插入
     * @param list 插入表：每条记录需要设置主键值。
     * @return
     */
    Result insertBatch(@NonNull List<T> list);

    /***
     * 更新：以主键作为条件，非空字段才会更新。
     * @param t
     * @return
     */
    Result update(@NonNull T t);

    /***
     * 更新：以主键作为条件，所有字段都会更新。
     * @param t
     * @return
     */
    Result updateAll(@NonNull T t);

    Result updateBatch(@NonNull List<T> list);

    /***
     * 删除：以主键为条件，执行逻辑删除。
     * @param t
     * @return
     */
    Result delete(@NonNull T t);

    /***
     * 销毁：以主键为条件，执行物理删除。
     * @param t
     * @return
     */
    Result destroy(@NonNull T t);

    /***
     * 批量销毁：以主键为条件，执行物理删除。
     * @param ids
     * @return
     */
    Result destroyBatch(@NonNull List<Long> ids);

    /***
     * 查询一条记录。
     * @param query 非空字段作为条件。
     * @return
     */
    Result<T> selectOne(@NonNull T query);

    /***
     * 查询一条记录。
     * @param id 主键。
     * @return
     */
    Result<T> selectByPrimaryKey(@NonNull Long id);

    /***
     * 查询记录数。
     * @param query 非空字段作为条件。
     * @return
     */
    Result<Integer> selectCount(T query);

    /***
     * 查询记录数：自定义条件。
     * @param weekend
     * @return
     */
    Result<Integer> selectCount(Weekend<T> weekend);

    /***
     * 查询列表：无条件查询，返回全部记录。
     * @return
     */
    Result<List<T>> selectAll();

    /***
     * 查询列表。
     * @param query 非空字段作为条件。
     * @return
     */
    Result<List<T>> select(@NonNull T query);

    /***
     * 查询列表。
     * @param query  非空字段作为条件。
     * @param orderField 排序字段。
     * @return
     */
    Result<List<T>> select(@NonNull T query, @NonNull String orderField);

    /***
     * 查询列表。
     * @param weekend 样例：灵活定义查询条件
     * @return
     */
    Result<List<T>> select(@NonNull Weekend<T> weekend);

    /***
     * 查询列表。
     * @param weekend 样例：灵活定义查询条件
     * @return
     */
    Result<List<T>> select(@NonNull Weekend<T> weekend, String orderField, boolean isDesc);


    /***
     * 查询分页
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result<PageInfo<T>> selectPage(@NonNull T query, @NonNull int pageNum, @NonNull int pageSize);

    /***
     * 查询分页：支持and关系的多条件查询
     * @param query 非空字段作为条件。
     * @param pageNum 页码：第几页
     * @param pageSize 尺寸：每页记录最大记录数
     * @param orderField：排序字段
     * @return
     */
    Result<PageInfo<T>> selectPage(@NonNull T query, @NonNull int pageNum, @NonNull int pageSize, @NonNull String orderField);

    /***
     * 查询分页：自定义条件
     * @param weekend 查询条件
     * @param pageNum 页码：第几页
     * @param pageSize 尺寸：每页记录最大记录数
     * @return
     */
    Result<PageInfo<T>> selectPage(@NonNull Weekend<T> weekend, @NonNull int pageNum, @NonNull int pageSize);

    /***
     * 查询分页：自定义条件
     * @param weekend 查询条件
     * @param pageNum 页码：第几页
     * @param pageSize 尺寸：每页记录最大记录数
     * @param orderField：排序字段
     * @param isDesc：是否降序
     * @return
     */
    Result<PageInfo<T>> selectPage(@NonNull Weekend<T> weekend, @NonNull int pageNum, @NonNull int pageSize, @NonNull String orderField, @NonNull boolean isDesc);

    /***
     * 查询列表：Id在ID列表中
     * @param clazz 数据模型类型
     * @param idList ID列表
     * @return 数据模型对象列表
     */
    Result<List<T>> selectIn(Class<T> clazz, Collection<Long> idList);

    /**
     * 兼容
     * @param clazz
     * @param idList
     * @return
     */
    Result<List<T>> selectIn(Class<T> clazz, List<Long> idList);
    Result<List<T>> selectIn(Collection<Long> idList);


    /***
     * 查询列表：Id不在ID列表中
     * @param clazz 数据模型类型
     * @param idList ID列表
     * @return 数据模型对象列表
     */
    Result<List<T>> selectNotIn(Class<T> clazz, Collection<Long> idList);

    /**
     * 兼容
     * @param clazz
     * @param idList
     * @return
     */
    Result<List<T>> selectNotIn(Class<T> clazz, List<Long> idList);
    Result<List<T>> selectNotIn(Collection<Long> idList);

}
