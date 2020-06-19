package com.zjx.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.zjx.service.CacheHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Aaron
 * @date 2020/6/17 14:41
 */
@Component
public class JobForDay implements SimpleJob {

    @Resource
    private CacheHandler cacheHandler;

    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("执行 每天定时任务开始...");
        cacheHandler.processBeforeData();
        System.out.println("执行 每天定时任务结束...");
    }
}
