package com.zjx.statistics.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Aaron
 * @date 2020/6/29 10:19
 */
@Data
public class StatisticsFieldDTO implements Serializable {
    /**
     * 统计属性
     */
    private String cacheField;
    /**
     * hash 属性集合 和 对应的计算规则
     */
    private List<StatisticsFieldHashRuleDTO> hashFieldList;
}
