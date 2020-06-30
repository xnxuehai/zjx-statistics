package com.zjx.statistics.listener;

import com.alibaba.fastjson.JSON;
import com.zjx.statistics.cache.CacheStore;
import com.zjx.statistics.constant.Constant;
import com.zjx.statistics.dto.StatisticsFieldDTO;
import com.zjx.statistics.dto.StatisticsFieldHashRuleDTO;
import com.zjx.statistics.engine.ComputeContext;
import com.zjx.statistics.engine.ComputeContextData;
import com.zjx.statistics.engine.ComputingEngine;
import com.zjx.statistics.handler.BusinessHandler;
import com.zjx.statistics.handler.ParseHandler;
import com.zjx.statistics.handler.ValidateHandler;
import com.zjx.statistics.transport.TransDTO;
import com.zjx.statistics.util.CarryUtil;
import com.zjx.statistics.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Aaron
 * @date 2020/6/29 16:43
 */
@Slf4j
public class StatisticsMessageListener implements MessageListenerConcurrently {
    @Resource
    private BusinessHandler businessHandler;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msg, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        log.info("{} Receive New Messages: {} {}", Thread.currentThread().getName(), msg);

        for (MessageExt messageExt : msg) {
            try {
                String str = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                log.info("str:{}", str);
                businessHandler.process(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 标记该消息已经被成功消费
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
