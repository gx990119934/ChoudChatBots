package cloud.chat.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author gx
 */
@Component
@Aspect
public class NotNullCommandExecutor {

    //使用该方法，当出现多个注解时，无法切入
    //@Around("@annotation(monkey.common.NotNull)")

    //暂时直接修改为切入controller和service层中
    @Around("execution(public * *..service..*.*(..)) || execution(public * *..*Controller.*(..))")
    public Object controllerExecute(ProceedingJoinPoint point) throws Throwable{
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Result result = NotNullDataValidator.check(method, point.getArgs());
        if (result.isSuccess()) {
            return point.proceed();
        } else {
            return result;
        }
    }
}
