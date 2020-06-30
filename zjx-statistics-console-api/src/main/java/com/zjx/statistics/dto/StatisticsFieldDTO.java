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
     * 过期时间 单位 秒
     */
    private Integer expired;
    /**
     * 数据类型 1 string 2 set 3 hash 4 zSet
     */
    private Integer dataType;
    /**
     * hash 属性集合 和 对应的计算规则
     */
    private List<StatisticsFieldHashRuleDTO> hashFieldList;
}
