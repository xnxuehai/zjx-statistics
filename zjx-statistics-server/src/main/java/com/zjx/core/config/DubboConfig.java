package com.zjx.core.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
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
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setVersion("1.0.0");
        providerConfig.setTimeout(15000);
        providerConfig.setRetries(0);
        providerConfig.setDelay(-1);
        return providerConfig;
    }
}
