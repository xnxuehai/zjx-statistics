package com.zjx.statistics.cache;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zjx.statistics.dto.StatisticsFieldDTO;
import com.zjx.statistics.engine.impl.AttributeAdd;
import com.zjx.statistics.engine.impl.NoAttributeAdd;
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
        // 初始化 统计 field
        initStatisticsField();

        // 初始化 规则
        initAlgorithm();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void initStatisticsField() {
        log.info("initStatisticsField start");
        try {
            List<StatisticsFieldDTO> statisticsFieldAll = statisticsFieldFacade.getStatisticsFieldAll();
            for (StatisticsFieldDTO statisticsFieldDTO : statisticsFieldAll) {
                // 添加 StatisticsMeta
                CacheStore.getInstance().addFieldCache(statisticsFieldDTO.getCacheField(), statisticsFieldDTO);
            }
        } catch (Exception e) {
            log.error("initStatisticsField error");
            e.printStackTrace();
            AbstractApplicationContext abstractApplicationContext = (AbstractApplicationContext) applicationContext;
            // 关闭容器
            abstractApplicationContext.close();
        }
        log.info("initStatisticsField end");
    }


    private void initAlgorithm() {
        log.info("initAlgorithm start");
        try {
            // 实例化 无属性累加规则
            CacheStore.getInstance().addAlgorithm(NoAttributeAdd.class.getName(), new NoAttributeAdd());
            // 实例化 按照树形累加
            CacheStore.getInstance().addAlgorithm(AttributeAdd.class.getName(), new AttributeAdd());
        } catch (Exception e) {
            log.error("initAlgorithm error");
            e.printStackTrace();
            AbstractApplicationContext abstractApplicationContext = (AbstractApplicationContext) applicationContext;
            // 关闭容器
            abstractApplicationContext.close();
        }
        log.info("initAlgorithm end");
    }

}
