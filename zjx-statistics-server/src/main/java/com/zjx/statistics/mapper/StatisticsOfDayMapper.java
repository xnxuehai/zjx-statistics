package com.zjx.statistics.mapper;

import com.zjx.statistics.domain.StatisticsOfDay;

/**
 * @author Aaron
 * @date 2020/6/18 11:04
 */
public interface StatisticsOfDayMapper {
    /**
     * 新增数据
     *
     * @param statisticsOfDay
     */
    void insert(StatisticsOfDay statisticsOfDay);
}
