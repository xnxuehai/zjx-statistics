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

import static com.zjx.statistics.constant.Constant.*;
import static com.zjx.statistics.constant.Constant.Z_SET;

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

    @Override
    public void process() {
        addUserCount();
    }

    private void addUserCount() {
        Map<String, StatisticsFieldDTO> store = CacheStore.getInstance().getStore();

        store.forEach((k, v) -> {
            String key = processKey(k);
            Object obj = null;
            StatisticsOfDay statisticsOfDay = new StatisticsOfDay();
            statisticsOfDay.setModuleId(1);
            statisticsOfDay.setCacheField(k);
            statisticsOfDay.setCountDate(DateUtil.getCurrentLocalDateTime(Constant.DATE_FORMAT_STR));

            switch (v.getDataType().intValue()) {
                case STRING:
                    obj = redisTemplate.opsForValue().get(key);
                    statisticsOfDay.setCacheFieldValue(JSON.toJSONString(obj));
                    statisticsOfDayMapper.save(statisticsOfDay);
                    log.info("save string success");
                    break;
                case SET:
                    obj = redisTemplate.opsForSet().members(key);
                    statisticsOfDay.setCacheFieldValue(JSON.toJSONString(obj));
                    statisticsOfDayMapper.save(statisticsOfDay);
                    log.info("save set success");
                    break;
                case HASH:
                    break;
                case Z_SET:
                    break;
                default:
                    log.error("invalid compute type");
                    break;
            }
        });

    }

    private String processKey(String key) {
        String regex = "\\$date\\$";
        key = key.replaceAll(regex, DateUtil.getCurrentLocalDateTime(Constant.DATE_FORMAT_STR));
        return key;
    }
}
