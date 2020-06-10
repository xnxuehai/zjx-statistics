package com.zjx.service;

import com.zjx.statistics.annotation.EnableZjxStatistics;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Aaron
 * @date 2020/6/10 3:34 下午
 */
@EnableZjxStatistics
@ComponentScan(basePackages = "com.zjx.service")
public class ApplicationTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ApplicationTest.class);
        applicationContext.refresh();

        BizService bean = applicationContext.getBean(BizService.class);

        bean.doService("input param");
    }
}
