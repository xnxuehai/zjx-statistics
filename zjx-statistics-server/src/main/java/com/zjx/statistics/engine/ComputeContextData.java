package com.zjx.statistics.engine;

import com.zjx.statistics.transport.HashField;
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
}
