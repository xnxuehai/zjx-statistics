package com.zjx.statistics;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Aaron
 * @date 2020/6/28 15:34
 */
@SpringBootApplication
@MapperScan("com.zjx.statistics.mapper")
@DubboComponentScan("com.zjx.statistics.service.impl.facade")
public class ConsoleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class, args);
    }
}
