package com.zjx.statistics.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.zjx.statistics.service.CacheHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Aaron
 * @date 2020/6/17 14:41
 */
@Component
@Slf4j
public class JobForDay implements SimpleJob {
    @Resource
    private CacheHandler cacheHandler;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("执行 每天定时任务开始...");
        cacheHandler.processBeforeData();
        log.info("执行 每天定时任务开始...");
    }
}
