package cloud.chat.common;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import lombok.NonNull;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.util.StringUtil;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.lang.reflect.*;
import java.util.*;

public class DbServiceImpl<T> implements DbService<T> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected DbMapper<T> mapper;

    @Autowired
    protected ObjectIdGenerator objectIdGenerator;

    protected String idMethod = "setId";
    protected String getIdMethod = "getId";
    protected String flagMethod = "setFlag";
    protected String updateTimeMethod = "setUpdateTime";
    protected String createTimeMethod = "setCreateTime";
    protected String createTimeField = "createTime";

    protected String searchCodeField = "searchCode";


    /**
     * 处理模糊查询字符
     *
     * @param character
     * @return
     */
    public static String processLikeCharacter(String character) {
        if (!StringUtil.isEmpty(character)) {
            return character.replaceAll("\\%", "\\\\%")
                    .replaceAll("\\_", "\\\\_");
        }
        return character;
    }

    public Result<T> insert(@NonNull T t) {
        t = setDefaultFieldValues(t);
        setSearchCode(t);
        int i = mapper.insert(t);
        if (i == 1) {
            return new Result<>(t);
        }
        return fail();
    }


    public Result<Long> insertOne(@NonNull T t) {
        int i = mapper.insert(t);
        if (i == 1) {
            return new Result<>();
        }
        return fail();
    }

    public Result insertBatch(@NonNull List<T> list) {
        list.stream().forEach(l -> {
            l = setDefaultFieldValues(l);
        });

        int i = mapper.insertBatch(list);
        if (i >= 1) {
            return new Result();
        }
        return fail();
    }

    public Result update(@NonNull T t) {
        setFieldValue(t, this.updateTimeMethod, Calendar.getInstance().getTime());
        setSearchCode(t);
        int i = mapper.updateByPrimaryKeySelectiveField(t);
        if (i == 1) {
            return new Result();
        }
        return fail();
    }

    @Override
    public Result updateAll(@NonNull T t) {
        setFieldValue(t, this.updateTimeMethod, Calendar.getInstance().getTime());
        setSearchCode(t);
        int i = mapper.updateByPrimaryKey(t);
        if (i == 1) {
            return new Result();
        }
        return fail();
    }

    @Override
    public Result updateBatch(@NonNull List<T> list) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        int i = mapper.updateBatch(map);
        if (i >= 1) {
            return new Result();
        }
        return fail();
    }

    public Result delete(@NonNull T t) {
        setFieldValue(t, this.flagMethod, 0);
        int i = mapper.updateByPrimaryKeySelective(t);
        if (i == 1) {
            return new Result();
        }
        return fail();
    }

    public Result destroy(@NonNull T t) {
        int i = mapper.delete(t);
        if (i == 1) {
            return new Result();
        }
        return fail();
    }

    public Result destroyBatch(@NonNull List<Long> ids) {
        Weekend<T> weekend = Weekend.of(getGenericClass());
        weekend.weekendCriteria().andIn("id", ids);
        int i = mapper.deleteByExample(weekend);
        if (i >= 1) {
            return new Result();
        }
        return fail();
    }

    public Result<T> selectByPrimaryKey(@NonNull Long id) {
        return new Result<>(mapper.selectByPrimaryKey(id));
    }

    public Result<Integer> selectCount(T query) {
        setFieldValue(query, this.flagMethod, 1);
        Weekend<T> weekend = getWeekend(query);
        return selectCount(weekend);
    }

    public Result<Integer> selectCount(Weekend<T> weekend) {
        int count = mapper.selectCountByExample(weekend);
        return Result.success(count);
    }

    /**
     * Description: 单个查询，所有查询默认查询flag=1的数据
     * Author: ChenQ
     * Date: 2020/4/28
     */
    public Result<T> selectOne(@NonNull T query) {
        setFieldValue(query, this.flagMethod, 1);
        Weekend<T> weekend = getWeekend(query);
        return selectOne(weekend);
    }

    public Result<T> selectOne(@NonNull Weekend<T> query) {
        return new Result<>(mapper.selectOneByExample(query));
    }

    /**
     * Description: 列表查询，默认按照createTime字段倒序查询
     * Author: ChenQ
     * Date: 2020/4/28
     */
    public Result<List<T>> select(@NonNull T query) {
        setFieldValue(query, this.flagMethod, 1);
        return select(query, this.createTimeField);
    }

    public Result<List<T>> select(@NonNull Weekend<T> query) {
        return select(query, null, true);
    }

    /**
     * Description: 列表查询，根据指定的字段倒序排序
     * Author: ChenQ
     * Date: 2020/4/28
     */
    public Result<List<T>> select(@NonNull T query, @NonNull String orderField) {
        return select(query, orderField, true);
    }

    /**
     * Description: 列表查询，根据指定的字段和传入的排序方法进行排序
     * Author:Lv.Lin
     *
     * @param query
     * @param orderField
     * @param isDesc
     * @return
     */
    public Result<List<T>> select(@NonNull T query, @NonNull String orderField, boolean isDesc) {
        setFieldValue(query, this.flagMethod, 1);
        Weekend<T> weekend = getWeekend(query);
        return select(weekend, orderField, isDesc);
    }

    public Result<List<T>> select(@NonNull Weekend<T> weekend, String orderField, boolean isDesc) {
        if (weekend == null) {
            return fail();
        }
        if (StringUtil.isNotEmpty(orderField)) {
            if (isDesc) {
                weekend.orderBy(orderField).desc();
            } else {
                weekend.orderBy(orderField).asc();
            }
        }
        List<T> result = mapper.selectByExample(weekend);
        return new Result<>(result);
    }

    public Result<List<T>> selectAll() {
        return new Result<>(mapper.selectAll());
    }

    /**
     * Description: 分页查询，默认按照createTime字段倒序查询
     * Author: ChenQ
     * Date: 2020/4/28
     */
    public Result<PageInfo<T>> selectPage(@NonNull T query, @NonNull int pageNum, @NonNull int pageSize) {
        setFieldValue(query, this.flagMethod, 1);
        return selectPage(query, pageNum, pageSize, this.createTimeField);
    }

    /**
     * Description: 分页查询，根据指定的字段倒序排序
     * Author: ChenQ
     * Date: 2020/4/28
     */
    public Result<PageInfo<T>> selectPage(@NonNull T query, @NonNull int pageNum, @NonNull int pageSize, @NonNull String orderField) {
        setFieldValue(query, this.flagMethod, 1);
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize < 1) {
            pageSize = 1;
        }
        Weekend<T> weekend = getWeekend(query, orderField);
        if (weekend == null) {
            return fail();
        }
        return selectPage(weekend, pageNum, pageSize, orderField, true);
    }

    /**
     * Description: 分页查询，默认按照createTime字段倒序查询
     * Author: ChenQ
     * Date: 2020/4/28
     */
    public Result<PageInfo<T>> selectPage(@NonNull Weekend<T> weekend, @NonNull int pageNum, @NonNull int pageSize) {
        return selectPage(weekend, pageNum, pageSize, this.createTimeField, true);
    }


    /**
     * Description: 分页查询，根据指定的字段排序
     * Author: ChenQ
     * Date: 2020/4/28
     */
    public Result<PageInfo<T>> selectPage(@NonNull Weekend<T> weekend, @NonNull int pageNum, @NonNull int pageSize, @NonNull String orderField,
                                          @NonNull boolean isDesc) {
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize < 1) {
            pageSize = 1;
        }
        if (isDesc) {
            weekend.orderBy(orderField).desc();
        } else {
            weekend.orderBy(orderField).asc();
        }
        PageRowBounds pageRowBounds = new PageRowBounds((pageNum - 1) * pageSize, pageSize);
        List<T> result = mapper.selectByExampleAndRowBounds(weekend, pageRowBounds);
        PageInfo<T> pageInfo = new PageInfo<>(result);
        return new Result<>(pageInfo);
    }

    /**
     * @param sqlQueryRequest description 分页查询，根据标准的查询条件
     *                        Author: lv.lin
     *                        Date : 2020-8-20
     * @return
     */
    public Result<PageInfo<T>> selectPage(SqlQueryRequest sqlQueryRequest) {
        Weekend weekend = new Weekend(getType());

        //解析
        SqlQueryRequestExecutor.analysisSqlQueryRequest(weekend, sqlQueryRequest);

        SqlQueryRequest.Page page = sqlQueryRequest.getPage();
        return selectPage(weekend, page.getNum(), page.getSize());
    }

    /**
     * 解析成weekend，方便开发者后续继续操作，分页未加入
     *
     * @param sqlQueryRequest
     * @return
     */
    public Weekend createQuery(SqlQueryRequest sqlQueryRequest) {
        Weekend weekend = new Weekend(getType());
        //解析
        SqlQueryRequestExecutor.analysisSqlQueryRequest(weekend, sqlQueryRequest);

        return weekend;
    }


    /**
     * 获取泛型类型
     *
     * @return
     */
    private Class<T> getType() {
        try {
            ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
            Class<T> type = (Class<T>) superClass.getActualTypeArguments()[0];
            return type;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }


    protected void setFieldValue(T obj, String fieldName, Object value) {
        Class<?> clazz = obj.getClass();
        try {
            Method method = clazz.getMethod(fieldName, value.getClass());
            if (method == null) {
                return;
            }
            if (fieldName.equals(idMethod)) {
                Method getMethod = clazz.getMethod(getIdMethod);
                Object id = getMethod.invoke(obj);
                if (id == null) {
                    method.invoke(obj, value);
                }
            } else {
                method.invoke(obj, value);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }
    }

    protected Weekend<T> getWeekend(T query) {
        return createQuery(query);
    }

    protected Weekend<T> getWeekend(T query, String orderField) {
        return createQuery(query, orderField);
    }

    protected Weekend<T> getWeekend(T query, String orderField, boolean isDesc) {
        return createQuery(query, orderField, isDesc);
    }

    protected T setDefaultFieldValues(@NonNull T t) {
        long id = objectIdGenerator.newId();
        setFieldValue(t, this.idMethod, id);
        setFieldValue(t, this.flagMethod, 1);
        setFieldValue(t, this.createTimeMethod, Calendar.getInstance().getTime());
        return t;
    }

    /**
     * 对搜索字段searchCode进行赋值，默认根据name字段
     *
     * @param t
     */
    protected void setSearchCode(@NonNull T t) {
        setSearchCode(t, "name");
    }

    /**
     * 对搜索字段searchCode进行赋值
     *
     * @param t         实体对象
     * @param nameField 名称字段
     */
    protected void setSearchCode(@NonNull T t, String nameField) {
        if (t == null) return;

        Map<String, Field> entityFields = new HashMap<>();
        //判断nameField字段和searchCode字段
        Class<?> clazz = t.getClass();

        List<Field> allField = getAllField(clazz);
        for (Field field : allField) {
            field.setAccessible(true);
            entityFields.put(field.getName(), field);
        }

        if (entityFields.containsKey(nameField) && entityFields.containsKey(this.searchCodeField)) {

            //判断字段是否为空
            Field nField = entityFields.get(nameField);
            Field scField = entityFields.get(searchCodeField);
            try {

                //不是字符串字段，不处理
                if (!nField.getType().equals(String.class)) return;
                if (!scField.getType().equals(String.class)) return;

                //如果name字段无数据，则不处理
                String nameVal = (String) nField.get(t);
                if (nameVal == null) return;

                //如果 searchCode 有值，则不处理
                String searchCodeVal = (String) scField.get(t);
                if (searchCodeVal != null) return;

                //设置searchCode字段的值
                scField.set(t, ChineseCharacterHelper.getSpells(nameVal));


            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        }

    }

    /**
     * 获取类中所有的字段，包括父类
     *
     * @param clazz
     * @return
     */
    private List<Field> getAllField(Class clazz) {
        List<Field> fields = new ArrayList<>();

        Class tempClazz = clazz;
        while (tempClazz != null) {
            for (Field declaredField : tempClazz.getDeclaredFields()) {
                fields.add(declaredField);
            }
            tempClazz = tempClazz.getSuperclass();
        }

        return fields;
    }


    private Result fail() {
        return fail(null);
    }

    private <T> Result<T> fail(T data) {
        return new Result<>(700, "数据库操作失败！");
    }

    @Override
    public Result<List<T>> selectIn(Class<T> clazz, Collection<Long> idList) {
        return selectIncludeIds(clazz, idList);
    }

    @Override
    public Result<List<T>> selectIn(Class<T> clazz, List<Long> idList) {
        return selectIncludeIds(clazz, idList);
    }

    @Override
    public Result<List<T>> selectIn(Collection<Long> idList) {
        return selectIncludeIds(getType(), idList);
    }

    private Result<List<T>> selectIncludeIds(Class<T> clazz, Collection<Long> idList) {
        Weekend<T> weekend = Weekend.of(clazz);
        WeekendCriteria<T, Object> criteria = weekend.weekendCriteria();
        criteria.andEqualTo("flag", 1).andIn("id", idList);
        return this.select(weekend);
    }

    @Override
    public Result<List<T>> selectNotIn(Class<T> clazz, Collection<Long> idList) {
        return selectExcludeIds(clazz, idList);
    }

    @Override
    public Result<List<T>> selectNotIn(Class<T> clazz, List<Long> idList) {
        return selectExcludeIds(clazz, idList);
    }

    @Override
    public Result<List<T>> selectNotIn(Collection<Long> idList) {
        return selectExcludeIds(getType(), idList);
    }

    private Result<List<T>> selectExcludeIds(Class<T> clazz, Collection<Long> idList) {
        Weekend<T> weekend = Weekend.of(clazz);
        WeekendCriteria<T, Object> criteria = weekend.weekendCriteria();
        criteria.andEqualTo("flag", 1).andNotIn("id", idList);
        return this.select(weekend);
    }

    public <T> Weekend<T> createQuery(T condition, Date beginTime, Date endTime) {
        return createQuery(condition, beginTime, endTime, "createTime");
    }


    protected <T> Weekend<T> createQuery(T condition, Date beginTime, Date endTime, String timePropertyName) {
        Weekend<T> weekend = createQuery(condition, true);
        weekend.and().andBetween(timePropertyName, beginTime, endTime);
        return weekend;
    }

    protected <T> Weekend<T> createQuery(T condition) {
        return createQuery(condition, false);
    }

    protected <T> Weekend<T> createQuery(T condition, boolean excludeTimeProperties) {
        Weekend<T> weekend = new Weekend<>((Class<T>) condition.getClass(), Boolean.TRUE.booleanValue());
        WeekendCriteria<?, Object> criteria = weekend.weekendCriteria();
        try {
            Map<String, Object> describe = PropertyUtils.describe(condition);
            for (Map.Entry<String, Object> entry : describe.entrySet()) {
                Object value = entry.getValue();
                if (value == null || StringUtil.isEmpty(value.toString())) {
                    continue;
                }
                if (excludeTimeProperties) {
                    if (value instanceof Date) {
                        continue;
                    }
                }
                if (value instanceof Class) {
                    continue;
                }
                if ("pageNum".equals(entry.getKey())) {
                    continue;
                }
                if ("pageSize".equals(entry.getKey())) {
                    continue;
                }
                if ("total".equals(entry.getKey())) {
                    continue;
                }
                if ("beginIndex".equals(entry.getKey())) {
                    continue;
                }
                if ("maxSize".equals(entry.getKey())) {
                    continue;
                }

                criteria.andEqualTo(entry.getKey(), value);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
            return null;
        }

        return weekend;
    }

    protected <T> Weekend<T> createQuery(T condition, String orderField) {
        return createQuery(condition, orderField, true);
    }

    protected <T> Weekend<T> createQuery(T condition, String orderField, boolean isDesc) {
        Weekend<T> weekend = createQuery(condition);
        if (isDesc) {
            weekend.orderBy(orderField).desc();
        } else {
            weekend.orderBy(orderField).asc();
        }
        return weekend;
    }

    public Class<T> getGenericClass() {
        Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return (Class<T>) type;
    }
}
