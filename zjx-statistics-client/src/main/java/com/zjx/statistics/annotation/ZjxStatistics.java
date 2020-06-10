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

    // 模块
    String module();

    // 统计属性
    String[] field();
}
