package com.zjx.statistics.annotation;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.zjx.statistics.annotation.config.ZjxStatisticsConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启统计服务支持
 *
 * @author Aaron
 * @date 2020/6/10 10:16 上午
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = ZjxStatisticsConfiguration.class)
@EnableAspectJAutoProxy
@DubboComponentScan("com.zjx.statistics.facade")
public @interface EnableZjxStatistics {

}
