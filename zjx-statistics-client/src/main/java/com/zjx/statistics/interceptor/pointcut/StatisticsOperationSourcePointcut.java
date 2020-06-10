package com.zjx.statistics.interceptor.pointcut;

import com.zjx.statistics.interceptor.StatisticsOperationSource;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 统计切点。如果匹配上，则需要生成动态代理类。
 *
 * @author Aaron
 * @date 2020/6/10 10:47 上午
 */
public abstract class StatisticsOperationSourcePointcut extends StaticMethodMatcherPointcut implements Serializable {

    protected StatisticsOperationSourcePointcut() {
        setClassFilter(new StatisticsOperationSourceClassFilter());
    }

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        StatisticsOperationSource cas = getStatisticsOperationSource();
        return (cas != null && !CollectionUtils.isEmpty(cas.getStatisticsOperations(method, aClass)));
    }


    private class StatisticsOperationSourceClassFilter implements ClassFilter {

        @Override
        public boolean matches(Class<?> clazz) {
            if (CacheManager.class.isAssignableFrom(clazz)) {
                return false;
            }
            StatisticsOperationSource cas = getStatisticsOperationSource();
            return (cas == null || cas.isCandidateClass(clazz));
        }
    }

    /**
     * 由子类实现
     *
     * @return
     */
    @Nullable
    protected abstract StatisticsOperationSource getStatisticsOperationSource();

}
