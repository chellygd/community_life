package com.wkrj.core.component.log;

import java.lang.annotation.*;

/**
 * 操作日志记录注解，自定义标签名
 * （注解后，会把操作日志记录到数据库）
 * @author ziro
 * @date 2019/8/7 16:56
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WkrjLogInfo {
    // 方法路径
    String logmethod() default "";
    // 方法描述
    String logmsg() default "";
}
