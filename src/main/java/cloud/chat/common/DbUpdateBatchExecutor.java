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
public class DbUpdateBatchExecutor extends MapperTemplate {

    public DbUpdateBatchExecutor(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String updateBatch(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, this.tableName(entityClass)));
        sql.append("<trim prefix=\"set\" suffixOverrides=\",\">");
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        Iterator<EntityColumn> iterator = columnList.iterator();
        while (iterator.hasNext()) {

            EntityColumn column = iterator.next();
            if (column.getProperty().equals("id")) {
                continue;
            }

            StringBuilder setSql = new StringBuilder();
            setSql.append("<trim prefix=\"").append(column.getColumn()).append(" =case\" suffix=\"end,\">");
            setSql.append("<foreach collection=\"list\" item=\"item\" index=\"index\">");
            setSql.append("<if test=\"");
            setSql.append("item." + column.getProperty());
            setSql.append("!=null\">");
            setSql.append(" when id=#{item.id} then ").append(column.getColumnHolder("item"));
            setSql.append("</if>");
            setSql.append("</foreach>");
            setSql.append("</trim>");
            sql.append(setSql);
        }
        sql.append("</trim>");
        sql.append("where id in");
        sql.append("<foreach collection=\"list\" item=\"record\" index=\"index\" separator=\",\" open=\"(\" close=\")\">");
        sql.append("#{record.id,jdbcType=BIGINT}");
        sql.append("</foreach>");
        return sql.toString();
    }
}
