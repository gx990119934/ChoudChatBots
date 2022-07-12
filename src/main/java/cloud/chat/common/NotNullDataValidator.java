package cloud.chat.common;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Slf4j
public class NotNullDataValidator {

    public static Result check(Method method, Object[] args) {
        if (args.length == 0) return Result.success();

        Annotation[] annotations = method.getAnnotationsByType(NotNull.class);
        if (annotations == null || annotations.length == 0) return Result.success();

        for (Annotation a : annotations) {
            NotNull rule = (NotNull) a;

            try {
                int argIndex = rule.argIndex();
                String fieldName = rule.field();

                if (argIndex >= args.length) {
                    return new Result<>(900, "指定的argIndex：" + argIndex + ",超出了参数长度：" + args.length);
                }
                Object targetObj = args[argIndex];
                Object value = getFieldValue(targetObj.getClass(), fieldName, targetObj);

                if (value == null) {
                    String message = rule.message();
                    if (message.equals("-")) {
                        String propertyName = rule.name();
                        if (propertyName.equals("-")) {
                            message = "不能有空值！";
                        } else {
                            message = propertyName + "不能为空！";
                        }
                    }
                    return new Result<>(rule.statusCode(), message);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return new Result<>();
    }

    private static Object getFieldValue(Class<?> clazz, String fieldName, Object obj) throws NoSuchFieldException, IllegalAccessException {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException e) {
            Class<?> superclass = clazz.getSuperclass();
            if (superclass == Object.class) {
                throw e;
            } else {
                return getFieldValue(superclass, fieldName, obj);
            }
        }
    }
}
