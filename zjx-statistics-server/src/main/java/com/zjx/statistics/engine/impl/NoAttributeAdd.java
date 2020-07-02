package com.zjx.statistics.engine.impl;

import com.zjx.statistics.engine.Algorithm;
import com.zjx.statistics.engine.ComputeContext;
import com.zjx.statistics.engine.ComputeContextData;
import lombok.extern.slf4j.Slf4j;

/**
 * 无属性累加算法
 *
 * @author Aaron
 * @date 2020/6/29 20:34
 */
public class NoAttributeAdd implements Algorithm {

    @Override
    public void operate() {
        ComputeContextData computeContextData = ComputeContext.getInstance().getComputeContextData();
        if (computeContextData.getAccumulate() != null) {
            computeContextData.setAccumulate(computeContextData.getAccumulate() + 1);
        } else {
            computeContextData.setAccumulate(1);
        }
    }
}
