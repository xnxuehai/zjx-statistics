package com.zjx.statistics.engine;

import com.zjx.statistics.cache.CacheStore;

/**
 * 计算引擎
 *
 * @author Aaron
 * @date 2020/6/29 14:24
 */
public class ComputingEngine {

	private Algorithm algorithm;

	public ComputingEngine(String algorithmPath) {
		this.algorithm = CacheStore.getInstance().getAlgorithm(algorithmPath);
	}

	public void operate() {
		this.algorithm.operate();
	}
}
