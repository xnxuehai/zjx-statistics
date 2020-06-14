package com.zjx.statistics.interceptor.operation.source;

import com.zjx.statistics.interceptor.StatisticsOperationSource;
import com.zjx.statistics.interceptor.operation.AbstractStatisticsOperation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.MethodClassKey;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Aaron
 * @date 2020/6/10 11:25 上午
 */
public abstract class AbstractStatisticsOperationSource implements StatisticsOperationSource {

    /**
     * 统计操作缓存集合
     */
    private final Map<Object, Collection<AbstractStatisticsOperation>> attributeCache = new ConcurrentHashMap<>(1024);

    @Override
    public Collection<AbstractStatisticsOperation> getStatisticsOperations(Method method, Class<?> targetClass) {
        // 不处理 Object 中的方法
        if (method.getDeclaringClass() == Object.class) {
            return null;
        }

        // 使用 Spring 中 Method 与 Class 组成的 Object 作为缓存 key
        Object cacheKey = getCacheKey(method, targetClass);

        Collection<AbstractStatisticsOperation> cached = this.attributeCache.get(cacheKey);

        if (cached != null) {
            // 存在缓存直接返回
            return cached;
        } else {
            Collection<AbstractStatisticsOperation> cacheOps = computeCacheOperations(method, targetClass);
            if (cacheOps != null) {
                // 添加缓存
                this.attributeCache.put(cacheKey, cacheOps);
            }
            return cacheOps;
        }
    }

    protected Object getCacheKey(Method method, @Nullable Class<?> targetClass) {
        return new MethodClassKey(method, targetClass);
    }


    @Nullable
    private Collection<AbstractStatisticsOperation> computeCacheOperations(Method method, @Nullable Class<?> targetClass) {

        // 获取 Method
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);

        Collection<AbstractStatisticsOperation> opDef = findStatisticsOperations(specificMethod);
        if (opDef != null) {
            return opDef;
        }

        opDef = findStatisticsOperations(specificMethod.getDeclaringClass());
        if (opDef != null && ClassUtils.isUserLevelMethod(method)) {
            return opDef;
        }

        if (specificMethod != method) {
            opDef = findStatisticsOperations(method);
            if (opDef != null) {
                return opDef;
            }

            opDef = findStatisticsOperations(method.getDeclaringClass());
            if (opDef != null && ClassUtils.isUserLevelMethod(method)) {
                return opDef;
            }

        }

        return null;
    }

    /**
     * 交给子类来实现具体的方法
     *
     * @param clazz Class
     * @return
     */
    @Nullable
    protected abstract Collection<AbstractStatisticsOperation> findStatisticsOperations(Class<?> clazz);

    /**
     * 交给子类来实现具体的方法
     *
     * @param method Method
     * @return
     */
    @Nullable
    protected abstract Collection<AbstractStatisticsOperation> findStatisticsOperations(Method method);
}
