package com.zjx.statistics.core.cache;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zjx.statistics.dto.StatisticsFieldDTO;
import com.zjx.statistics.facade.StatisticsFieldFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化缓存
 *
 * @author Aaron
 * @date 2020/6/18 17:55
 */
@Slf4j
@Component
public class InitCache implements CommandLineRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Reference
    private StatisticsFieldFacade statisticsFieldFacade;

    @Override
    public void run(String... args) {
        log.info("初始化统计属性缓存缓存开始....");
        try {
            List<StatisticsFieldDTO> statisticsFieldAll = statisticsFieldFacade.getStatisticsFieldAll();
            for (StatisticsFieldDTO statisticsFieldDTO : statisticsFieldAll) {
                // 添加 StatisticsMeta
                CacheStore.getInstance().addFieldCache(statisticsFieldDTO.getCacheField(), statisticsFieldDTO.getHashFieldList());
            }
        } catch (Exception e) {
            log.error("初始化统计属性缓存异常!!!");
            e.printStackTrace();
            AbstractApplicationContext abstractApplicationContext = (AbstractApplicationContext) applicationContext;
            // 关闭容器
            abstractApplicationContext.close();
        }
        log.info("初始化统计属性缓存缓存结束....");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
