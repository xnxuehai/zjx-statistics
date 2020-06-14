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
}
