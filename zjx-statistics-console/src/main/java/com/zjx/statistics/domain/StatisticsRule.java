package com.zjx.statistics.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Aaron
 * @date 2020/6/29 9:49
 */
@Data
public class StatisticsRule {
    /**
     * 唯一标识
     */
    private Integer id;
    /**
     * 描述信息
     */
    private String description;
    /**
     * 规则对应的计算引擎
     *
     */
    private String ruleEngine;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
