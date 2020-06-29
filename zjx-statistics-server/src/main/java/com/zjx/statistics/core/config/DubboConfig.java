package com.zjx.statistics.core.config;

import com.alibaba.dubbo.config.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aaron
 * @date 2020/6/15 10:19
 */
@Configuration
public class DubboConfig {

    @Value("${dubbo.registry.address}")
    private String zk_address;

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setId("zjx_statistics");
        applicationConfig.setName("zjx_statistics");
        applicationConfig.setQosEnable(true);
        applicationConfig.setQosPort(20008);
        return applicationConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setId("dubbo");
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20888);
        protocolConfig.setThreads(5000);
        protocolConfig.setDispatcher("message");
        protocolConfig.setThreadpool("cached");
        return protocolConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(zk_address);
        registryConfig.setCheck(true);
        return registryConfig;
    }

    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setCheck(true);
        consumerConfig.setVersion("1.0.0");
        consumerConfig.setRetries(0);
        return consumerConfig;
    }
}
