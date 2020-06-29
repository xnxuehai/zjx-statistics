package com.zjx.statistics.mapper;

import com.zjx.statistics.domain.StatisticsRule;

import java.util.List;

/**
 * @author Aaron
 * @date 2020/6/29 9:53
 */
public interface StatisticsRuleMapper {
    /**
     * 获取所有计算规则
     *
     * @return List<StatisticsRule>
     */
    List<StatisticsRule> findAll();
}
