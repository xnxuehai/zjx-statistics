package com.zjx.statistics.cache;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zjx.statistics.dto.StatisticsFieldDTO;
import com.zjx.statistics.engine.NoAttributeAdd;
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


	private void initAlgorithm() {
		log.info("实例化规则实例开始....");
		try {
			// 实例化 无属性累加规则
			CacheStore.getInstance().addAlgorithm(NoAttributeAdd.class.getName(), new NoAttributeAdd());
		} catch (Exception e) {
			log.error("实例化规则实例异常!!!");
			e.printStackTrace();
			AbstractApplicationContext abstractApplicationContext = (AbstractApplicationContext) applicationContext;
			// 关闭容器
			abstractApplicationContext.close();
		}
		log.info("实例化规则实例结束....");
	}

}
