package com.zjx.statistics.facade;

import com.zjx.statistics.dto.CounterDTO;

/**
 * @author Aaron
 * @date 2020/6/14 17:14
 */
public interface ReceiveInfo {
    /**
     * 接收统计数据
     *
     * @param counterDTO
     */
    void receiveInfo(CounterDTO counterDTO);

    /**
     * 统计学生的经验值 和 奖章
     *
     * @param userId     userId
     * @param experience experience
     * @param medal      medal
     */
    void receiveInfo(Integer userId, Integer experience, Integer medal);
}
