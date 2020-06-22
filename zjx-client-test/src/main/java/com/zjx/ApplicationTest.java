package com.zjx;

import com.zjx.statistics.annotation.EnableZjxStatistics;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.Executor;


/**
 * @author Aaron
 * @date 2020/6/10 3:34 下午
 */
@EnableZjxStatistics
@SpringBootApplication
public class ApplicationTest implements ApplicationContextAware {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class, args);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContext applicationContext1 = applicationContext;
        Executor statisticsThreadPoolExecutor = applicationContext.getBean("statisticsThreadPoolExecutor", Executor.class);
        System.out.println(statisticsThreadPoolExecutor);
    }
}
