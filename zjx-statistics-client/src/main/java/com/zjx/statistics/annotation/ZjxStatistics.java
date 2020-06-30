package com.zjx.statistics.annotation;

import java.lang.annotation.*;

/**
 * @author Aaron
 * @date 2020/6/10 10:57 上午
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZjxStatistics {
    /**
     * 统计的唯一标识（目前是 userId）
     *
     * @return
     */
    String key();

    /**
     * 模块（需要统计的模块名称）
     *
     * @return
     */
    String module();

    /**
     * Hash Field
     *
     * @return
     */
    String[] hashField() default "";
}
