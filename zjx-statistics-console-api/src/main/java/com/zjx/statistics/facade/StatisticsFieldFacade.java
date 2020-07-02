package com.zjx.statistics.facade;

import com.zjx.statistics.dto.StatisticsFieldDTO;

import java.util.List;

/**
 * 获取统计 field 相关信息的接口
 *
 * @author Aaron
 * @date 2020/6/29 10:18
 */
public interface StatisticsFieldFacade {
    /**
     * 获取所有需要统计的 field
     *
     * @return List<StatisticsFieldDTO>
     */
    List<StatisticsFieldDTO> getStatisticsFieldAll();

    /**
     * 根据 cacheField 获取信息
     *
     * @param cacheField cacheField
     * @return
     */
    StatisticsFieldDTO getStatisticsFieldByCacheField(String cacheField);
}
