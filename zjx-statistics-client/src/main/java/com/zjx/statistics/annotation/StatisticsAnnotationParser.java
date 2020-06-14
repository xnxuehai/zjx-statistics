package com.zjx.statistics.annotation;

import com.zjx.statistics.interceptor.operation.AbstractStatisticsOperation;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * 注解解析器
 *
 * @author Aaron
 * @date 2020/6/10 11:57 上午
 */
public interface StatisticsAnnotationParser {

    /**
     * 根据 Class 获取统计操作
     *
     * @param clazz Class
     * @return
     */
    @Nullable
    Collection<AbstractStatisticsOperation> parseStatisticsAnnotations(Class<?> clazz);

    /**
     * 根据 Method 获取统计操作
     *
     * @param method Method
     * @return
     */
    @Nullable
    Collection<AbstractStatisticsOperation> parseStatisticsAnnotations(Method method);


}
