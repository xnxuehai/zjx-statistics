package com.zjx.statistics.business.mapper;

import com.zjx.statistics.business.domain.StatisticsOfDay;

/**
 * @author Aaron
 * @date 2020/7/1 15:15
 */
public interface StatisticsOfDayMapper {
    /**
     * 添加统计信息
     *
     * @param statisticsOfDay statisticsOfDay
     */
    void save(StatisticsOfDay statisticsOfDay);
}
