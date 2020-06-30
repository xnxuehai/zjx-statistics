package com.zjx.statistics.listener;

import com.alibaba.fastjson.JSON;
import com.zjx.statistics.transport.TransDTO;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Aaron
 * @date 2020/6/29 16:43
 */
public class StatisticsMessageListener implements MessageListenerConcurrently {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msg, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msg);
        // 解析数据 {“module”:"user:operation:$date$:new:user","key":"1","hashField":""}
        for (MessageExt messageExt : msg) {
            try {
                String str = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                System.out.println(str);
                TransDTO transDTO = JSON.toJavaObject(JSON.parseObject(str), TransDTO.class);
                System.out.println(transDTO.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 数据校验

        // 进行计算 TODO 基于 ThreadLocal 进行数据传输

        // 标记该消息已经被成功消费
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
