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
import java.util.List;
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
     * @param cacheKey
     */
    public void compute(TransDTO transDTO, String cacheKey) {
        // 获取缓存数据
        StatisticsFieldDTO hashFile = CacheStore.getInstance().getHashFile(transDTO.getModule());

        switch (hashFile.getDataType().intValue()) {
            case STRING:
                processString(cacheKey, transDTO, hashFile, hashFile.getHashFieldList());
                break;
            case SET:
                processSet(cacheKey, transDTO, hashFile, hashFile.getHashFieldList());
                break;
            case HASH:
                processHash(cacheKey, transDTO, hashFile, hashFile.getHashFieldList());
                break;
            case Z_SET:
                processZSet(cacheKey, transDTO, hashFile, hashFile.getHashFieldList());
                break;
            default:
                log.error("invalid compute type");
                break;
        }

    }

    private void processString(String cacheKey, TransDTO transDTO, StatisticsFieldDTO hashFile, List<StatisticsFieldHashRuleDTO> statisticsFieldHashRuleDTOS) {
        Integer count = (Integer) redisTemplate.opsForValue().get(cacheKey);
        StatisticsFieldHashRuleDTO statisticsFieldHashRuleDTO = null;

        for (StatisticsFieldHashRuleDTO temp : statisticsFieldHashRuleDTOS) {
            if (Constant.INVALID_FLAG.equals(temp.getHashField())) {
                statisticsFieldHashRuleDTO = temp;
            }
        }

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
            redisTemplate.opsForValue().set(cacheKey, computeContextData.getAccumulate());
        } else {
            // 需要设置过期时间
            redisTemplate.opsForValue().set(cacheKey, computeContextData.getAccumulate(), hashFile.getExpired(), TimeUnit.SECONDS);
        }
        ComputeContext.getInstance().remove();

        log.info("save compute string success");
    }

    private void processSet(String cacheKey, TransDTO transDTO, StatisticsFieldDTO hashFile, List<StatisticsFieldHashRuleDTO> statisticsFieldHashRuleDTOS) {
        redisTemplate.opsForSet().add(cacheKey, transDTO.getKey());
        redisTemplate.expire(cacheKey, hashFile.getExpired(), TimeUnit.SECONDS);

        log.info("save compute set success");
    }

    private void processHash(String key, TransDTO transDTO, StatisticsFieldDTO hashFile, List<StatisticsFieldHashRuleDTO> statisticsFieldHashRuleDTOS) {

    }

    private void processZSet(String key, TransDTO transDTO, StatisticsFieldDTO hashFile, List<StatisticsFieldHashRuleDTO> statisticsFieldHashRuleDTOS) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        StatisticsFieldHashRuleDTO statisticsFieldHashRuleDTO = null;

        for (StatisticsFieldHashRuleDTO temp : statisticsFieldHashRuleDTOS) {
            if (Constant.INVALID_FLAG.equals(temp.getHashField())) {
                statisticsFieldHashRuleDTO = temp;
            }
        }

        // TODO 此处需要计算，后期需要加分布式锁
        Double score = zSetOperations.score(key, transDTO.getKey().toString());

        // 初始化计算上下文
        ComputeContextData computeContextData = new ComputeContextData();
        computeContextData.setScore(score);
        computeContextData.setTransDTO(transDTO);
        computeContextData.setStatisticsFieldHashRuleDTOS(statisticsFieldHashRuleDTOS);
        ComputeContext.getInstance().setComputeContextData(computeContextData);

        // 选择计算策略
        ComputingEngine computingEngine = new ComputingEngine(statisticsFieldHashRuleDTO.getRuleEngine());

        // 计算
        computingEngine.operate();
        computeContextData = ComputeContext.getInstance().getComputeContextData();

        zSetOperations.add(key, Integer.valueOf(transDTO.getKey().toString()), computeContextData.getScore().doubleValue());

        ComputeContext.getInstance().remove();
    }

    /**
     * 学生排行榜
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
