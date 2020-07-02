package com.zjx.statistics.service;

import com.zjx.statistics.domain.StatisticsField;
import com.zjx.statistics.domain.StatisticsFieldFollow;
import com.zjx.statistics.domain.StatisticsRule;

import java.util.List;

/**
 * 统计 field 相关的 service
 *
 * @author Aaron
 * @date 2020/6/29 10:12
 */
public interface IStatisticsFieldService {
    /**
     * 获取所有统计 field
     *
     * @return List<StatisticsField>
     */
    List<StatisticsField> findAllField();

    /**
     * 根据 fieldId 获取 hash 属性
     *
     * @param fieldId fieldId
     * @return List<StatisticsFieldFollow>
     */
    List<StatisticsFieldFollow> findByFieldId(Integer fieldId);

    /**
     * 获取所有规则
     *
     * @return List<StatisticsRule>
     */
    List<StatisticsRule> findAllRule();

    /**
     * 根据 CacheField 获取 信息
     *
     * @param cacheField cacheField
     * @return StatisticsField
     */
    StatisticsField selectByCacheField(String cacheField);
}
