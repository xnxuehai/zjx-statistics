package com.zjx.statistics.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cache.interceptor.CacheOperationInvoker;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 通知（也可以称作拦截器，即在真实方法前、后进行相关的逻辑）
 *
 * @author Aaron
 * @date 2020/6/10 10:38 上午
 */
public class StatisticsInterceptor extends StatisticsAspectSupport implements MethodInterceptor, Serializable {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        // 应该是异常处理 目前先不考虑
        StatisticsOperationInvoker aopAllianceInvoker = () -> {
            try {
                return invocation.proceed();
            } catch (Throwable ex) {
                throw new CacheOperationInvoker.ThrowableWrapper(ex);
            }
        };

        try {
            return execute(aopAllianceInvoker, invocation.getThis(), method, invocation.getArguments());
        } catch (CacheOperationInvoker.ThrowableWrapper th) {
            throw th.getOriginal();
        }

    }

}
