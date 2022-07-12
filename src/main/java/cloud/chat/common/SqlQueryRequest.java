package cloud.chat.common;

import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

/**
 * @ProjectName: monkey-java
 * @Package: monkey.common
 * @ClassName: SqlQueryRequest
 * @Author: lv.lin
 * @Description: 通用sql查询请求
 * @Date: 2020/8/20 11:02
 * @Version: 1.0
 */
public class SqlQueryRequest extends DbPageParameter{

    private Long Id ;
    private List<Where> where ;
    private List<Order> orderBy ;
    private Page page ;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public List<Where> getWhere() {
        return where;
    }

    public void setWhere(List<Where> where) {
        this.where = where;
    }

    public List<Order> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(List<Order> orderBy) {
        this.orderBy = orderBy;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public static class Where{
        private String field ;
        // 连接符  >,=,<,>=,<=,like,in,not in
        private String comparator ;
        private Object value ;
        // 是否使用or连接，默认false
        private Boolean or ;
        // 值是否是String类型
        private boolean isString;
        private boolean isDate ;

        //分组，当存在分组时，只有or属性还有效，其他字段无效
        private List<Where> group ;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getComparator() {
            if(StringUtil.isEmpty(comparator)){
                comparator = "=" ;
            }
            return comparator;
        }

        public void setComparator(String comparator) {
            this.comparator = comparator;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Boolean getOr() {
            if(or==null){
                or = false ;
            }
            return or;
        }

        public void setOr(Boolean or) {
            this.or = or;
        }

        public boolean getIsString() {
            return isString;
        }

        public void setIsString(boolean isString) {
            this.isString = isString;
        }

        public List<Where> getGroup() {
            return group;
        }

        public void setGroup(List<Where> group) {
            this.group = group;
        }

        public boolean getIsDate(){
            return isDate ;
        }
        public void setIsDate(boolean isDate){
            this.isDate = isDate ;
        }

    }

    public static class Order{
        private String field ;
        private Boolean desc ;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Boolean getDesc() {
            return desc;
        }

        public void setDesc(Boolean desc) {
            this.desc = desc;
        }
    }

    public static class Page{
        private int num ;
        private int size ;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

}
