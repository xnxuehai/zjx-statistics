package com.zjx.statistics.annotation.config;


import com.zjx.statistics.interceptor.StatisticsInterceptor;
import com.zjx.statistics.interceptor.StatisticsOperationSource;
import com.zjx.statistics.interceptor.advisor.BeanFactoryZjxStatisticsOperationSourceAdvisor;
import com.zjx.statistics.interceptor.operation.source.AnnotationStatisticsOperationSource;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${statistics.threadPool.corePoolSize:50}")
    private Integer corePoolSize;
    @Value("${statistics.threadPool.maximumPoolSize:50}")
    private Integer maximumPoolSize;
    @Value("${statistics.threadPool.keepAliveTime:60}")
    private Integer keepAliveTime;
    @Value("${statistics.threadPool.capacity:300}")
    private Integer capacity;
    @Value("${statistics.threadPool.threadFactoryNamePrefix:zjx-statistics-thread}")
    private String threadFactoryNamePrefix;
    @Value("${statistics.mq.producer.group:zjx_statistics_producer_group}")
    private String producerGroup;
    @Value("${statistics.mq.nameSrvAddr:121.43.181.38:9876}")
    private String nameSrvAddr;

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
                new ThreadPoolExecutor(Integer.valueOf(corePoolSize), Integer.valueOf(maximumPoolSize), Integer.valueOf(keepAliveTime), TimeUnit.SECONDS,
                        new LinkedBlockingQueue(Integer.valueOf(capacity)), new StatisticsThreadFactory(threadFactoryNamePrefix),
                        new ThreadPoolExecutor.AbortPolicy());
        return statisticsThreadPoolExecutor;
    }

    @Bean
    public DefaultMQProducer statisticsProducer() {
        DefaultMQProducer producer = null;
        try {
            // 实例化消息生产者Producer
            producer = new DefaultMQProducer(producerGroup);
            // 设置NameServer的地址
            producer.setNamesrvAddr(nameSrvAddr);
            producer.setVipChannelEnabled(false);
            // 启动Producer实例
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return producer;
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
