package cloud.chat.common;

import java.lang.annotation.*;

/**
 * @author gx
 * 注解： 禁止空值
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(value = NotNullValidators.class)
public @interface NotNull {

    //字段：必填，属性英文名
    public String field() default "-";

    //名称：选填，属性中文名
    public String name() default "-";

    //状态码：必填
    public int statusCode() default 709;

    //消息：选填
    public String message() default "-";

    //目标对比序号
    int argIndex() default 0 ;
}
