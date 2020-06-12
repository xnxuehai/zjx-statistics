package com.zjx.statistics.annotation.config;


import com.zjx.statistics.interceptor.operation.source.AnnotationStatisticsOperationSource;
import com.zjx.statistics.interceptor.advisor.BeanFactoryZjxStatisticsOperationSourceAdvisor;
import com.zjx.statistics.interceptor.StatisticsInterceptor;
import com.zjx.statistics.interceptor.StatisticsOperationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


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

    @Bean
    public Executor statisticsThreadPoolExecutor() {
        ThreadPoolExecutor statisticsThreadPoolExecutor =
                new ThreadPoolExecutor(30, 30, 60, TimeUnit.SECONDS,
                        new LinkedBlockingQueue(300), new StatisticsThreadFactory("zjx-statistics-Thread"),
                        new ThreadPoolExecutor.AbortPolicy());
        return statisticsThreadPoolExecutor;
    }

    /**
     * 构建线程池工厂
     */
    static class StatisticsThreadFactory implements ThreadFactory {
        private ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private String namePrefix;

        StatisticsThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + "-" + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

}
