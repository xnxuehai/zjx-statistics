package com.zjx.statistics.annotation.config;


import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.context.annotation.*;

import java.lang.reflect.Proxy;

/**
 * 基于{@link Configuration} 开启模块装配机制
 * 目前只支持 jdk 的 动态代理机制 {@link Proxy}
 *
 * @author Aaron
 * @date 2020/6/10 10:17 上午
 */
@Configuration
public class ZjxStatisticsConfigurationSelector {

    @Bean
    public AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator() {
        AnnotationAwareAspectJAutoProxyCreator autoProxyRegistrar = new AnnotationAwareAspectJAutoProxyCreator();
        return autoProxyRegistrar;
    }

}
