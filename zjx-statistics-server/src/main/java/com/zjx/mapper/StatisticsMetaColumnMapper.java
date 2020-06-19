package com.zjx.mapper;

import com.zjx.domain.StatisticsMetaColumn;

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
