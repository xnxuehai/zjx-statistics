package com.zjx.statistics.service.impl;

import com.zjx.statistics.core.cache.CacheKey;
import com.zjx.statistics.core.cache.CacheStore;
import com.zjx.statistics.core.constant.Constant;
import com.zjx.statistics.core.util.DateUtil;
import com.zjx.statistics.core.util.StringUtil;
import com.zjx.statistics.domain.StatisticsMeta;
import com.zjx.statistics.domain.StatisticsMetaColumn;
import com.zjx.statistics.domain.StatisticsOfDay;
import com.zjx.statistics.service.CacheHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * @author Aaron
 * @date 2020/6/18 13:59
 */
@Service
@Slf4j
public class CacheHandlerImpl implements CacheHandler {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void processBeforeData() {
        // 获取 redis 中统计的用户 id
        Set<Integer> members = redisTemplate.opsForSet().members(CacheKey.getUserIdCacheKey());
        log.info("members:{}", members);
        StatisticsOfDay statisticsOfDay = null;
        // 获取 redis 中缓存数据
        for (Integer member : members) {
            statisticsOfDay = new StatisticsOfDay();
            statisticsOfDay.setUsersId(member);
            statisticsOfDay.setCountDate(DateUtil.getCurrentLocalDateTime(Constant.DATE_FORMAT_STR));

            // 插入数据库
//            statisticsOfDayMapper.insert(statisticsOfDay);
            log.info("插入到统计数据:key --> {}", statisticsOfDay.getUsersId() + "_" + statisticsOfDay.getCountDate());
        }
    }

    private void setValue(StatisticsOfDay statisticsOfDay, String persistenceColumn, Integer counter) {
        Class<StatisticsOfDay> clazz = StatisticsOfDay.class;
        try {
            Field field = clazz.getDeclaredField(StringUtil.underlineToCamel(persistenceColumn));
            field.setAccessible(true);
            field.set(statisticsOfDay, counter);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            log.error("持久化到 mysql 中异常,java 反射异常!!! 00");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            log.error("持久化到 mysql 中异常,java 反射异常!!! 01");
        }
    }
}
