package com.zjx.statistics;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Aaron
 * @date 2020/7/1 11:02
 */
@SpringBootApplication
@MapperScan("com.zjx.statistics.business.mapper")
@DubboComponentScan("com.zjx.service.impl")
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }
}
