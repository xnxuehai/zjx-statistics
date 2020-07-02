package com.zjx.statistics.mapper;

import com.zjx.statistics.domain.StatisticsField;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Aaron
 * @date 2020/6/28 15:59
 */
public interface StatisticsFieldMapper {
    /**
     * 获取所有的统计 field
     *
     * @return List<StatisticsField>
     */
    List<StatisticsField> selectAll();

    /**
     * 根据 CacheField 获取 信息
     *
     * @param cacheField cacheField
     * @return StatisticsField
     */
    StatisticsField selectByCacheField(@Param("cacheField") String cacheField);
}
