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
//	@ZjxStatistics(module = "video", field = {"name", "age"})
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
	 * @param data
	 * @param str
	 * @return
	 */
//	@ZjxStatistics(module = "video", field = {"name", "str", "height"})
	Data doService(Data data, String str);

}
