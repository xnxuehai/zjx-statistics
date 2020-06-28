package com.zjx.statistics.core.cache;

import com.zjx.statistics.domain.StatisticsMeta;
import com.zjx.statistics.mapper.StatisticsMetaColumnMapper;
import com.zjx.statistics.mapper.StatisticsMetaMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
    @Resource
    private StatisticsMetaMapper statisticsMetaMapper;
    @Resource
    private StatisticsMetaColumnMapper statisticsMetaColumnMapper;

    @Override
    public void run(String... args) throws Exception {
        log.info("初始化本地缓存开始....");
        try {
            List<StatisticsMeta> statisticsMetas = statisticsMetaMapper.selectAll();
            for (StatisticsMeta statisticsMeta : statisticsMetas) {
                // 添加 StatisticsMeta
                CacheStore.getInstance().addStatisticsMeta(statisticsMeta);
                // 添加 StatisticsMetaColumn
                CacheStore.getInstance().addStatisticsMetaColumn(statisticsMeta.getId(), statisticsMetaColumnMapper.selectAllByMetaId(statisticsMeta.getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            AbstractApplicationContext abstractApplicationContext = (AbstractApplicationContext) applicationContext;
            // 关闭容器
            abstractApplicationContext.close();
        }
        log.info("初始化本地缓存结束....");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
