package com.zjx.statistics.service;

import com.zjx.statistics.domain.Data;
import com.zjx.statistics.annotation.ZjxStatistics;

/**
 * 模拟统计模块服务
 *
 * @author Aaron
 * @date 2020/6/10 3:35 下午
 */
public interface TestBizService {
    /**
     * 小节视频
     *
     * @param data data
     * @return
     */
    @ZjxStatistics(key = "userId", module = "user:operation:$date$:new:user")
    Data registerUser(Data data);

}
