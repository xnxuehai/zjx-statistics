package com.park.service;

import com.zjx.statistics.annotation.ZjxStatistics;

/**
 * @author Aaron
 * @date 2020/6/10 3:35 下午
 */
public interface BizService {
    /**
     * 代理
     *
     * @param name
     */
    @ZjxStatistics(module = "video", field = {"name", "pwd"})
    void doServiceStatistics(String name);

    /**
     * 不代理
     * @param name
     */
    void doService(String name);
}
