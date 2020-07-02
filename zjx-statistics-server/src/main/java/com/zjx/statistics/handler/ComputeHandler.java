package com.zjx.statistics.handler;

import com.zjx.statistics.cache.CacheStore;
import com.zjx.statistics.constant.Constant;
import com.zjx.statistics.dto.StatisticsFieldDTO;
import com.zjx.statistics.dto.StatisticsFieldHashRuleDTO;
import com.zjx.statistics.engine.ComputeContext;
import com.zjx.statistics.engine.ComputeContextData;
import com.zjx.statistics.engine.ComputingEngine;
import com.zjx.statistics.transport.TransDTO;
import com.zjx.statistics.util.CarryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static com.zjx.statistics.constant.Constant.*;


/**
 * 计算处理
 *
 * @author Aaron
 * @date 2020/6/30 16:06
 */
@Component
@Slf4j
public class ComputeHandler {
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 计算并且存储
     *
     * @param transDTO
     * @param key
     */
    public void compute(TransDTO transDTO, String key) {
        // 获取缓存数据
        StatisticsFieldDTO hashFile = CacheStore.getInstance().getHashFile(transDTO.getModule());

        for (StatisticsFieldHashRuleDTO statisticsFieldHashRuleDTO : hashFile.getHashFieldList()) {

            switch (hashFile.getDataType().intValue()) {
                case STRING:
                    processString(key, transDTO, hashFile, statisticsFieldHashRuleDTO);
                    break;
                case SET:
                    processSet(key, transDTO, hashFile, statisticsFieldHashRuleDTO);
                    break;
                case HASH:
                    processHash(key, transDTO, hashFile, statisticsFieldHashRuleDTO);
                    break;
                case Z_SET:
                    processZSet(key, transDTO, hashFile, statisticsFieldHashRuleDTO);
                    break;
                default:
                    log.error("invalid compute type");
                    break;
            }
        }

    }

    private void processString(String key, TransDTO transDTO, StatisticsFieldDTO hashFile, StatisticsFieldHashRuleDTO statisticsFieldHashRuleDTO) {
        Integer count = (Integer) redisTemplate.opsForValue().get(key);

        // 初始化计算上下文
        ComputeContextData computeContextData = new ComputeContextData();
        computeContextData.setAccumulate(count);
        ComputeContext.getInstance().setComputeContextData(computeContextData);

        // 选择计算策略
        ComputingEngine computingEngine = new ComputingEngine(statisticsFieldHashRuleDTO.getRuleEngine());

        // 计算
        computingEngine.operate();
        computeContextData = ComputeContext.getInstance().getComputeContextData();

        if (INVALID_FLAG.equals(hashFile.getExpired().toString())) {
            // 不需要设置过期时间
            redisTemplate.opsForValue().set(key, computeContextData.getAccumulate());
        } else {
            // 需要设置过期时间
            redisTemplate.opsForValue().set(key, computeContextData.getAccumulate(), hashFile.getExpired(), TimeUnit.SECONDS);
        }
        ComputeContext.getInstance().remove();

        log.info("save compute string success");
    }

    private void processSet(String key, TransDTO transDTO, StatisticsFieldDTO hashFile, StatisticsFieldHashRuleDTO statisticsFieldHashRuleDTO) {
        redisTemplate.opsForSet().add(key, transDTO.getKey());
        redisTemplate.expire(key, hashFile.getExpired(), TimeUnit.SECONDS);

        log.info("save compute set success");
    }

    private void processHash(String key, TransDTO transDTO, StatisticsFieldDTO hashFile, StatisticsFieldHashRuleDTO statisticsFieldHashRuleDTO) {

    }

    private void processZSet(String key, TransDTO transDTO, StatisticsFieldDTO hashFile, StatisticsFieldHashRuleDTO statisticsFieldHashRuleDTO) {

    }

    /**
     * 排行榜
     *
     * @param userId
     * @param experience
     * @param medal
     */
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
