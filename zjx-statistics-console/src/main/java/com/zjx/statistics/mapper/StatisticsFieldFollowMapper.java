package com.zjx.statistics.mapper;

import com.zjx.statistics.domain.StatisticsFieldFollow;

import java.util.List;

/**
 * @author Aaron
 * @date 2020/6/28 16:00
 */
public interface StatisticsFieldFollowMapper {
    /**
     * 根据 fieldId 获取 hash 属性
     *
     * @param fieldId fieldId
     * @return List<StatisticsFieldFollow>
     */
    List<StatisticsFieldFollow> findByFieldId(Integer fieldId);
}
