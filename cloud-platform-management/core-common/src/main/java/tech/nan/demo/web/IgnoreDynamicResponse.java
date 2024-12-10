package tech.nan.demo.web;

import java.lang.annotation.*;

/**
 * 注解不需要统一包装返回值的方法
 * @author Nan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface IgnoreDynamicResponse {
}
