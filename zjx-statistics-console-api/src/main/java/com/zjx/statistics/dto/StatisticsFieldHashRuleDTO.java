package com.zjx.statistics.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Aaron
 * @date 2020/6/29 10:53
 */
@Data
public class StatisticsFieldHashRuleDTO implements Serializable {
    /**
     * hash 属性
     */
    private String hashField;
    /**
     * 计算规则引擎
     */
    private String ruleEngine;
    /**
     * 排序字段
     */
    private Integer sort;
}
