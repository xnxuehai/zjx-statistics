package com.zjx.statistics.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.zjx.statistics.core.cache.CacheKey;
import com.zjx.statistics.core.constant.Constant;
import com.zjx.statistics.core.parser.CounterParser;
import com.zjx.statistics.core.util.CarryUtil;
import com.zjx.statistics.dto.CounterDTO;
import com.zjx.statistics.facade.ReceiveInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 接收客户端传入请求
 *
 * @author Aaron
 * @date 2020/6/15 10:24
 */
@Service
@Slf4j
public class ReceiveInfoService implements ReceiveInfo {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void receiveInfo(CounterDTO counterDTO) {
        log.info("接收到来自客户端的请求数据 data:{}", JSON.toJSONString(counterDTO));

        HashOperations hashOperations = redisTemplate.opsForHash();

        // 统计 缓存 key
        String cacheCKey = CacheKey.getCountKey(Integer.valueOf(counterDTO.getKey().toString()), counterDTO.getModule());

        // 添加统计用户的id
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add(CacheKey.getUserIdCacheKey(), counterDTO.getKey());
        redisTemplate.expire(CacheKey.getUserIdCacheKey(), Constant.EXPIRE, TimeUnit.SECONDS);

        Map<String, Object> parserData = CounterParser.getParserData(counterDTO);

        log.info("处理后需要统计的数据，kv 格式:{}", parserData);

        if (!redisTemplate.hasKey(cacheCKey)) {

            hashOperations.put(cacheCKey, Constant.CACHE_FLAG_STR, Constant.CACHE_FLAG_STR);
            
            redisTemplate.expire(cacheCKey, Constant.EXPIRE, TimeUnit.SECONDS);
        }

        parserData.forEach((k, v) -> {
            try {
                hashOperations.increment(cacheCKey, k, Double.valueOf(v.toString()));
            } catch (Exception e) {
                log.error("添加缓存出现异常!!!", e);
            }
        });
    }

    @Override
    public void receiveInfo(Integer userId, Integer experience, Integer medal) {
        log.info("接收用户经验值和奖章统计数据 userId:{},experience:{},medal:{}", userId, experience, medal);

        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        // TODO 此处需要计算，后期需要加分布式锁

        Double score = zSetOperations.score(Constant.USER_RANK_CACHE_KEY, userId);
        log.info("score:{}", score);
        if (score == null) {
            // 第一次添加
            zSetOperations.add(Constant.USER_RANK_CACHE_KEY, userId, CarryUtil.scoreParser(experience, medal, 0L));
        } else {
            zSetOperations.add(Constant.USER_RANK_CACHE_KEY, userId, CarryUtil.scoreParser(experience, medal, Long.valueOf(new BigDecimal(score).toString())));
        }
    }
}
