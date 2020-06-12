package com.park;

import com.park.domain.Data;
import com.park.service.BizService;
import com.zjx.statistics.annotation.EnableZjxStatistics;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Aaron
 * @date 2020/6/10 3:34 下午
 */
@EnableZjxStatistics
@ComponentScan
public class ApplicationTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ApplicationTest.class);
        applicationContext.refresh();

        BizService bean = applicationContext.getBean(BizService.class);

//        bean.doService("not use proxy");
//
//        bean.doServiceStatistics(new Data("Aaron", 20,174));

        for (int i = 0; i < 1000000; i++) {
            new Thread(() -> {
                Data data = new Data("Aaron", 20, 174, 1);
                data.setStatus(2);
                bean.doService("2", data, "123456");
            }).start();
        }

        applicationContext.close();
    }
}
