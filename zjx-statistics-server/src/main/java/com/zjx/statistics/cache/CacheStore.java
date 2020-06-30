package com.zjx.statistics.cache;

import com.zjx.statistics.dto.StatisticsFieldDTO;
import com.zjx.statistics.engine.Algorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存
 *
 * @author Aaron
 * @date 2020/6/18 17:30
 */
@Slf4j
public class CacheStore {
	/**
	 * 统计 field 本地缓存
	 */
	private Map<String, StatisticsFieldDTO> statisticsMetaColumnStore = new ConcurrentHashMap<>(8);
	/**
	 * 计算规则缓存
	 */
	private Map<String, Algorithm> algorithmInstanceStore = new ConcurrentHashMap<>();
	/**
	 * 提供一个Singleton类型的变量
	 */
	private static CacheStore instance = new CacheStore();

	/**
	 * 构造方法私有化
	 */
	private CacheStore() {
		// 防止反射破坏单例
		if (instance != null) {
			throw new RuntimeException("不允许非法访问!");
		}
	}

	/**
	 * 返回全局实例对象
	 *
	 * @return
	 */
	public static CacheStore getInstance() {
		return instance;
	}

	/**
	 * 获取 hash field 和 rule
	 *
	 * @param key key
	 * @return List<StatisticsFieldHashRuleDTO>
	 */
	public StatisticsFieldDTO getHashFile(String key) {
		return this.statisticsMetaColumnStore.get(key);
	}

	/**
	 * 添加缓存
	 *
	 * @param key key
	 */
	public void addFieldCache(String key, StatisticsFieldDTO statisticsFieldDTO) {
		if (statisticsMetaColumnStore.containsKey(key)) {
			log.error("当前缓存中存在key！！！");
			throw new RuntimeException("存在要添加的缓存key");
		}
		this.statisticsMetaColumnStore.put(key, statisticsFieldDTO);
	}

	/**
	 * 添加计算规则
	 *
	 * @param key       key
	 * @param algorithm algorithm
	 */
	public void addAlgorithm(String key, Algorithm algorithm) {
		if (this.algorithmInstanceStore.containsKey(key)) {
			log.error("当前缓存中存在key！！！");
			throw new RuntimeException("存在要添加的缓存key");
		}
		this.algorithmInstanceStore.put(key, algorithm);
	}

	/**
	 * 获取计算规则实例
	 *
	 * @param key key
	 * @return
	 */
	public Algorithm getAlgorithm(String key) {
		return this.algorithmInstanceStore.get(key);
	}
}
