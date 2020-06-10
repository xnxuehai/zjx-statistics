package com.zjx.statistics.annotation.config;


import com.zjx.statistics.interceptor.operation.source.AnnotationStatisticsOperationSource;
import com.zjx.statistics.interceptor.advisor.BeanFactoryZjxStatisticsOperationSourceAdvisor;
import com.zjx.statistics.interceptor.StatisticsInterceptor;
import com.zjx.statistics.interceptor.StatisticsOperationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 统计模块配置类
 *
 * @author Aaron
 * @date 2020/6/10 10:28 上午
 */
@Configuration
public class ZjxStatisticsConfiguration {

    @Bean
    public BeanFactoryZjxStatisticsOperationSourceAdvisor beanFactoryZjxStatisticsOperationSourceAdvisor() {
        BeanFactoryZjxStatisticsOperationSourceAdvisor advisor = new BeanFactoryZjxStatisticsOperationSourceAdvisor();
        advisor.setStatisticsOperationSource(statisticsOperationSource());
        advisor.setAdvice(statisticsInterceptor());
        return advisor;
    }

    @Bean
    public StatisticsOperationSource statisticsOperationSource() {
        AnnotationStatisticsOperationSource annotationCacheOperationSource = new AnnotationStatisticsOperationSource();
        return annotationCacheOperationSource;
    }

    @Bean
    public StatisticsInterceptor statisticsInterceptor() {
        StatisticsInterceptor interceptor = new StatisticsInterceptor();
        interceptor.setCacheOperationSource(statisticsOperationSource());
        return interceptor;
    }

}
