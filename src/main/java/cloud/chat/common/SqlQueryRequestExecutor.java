package cloud.chat.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.Weekend;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: monkey-java
 * @Package: monkey.common
 * @ClassName: SqlQueryRequestExecutor
 * @Author: lv.lin
 * @Description:
 * @Date: 2020/8/20 15:14
 * @Version: 1.0
 */
public class SqlQueryRequestExecutor {

    static Logger logger = LoggerFactory.getLogger(SqlQueryRequestExecutor.class) ;
    /**
     * 解析SqlQueryRequest 到Weekend对象中
     * @param weekend
     * @param sqlQueryRequest
     */
    public static void analysisSqlQueryRequest(Weekend weekend , SqlQueryRequest sqlQueryRequest){
        List<SqlQueryRequest.Where> where = sqlQueryRequest.getWhere() ;

        Class type = weekend.getEntityClass() ;

        //条件
        Example.Criteria criteria = weekend.createCriteria();
        analysisSqlQueryRequestWhere(criteria,sqlQueryRequest.getWhere(),type) ;
        //排序
        analysisSqlQueryRequestOrder(weekend,sqlQueryRequest.getOrderBy()) ;

    }

    /**
     * 解析条件
     * @param weekend
     * @param wheres
     */
    private static Example.Criteria analysisSqlQueryRequestWhere(Example.Criteria criteria , List<SqlQueryRequest.Where> wheres, Class type){
        if(CollectionUtils.isEmpty(wheres))return null;

        /*Example.Criteria criteria = weekend.createCriteria() ;

        Class type = weekend.getEntityClass() */;
        wheres.forEach(where->{
            List<SqlQueryRequest.Where> group = where.getGroup();
            if(!CollectionUtils.isEmpty(group)){
                //分组
                analysisSqlQueryRequestWhereGroup(criteria,where,type) ;
            }else {
                //常规
                analysisSqlQueryRequestWhereField(criteria,where,type) ;
            }
        });

        return criteria ;

    }

    private static void analysisSqlQueryRequestWhereField(Example.Criteria criteria , SqlQueryRequest.Where where, Class type){
        String fieldName = where.getField();
        //不存在不处理
        Field field = getField(type, fieldName);
        if(field==null)return;

        String column = getColumn(field) ;
        String comparator = where.getComparator();

        Boolean or = where.getOr();

        Object value = where.getValue();

        //空值，不处理
        if(value==null) return;

        //如果是模糊查询，加%
        if(comparator.toLowerCase().contains("like")){
            value = "%"+value+"%" ;
        }

        if(where.getIsDate()){
            try {
                value = new Date(Long.parseLong(String.valueOf(value))) ;
            }catch (Exception e){
                logger.error(e.toString());
                return;
            }
        }

        if(or){
            criteria.orCondition(column+" "+comparator, value) ;
        }else {
            criteria.andCondition(column+" "+comparator,value) ;
        }



    }

    private static void analysisSqlQueryRequestWhereGroup(Example.Criteria criteria , SqlQueryRequest.Where where , Class type){

        Boolean or = where.getOr();

        if(or){
            criteria.orCondition("(1=1") ;
        }else {
            criteria.andCondition("(1=1") ;
        }

        analysisSqlQueryRequestWhere(criteria,where.getGroup(),type) ;

        criteria.andCondition(" 1=1)") ;

    }
    /**
     * 解析排序
     * @param weekend
     * @param orders
     */
    private static void analysisSqlQueryRequestOrder(Weekend weekend , List<SqlQueryRequest.Order> orders){
        Class type = weekend.getEntityClass() ;
        if(!CollectionUtils.isEmpty(orders)){
            orders.forEach(order->{
                boolean hasField = hasField(type, order.getField());
                if(hasField){
                    Example.OrderBy orderBy1 = weekend.orderBy(order.getField());
                    if(order.getDesc()!=null&&order.getDesc()){
                        orderBy1.desc() ;
                    }
                }
            });
        }
    }

    private static boolean hasField(Class type , String fieldName){
        Field field = ReflectionUtils.findField(type, fieldName);
        if(field==null){
            logger.error("类:{}中，不存在字段:{}",type,fieldName);
            return false ;
        }else {
            return true ;
        }
    }
    private static Field getField(Class type , String fieldName){
        Field field = ReflectionUtils.findField(type, fieldName);
        return field ;
    }

    /**
     * 获取实体中数据库实际的字段名
     * @param field
     * @return
     */
    private static String getColumn(Field field){
        Column annotation = field.getAnnotation(Column.class);

        if(annotation==null)return field.getName() ;

        return annotation.name() ;
    }


}
