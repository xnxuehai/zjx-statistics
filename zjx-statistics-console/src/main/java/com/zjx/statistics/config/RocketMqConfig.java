package com.zjx.statistics.config;

import com.zjx.statistics.listener.ErrorLogListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aaron
 * @date 2020/6/29 15:27
 */
@Configuration
@Slf4j
public class RocketMqConfig {
    @Value("${statistics.mq.nameSrvAddr:121.43.181.38:9876}")
    private String nameSrvAddr;
    @Value("${statistics.mq.console.consumer.group:zjx_statistics_console_consumer_group}")
    private String consumerGroup;

    @Bean
    public DefaultMQPushConsumer consumer() {
        DefaultMQPushConsumer consumer = null;
        try {
            consumer = new DefaultMQPushConsumer(consumerGroup);

            // 设置NameServer的地址
            consumer.setNamesrvAddr(nameSrvAddr);

            consumer.setVipChannelEnabled(false);

            // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
            consumer.subscribe("error_log_topic", "error");
            // 注册回调实现类来处理从broker拉取回来的消息
            consumer.registerMessageListener(errorLogListener());
            // 启动消费者实例
            consumer.start();
            log.info("消费者启动成功...");
        } catch (MQClientException e) {
            log.error("消费者启动失败!");
            e.printStackTrace();
        }
        return consumer;
    }

    @Bean
    public ErrorLogListener errorLogListener() {
        return new ErrorLogListener();
    }
}
