package com.zjx.mapper;

import com.zjx.domain.StatisticsMeta;

import java.util.List;

/**
 * @author Aaron
 * @date 2020/6/18 13:47
 */
public interface StatisticsMetaMapper {
    /**
     * 获取所有信息
     *
     * @return
     */
    List<StatisticsMeta> selectAll();
}
