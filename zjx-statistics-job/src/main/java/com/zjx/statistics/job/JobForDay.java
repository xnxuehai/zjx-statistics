package com.zjx.statistics.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.zjx.statistics.handler.StatisticsHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author Aaron
 * @date 2020/6/17 14:41
 */
@Slf4j
@Component
public class JobForDay implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("执行 每天定时任务开始...");
        StatisticsHandlerContext.processAll();
        log.info("执行 每天定时任务开始...");
    }
}
