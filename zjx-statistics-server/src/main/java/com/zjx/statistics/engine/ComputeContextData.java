package com.zjx.statistics.engine;

import com.zjx.statistics.dto.StatisticsFieldHashRuleDTO;
import com.zjx.statistics.transport.HashField;
import com.zjx.statistics.transport.TransDTO;
import lombok.Data;

import java.util.List;

/**
 * @author Aaron
 * @date 2020/6/30 13:37
 */
@Data
public class ComputeContextData {
    /**
     * 待统计 数
     */
    private Integer accumulate;
    /**
     * 待统计 k , v
     */
    private List<HashField> hashFields;
    /**
     * zSet 排序值
     */
    private Double score;
    /**
     * 传入的数据对象
     */
    private TransDTO transDTO;
    /**
     * hash 属性 和 规则
     */
    private List<StatisticsFieldHashRuleDTO> statisticsFieldHashRuleDTOS;
}
