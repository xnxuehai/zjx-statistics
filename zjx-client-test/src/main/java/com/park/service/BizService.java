package com.park.service;

import com.park.domain.Data;
import com.zjx.statistics.annotation.ZjxStatistics;

/**
 * @author Aaron
 * @date 2020/6/10 3:35 下午
 */
public interface BizService {
    /**
     * 代理
     *
     * @param data
     * @return
     */
    String doServiceStatistics(Data data);

    /**
     * 不代理
     *
     * @param name
     * @return
     */
    Data doService(String name);

    /**
     * 代理 多参数
     *
     * @param userId userId
     * @param data   data
     * @param str    str
     * @return
     */
    @ZjxStatistics(key = "userId", module = "video", isCountSelf = true, paramField = {"name", "height"}, tableField = {"table_name", "table_height"}, openStatus = {"status"}, tableStatus = {"table_status"})
    Data doService(String userId, Data data, String str);

}
