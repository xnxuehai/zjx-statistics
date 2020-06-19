package com.zjx.service.impl;

import com.zjx.core.cache.CacheKey;
import com.zjx.core.cache.CacheStore;
import com.zjx.core.constant.Constant;
import com.zjx.core.util.DateUtil;
import com.zjx.core.util.StringUtil;
import com.zjx.domain.StatisticsMeta;
import com.zjx.domain.StatisticsMetaColumn;
import com.zjx.domain.StatisticsOfDay;
import com.zjx.mapper.StatisticsOfDayMapper;
import com.zjx.service.CacheHandler;
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
    private StatisticsOfDayMapper statisticsOfDayMapper;
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

            processStatisticsMeta(member, statisticsOfDay);

            // 插入数据库
            statisticsOfDayMapper.insert(statisticsOfDay);
            log.info("插入到统计数据:key --> {}", statisticsOfDay.getUsersId() + "_" + statisticsOfDay.getCountDate());
        }
    }

    /**
     * 处理 StatisticsMeta
     *
     * @param member 用户id
     */
    private void processStatisticsMeta(Integer member, StatisticsOfDay statisticsOfDay) {
        for (StatisticsMeta statisticsMeta : CacheStore.getInstance().getStatisticsMetaList()) {

            log.info("countKey:{}", CacheKey.getCountKey(member, statisticsMeta.getModuleName()));

            Map<String, Integer> entries = redisTemplate.opsForHash().entries(CacheKey.getCountKey(member, statisticsMeta.getModuleName()));

            // 去除标识位
            entries.remove("flag");
            log.info("entries:{}", entries);

            processStatisticsMetaColumn(statisticsMeta, entries, statisticsOfDay);
        }
    }

    private void processStatisticsMetaColumn(StatisticsMeta statisticsMeta, Map<String, Integer> entries, StatisticsOfDay statisticsOfDay) {

        for (StatisticsMetaColumn statisticsMetaColumn : CacheStore.getInstance().getStatisticsMetaColumn(statisticsMeta.getId())) {
            // 统计字段
            String statisticsColumn = statisticsMetaColumn.getStatisticsColumn();
            // 持久化字段
            String persistenceColumn = statisticsMetaColumn.getPersistenceColumn();
            // 状态字段
            String statisticsStatusColumn = statisticsMetaColumn.getStatisticsStatusColumn();
            // 状态值
            String statisticsStatusColumnValue = statisticsMetaColumn.getStatisticsStatusColumnValue();

            Integer counter = 0;
            if (Constant.INVALID_FLAG.equals(statisticsStatusColumn)) {
                // 无状态统计
                counter = entries.get(statisticsColumn);
            } else {
                // 状态 + 值 + 统计字段
                String key = statisticsStatusColumn + Constant.SPLIT + statisticsStatusColumnValue + Constant.SPLIT + Constant.COUNT_SELF;
                counter = entries.get(key);
            }
            // 设置值
            setValue(statisticsOfDay, persistenceColumn, counter);
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
