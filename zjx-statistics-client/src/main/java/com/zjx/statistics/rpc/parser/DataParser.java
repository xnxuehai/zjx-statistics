package com.zjx.statistics.rpc.parser;

import com.zjx.statistics.interceptor.operation.AbstractStatisticsOperation;
import com.zjx.statistics.rpc.dto.CounterDTO;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * 数据解析接口
 *
 * @author Aaron
 * @date 2020/6/11 6:01 下午
 */
public interface DataParser {
    /**
     * 将代理对象中的数据转换为 rpc 通信的数据
     *
     * @param method     method
     * @param operations operations
     * @param args       args
     * @return
     */
    List<CounterDTO> parser(Method method, Collection<AbstractStatisticsOperation> operations, Object[] args);
}
