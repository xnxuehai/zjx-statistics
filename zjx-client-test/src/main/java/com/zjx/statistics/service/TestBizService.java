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
    @ZjxStatistics(key = "userId", module = "$date$:new:user:set")
    Data registerUser(Data data);

    /**
     * 日统计新增用户数
     *
     * @param userId
     * @return
     */
    @ZjxStatistics(key = "userId", module = "$date$:new:user:count")
    void access(String userId);

    /**
     * test
     *
     * @param userId
     */
    @ZjxStatistics(key = "userId", module = "$date$:voice:button:count")
    void voice(String userId);

    /**
     * 学生排行
     *
     * @param userId
     * @param experience
     * @param medal
     */
    @ZjxStatistics(key = "userId", module = "user:rank:cache:key", hashField = {"experience", "medal"})
    void rank(String userId, Integer experience, Integer medal);
}
