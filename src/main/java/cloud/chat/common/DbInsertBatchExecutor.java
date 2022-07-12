package cloud.chat.common;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Iterator;
import java.util.Set;


/**
 * @author gx
 */
public class DbInsertBatchExecutor extends MapperTemplate {

    public DbInsertBatchExecutor(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String insertBatch(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.insertIntoTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, false, false, false));
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\" union all \" >");
        sql.append("select ") ;
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        Iterator var5 = columnList.iterator();
        while(var5.hasNext()) {
            EntityColumn column = (EntityColumn)var5.next();
            if (column.isInsertable()) {
                sql.append(column.getColumnHolder("record"));
                if(var5.hasNext()){
                    sql.append(",") ;
                }
            }
        }
        sql.append(" from dual ") ;
        sql.append("</foreach>");
        return sql.toString();
    }

    public String insertUseGeneratedKeys(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.insertIntoTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, false, false, false));
        sql.append(SqlHelper.insertValuesColumns(entityClass, false, false, false));
        return sql.toString();
    }
}
