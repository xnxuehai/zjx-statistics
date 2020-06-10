package com.zjx.statistics.annotation;

import com.zjx.statistics.annotation.config.ZjxStatisticsConfiguration;
import com.zjx.statistics.annotation.config.ZjxStatisticsConfigurationSelector;
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
@Import(value = {ZjxStatisticsConfigurationSelector.class, ZjxStatisticsConfiguration.class})
public @interface EnableZjxStatistics {

}
