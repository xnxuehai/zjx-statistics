package com.zjx.statistics.interceptor;

import com.zjx.statistics.interceptor.operation.AbstractStatisticsOperation;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * 操作源，包含所有标记 统计注解 的 注解元信息。
 *
 * @author Aaron
 * @date 2020/6/10 10:37 上午
 */
public interface StatisticsOperationSource {

    /**
     * 判断给定的类是否是统计操作的候选类
     *
     * @param targetClass
     * @return
     */
    default boolean isCandidateClass(Class<?> targetClass) {
        return true;
    }

    /**
     * 通过 method 和 class 获取统计操作的集合
     *
     * @param method      Method
     * @param targetClass Class
     * @return 统计操作集合
     */
    @Nullable
    Collection<AbstractStatisticsOperation> getStatisticsOperations(Method method, @Nullable Class<?> targetClass);

}
