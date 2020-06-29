//package com.zjx.statistics.config;
//
//import com.dangdang.ddframe.job.config.JobCoreConfiguration;
//import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
//import com.dangdang.ddframe.job.event.JobEventConfiguration;
//import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
//import com.dangdang.ddframe.job.lite.api.JobScheduler;
//import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
//import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
//import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
//import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
//import com.zjx.statistics.job.JobForDay;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
///**
// * @author Aaron
// * @date 2020/6/18 10:18
// */
//@Configuration
//public class ElasticJobConfig {
//    @Value("${elastic.serverList}")
//    private String serverList;
//
//    @Value("${elastic.namespace}")
//    private String namespace;
//
//    @Resource
//    private JobForDay jobForDay;
//
//    @Bean(initMethod = "init")
//    public ZookeeperRegistryCenter regCenter() {
//        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
//    }
//
//    @Bean(initMethod = "init")
//    public JobScheduler simpleJobScheduler(ZookeeperRegistryCenter regCenter, JobEventConfiguration jobEventConfiguration) {
//        return new SpringJobScheduler(jobForDay, regCenter, getLiteJobConfiguration(), jobEventConfiguration);
//    }
//
//    @Bean
//    public JobEventConfiguration jobEventConfiguration() {
//        DataSource dataSource = DataSourceBuilder.create()
//                .driverClassName("com.mysql.jdbc.Driver")
//                .url("jdbc:mysql://121.43.181.38:3306/elastic_job?useUnicode=true&characterEncoding=utf-8&verifyServerCertificate=false&useSSL=false&requireSSL=false")
//                .username("root")
//                .password("123456")
//                .type(DruidDataSource.class)
//                .build();
//        return new JobEventRdbConfiguration(dataSource);
//    }
//
//    private LiteJobConfiguration getLiteJobConfiguration() {
//        // 定义作业核心配置 0 0 0/1 * * ? *
//        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder("JobForDay", "0/30 * * * * ? *", 1).build();
//        // 定义SIMPLE类型配置
//        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, JobForDay.class.getCanonicalName());
//        // 定义Lite作业根配置
//        LiteJobConfiguration.Builder builder = LiteJobConfiguration.newBuilder(simpleJobConfig);
//        builder.reconcileIntervalMinutes(1);
//        LiteJobConfiguration build = builder.build();
//        return build;
//    }
//}
