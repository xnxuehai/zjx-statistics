package com.zjx.statistics.interceptor.pointcut;

import com.zjx.statistics.interceptor.StatisticsOperationSource;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 统计切点。如果匹配上，则需要生成动态代理类。
 *
 * @author Aaron
 * @date 2020/6/10 10:47 上午
 */
public abstract class StatisticsOperationSourcePointcut extends StaticMethodMatcherPointcut implements Serializable {


    @Override
    public boolean matches(Method method, Class<?> aClass) {
        StatisticsOperationSource cas = getStatisticsOperationSource();
        return (cas != null && !CollectionUtils.isEmpty(cas.getStatisticsOperations(method, aClass)));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StatisticsOperationSourcePointcut)) {
            return false;
        }
        StatisticsOperationSourcePointcut otherPc = (StatisticsOperationSourcePointcut) other;
        return ObjectUtils.nullSafeEquals(getStatisticsOperationSource(), otherPc.getStatisticsOperationSource());
    }

    @Override
    public int hashCode() {
        return StatisticsOperationSourcePointcut.class.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getName() + ": " + getStatisticsOperationSource();
    }

    /**
     * 由子类实现
     *
     * @return
     */
    @Nullable
    protected abstract StatisticsOperationSource getStatisticsOperationSource();

}
