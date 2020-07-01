package com.zjx.statistics.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 将 StatisticsHandler 类型的处理器动态添加到 Spring 中
 *
 * @author Aaron
 * @date 2020/7/1 13:54
 */
@Slf4j
@Component
public class StatisticsHandlerBeanPostProcess implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof StatisticsHandler) {
            // 将处理器添加到 StatisticsHandlerContext 中
            StatisticsHandlerContext.addStatisticsHandler((StatisticsHandler) bean);
            log.info("添加 {} 处理器成功", beanName);
        }
        return bean;
    }
}
