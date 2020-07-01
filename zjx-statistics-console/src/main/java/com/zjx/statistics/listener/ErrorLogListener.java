package com.zjx.statistics.listener;

import com.alibaba.fastjson.JSON;
import com.zjx.statistics.service.IErrorLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Aaron
 * @date 2020/6/29 16:43
 */
@Slf4j
public class ErrorLogListener implements MessageListenerConcurrently {
    @Resource
    private IErrorLogService errorLogService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msg, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        log.info("{} Receive New Messages: {} {}", Thread.currentThread().getName(), msg);

        for (MessageExt messageExt : msg) {
            try {
                String str = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                log.info("str:{}", str);
                Map<String, String> map = JSON.toJavaObject(JSON.parseObject(str), Map.class);
                errorLogService.save(map.get("moduleName"), map.get("desc"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 标记该消息已经被成功消费
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
