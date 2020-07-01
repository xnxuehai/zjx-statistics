package com.zjx.statistics.handler.impl;

import com.zjx.statistics.handler.StatisticsHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 有关教师相关的统计信息持久化
 *
 * @author Aaron
 * @date 2020/7/1 14:41
 */
@Component
@Slf4j
public class TeacherStatisticsHandler implements StatisticsHandler {
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void process() {
        log.info("进行教师模块统计的持久化操作");
        System.out.println(redisTemplate);
    }
}
