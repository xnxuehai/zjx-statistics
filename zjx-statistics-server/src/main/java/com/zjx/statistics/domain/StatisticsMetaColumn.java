package com.zjx.statistics.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Aaron
 * @date 2020/6/18 16:14
 */
@Data
public class StatisticsMetaColumn {
    /**
     * 标识
     */
    private Integer id;
    /**
     * 统计标识
     */
    private Integer statisticsMetaId;
    /**
     * 统计字段
     */
    private String statisticsColumn;
    /**
     * 状态字段
     */
    private String statisticsStatusColumn;
    /**
     * 状态字段值
     */
    private String statisticsStatusColumnValue;
    /**
     * 持久化字段
     */
    private String persistenceColumn;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
