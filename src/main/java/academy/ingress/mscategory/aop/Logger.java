package academy.ingress.mscategory.aop;

import academy.ingress.mscategory.annotation.IgnoreLogging;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class Logger {

  @Before("@within(academy.ingress.mscategory.annotation.Loggable) && execution(* academy.ingress.mscategory.service.concretes.*.*(..))")
  public void logMethodStart(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().getName();
    log.info("ActionLog." + methodName + ".Start: " + Arrays.stream(joinPoint.getArgs()).toList());
  }

  @After("@within(academy.ingress.mscategory.annotation.Loggable) && execution(* academy.ingress.mscategory.service.concretes.*.*(..))")
  public void logMethodEnd(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().getName();
    log.info("ActionLog." + methodName + ".End");
  }

  @AfterReturning(pointcut =
      "@within(academy.ingress.mscategory.annotation.Loggable) && execution(* academy.ingress.mscategory.service"
          + ".concretes.*.*(..))", returning = "result")
  public void logMethodReturn(JoinPoint joinPoint, Object result) {
    String methodName = joinPoint.getSignature().getName();

    var fields = checkFieldsForAnnotation(result);
    log.info("ActionLog." + methodName + ".Return: " + fields);
  }

  @AfterThrowing(pointcut =
      "@within(academy.ingress.mscategory.annotation.Loggable) && execution(* academy.ingress.mscategory.service"
          + ".concretes.*.*(..))", throwing = "exception")
  public void logMethodException(JoinPoint joinPoint, Exception exception) {
    String methodName = joinPoint.getSignature().getName();
    log.error("ActionLog. " + methodName + ".Error" + ": " + exception.getMessage());
  }

  private Map<String, String> checkFieldsForAnnotation(Object object) {
    Map<String, String> map = new HashMap<>();
    if (object == null) {
      return map;
    }
    Class<?> clazz = object.getClass();
    for (Field field : clazz.getDeclaredFields()) {
      if (AnnotationUtils.findAnnotation(field, IgnoreLogging.class) == null) {
        try {
          field.setAccessible(true);
          map.put(field.getName(), field.get(object).toString());
        } catch (Exception ignore) {

        }
      }
    }
    return map;
  }

}
