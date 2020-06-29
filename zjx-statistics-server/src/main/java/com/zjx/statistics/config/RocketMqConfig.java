package com.zjx.statistics.config;

import com.zjx.statistics.listener.StatisticsMessageListener;
import lombok.extern.slf4j.Slf4j;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aaron
 * @date 2020/6/29 15:27
 */
@Configuration
@Slf4j
public class RocketMqConfig {
    private static final String CONSUMER_GROUP = "zjx_statistics_consumer_group";
    private static final String NAME_SRV_ADDR = "121.43.181.38:9876";
    private static final String TOPIC = "syn_topic_test";

    @Bean
    public DefaultMQPushConsumer consumer() {
        DefaultMQPushConsumer consumer = null;
        try {
            consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);

            // 设置NameServer的地址
            consumer.setNamesrvAddr(NAME_SRV_ADDR);

            consumer.setVipChannelEnabled(false);

            // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
            consumer.subscribe(TOPIC, "*");
            // 注册回调实现类来处理从broker拉取回来的消息
            consumer.registerMessageListener(new StatisticsMessageListener());
            // 启动消费者实例
            consumer.start();
            log.info("消费者启动成功...");
        } catch (MQClientException e) {
            log.error("消费者启动失败!");
            e.printStackTrace();
        }
        return consumer;
    }
}
