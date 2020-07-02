package com.zjx.statistics.config;

import com.zjx.statistics.listener.StatisticsMessageListener;
import lombok.extern.slf4j.Slf4j;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Aaron
 * @date 2020/6/29 15:27
 */
@Configuration
@Slf4j
public class RocketMqConfig {
    @Value("${statistics.mq.consumer.group:zjx_statistics_consumer_group}")
    private String consumerGroup;
    @Value("${statistics.mq.console.producer.group:zjx_statistics_console_producer_group}")
    private String producerGroup;
    @Value("${statistics.mq.nameSrvAddr:121.43.181.38:9876}")
    private String nameSrvAddr;
    private static final String TOPIC = "statistics_metadata_info_topic";
    private static final String TAG = "info";

    @Bean
    public DefaultMQPushConsumer consumer() {
        DefaultMQPushConsumer consumer = null;
        try {
            consumer = new DefaultMQPushConsumer(consumerGroup);

            // 设置NameServer的地址
            consumer.setNamesrvAddr(nameSrvAddr);

            consumer.setVipChannelEnabled(false);

            // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
            consumer.subscribe(TOPIC, TAG);
            // 注册回调实现类来处理从broker拉取回来的消息
            consumer.registerMessageListener(statisticsMessageListener());
            // 启动消费者实例
            consumer.start();
            log.info("consumer start success!");
        } catch (MQClientException e) {
            log.error("consumer start fail!");
            e.printStackTrace();
        }
        return consumer;
    }

    @Bean
    public StatisticsMessageListener statisticsMessageListener() {
        return new StatisticsMessageListener();
    }

    @Bean
    public DefaultMQProducer producer() {
        DefaultMQProducer producer = null;
        try {
            // 实例化消息生产者Producer
            producer = new DefaultMQProducer(producerGroup);
            // 设置NameServer的地址
            producer.setNamesrvAddr(nameSrvAddr);
            producer.setVipChannelEnabled(false);
            // 启动Producer实例
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return producer;
    }
}
