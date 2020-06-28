package com.zjx.statistics.mapper;

import com.zjx.statistics.domain.StatisticsMetaColumn;

import java.util.List;

/**
 * @author Aaron
 * @date 2020/6/18 16:17
 */
public interface StatisticsMetaColumnMapper {
    /**
     * 获取信息
     *
     * @param id id
     * @return
     */
    List<StatisticsMetaColumn> selectAllByMetaId(Integer id);
}
