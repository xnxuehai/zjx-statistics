package com.zjx.statistics;

import com.zjx.statistics.annotation.EnableZjxStatistics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Aaron
 * @date 2020/6/10 3:34 下午
 */
@EnableZjxStatistics
@SpringBootApplication
public class ApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class, args);
    }
}
