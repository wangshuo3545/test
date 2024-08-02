package cn.exrick.xboot.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志自定义注解
 * @author Exrick
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QuartzRecordLog {

}
