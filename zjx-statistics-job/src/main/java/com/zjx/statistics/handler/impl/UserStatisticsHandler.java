package com.zjx.statistics.handler.impl;

import com.alibaba.fastjson.JSON;
import com.zjx.statistics.business.domain.StatisticsOfDay;
import com.zjx.statistics.business.mapper.StatisticsOfDayMapper;
import com.zjx.statistics.cache.CacheStore;
import com.zjx.statistics.constant.Constant;
import com.zjx.statistics.dto.StatisticsFieldDTO;
import com.zjx.statistics.handler.StatisticsHandler;
import com.zjx.statistics.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 有关用户相关的统计信息持久化
 *
 * @author Aaron
 * @date 2020/7/1 14:02
 */
@Component
@Slf4j
public class UserStatisticsHandler implements StatisticsHandler {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StatisticsOfDayMapper statisticsOfDayMapper;

    static List<String> key = new ArrayList<>();

    static {
        key.add("$date$:new:user:count");
        key.add("$date$:new:user:set");
    }

    @Override
    public void process() {
        log.info("进行用户模块统计的持久化操作");
        addUserCount();
    }

    private void addUserCount() {
        Map<String, StatisticsFieldDTO> store = CacheStore.getInstance().getStore();
        store.forEach((k, v) -> {
            String key = processKey(k);

            if (Constant.STRING.equals(v.getDataType())) {
                Object obj = redisTemplate.opsForValue().get(key);
                StatisticsOfDay statisticsOfDay = new StatisticsOfDay();
                statisticsOfDay.setModuleId(1);
                statisticsOfDay.setCacheField(k);
                statisticsOfDay.setCacheFieldValue(JSON.toJSONString(obj));
                statisticsOfDay.setCountDate(DateUtil.getCurrentLocalDateTime(Constant.DATE_FORMAT_STR));
                statisticsOfDayMapper.save(statisticsOfDay);
                log.info("save success");
            } else if (Constant.SET.equals(v.getDataType())) {
                Object obj = redisTemplate.opsForSet().members(key);
                StatisticsOfDay statisticsOfDay = new StatisticsOfDay();
                statisticsOfDay.setModuleId(1);
                statisticsOfDay.setCacheField(k);
                statisticsOfDay.setCacheFieldValue(JSON.toJSONString(obj));
                statisticsOfDay.setCountDate(DateUtil.getCurrentLocalDateTime(Constant.DATE_FORMAT_STR));
                statisticsOfDayMapper.save(statisticsOfDay);
                log.info("save success");
            } else {

            }

        });

    }

    private String processKey(String key) {
        String regex = "\\$date\\$";
        key = key.replaceAll(regex, DateUtil.getCurrentLocalDateTime(Constant.DATE_FORMAT_STR));
        return key;
    }
}
