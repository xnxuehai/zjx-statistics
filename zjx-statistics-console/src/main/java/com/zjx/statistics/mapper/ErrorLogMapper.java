package com.zjx.statistics.mapper;

import com.zjx.statistics.domain.ErrorLog;

/**
 * @author Aaron
 * @date 2020/7/1 9:32
 */
public interface ErrorLogMapper {
    /**
     * 添加异常日志
     *
     * @param errorLog
     */
    void save(ErrorLog errorLog);
}
