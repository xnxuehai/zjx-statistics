package com.zjx.statistics.core.cache;

import com.zjx.statistics.dto.StatisticsFieldHashRuleDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
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
    private Map<String, List<StatisticsFieldHashRuleDTO>> statisticsMetaColumnStore = new ConcurrentHashMap<>(8);
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
    public List<StatisticsFieldHashRuleDTO> getHashRule(String key) {
        return this.statisticsMetaColumnStore.get(key);
    }

    /**
     * 添加缓存
     *
     * @param key key
     */
    public void addFieldCache(String key, List<StatisticsFieldHashRuleDTO> list) {
        if (statisticsMetaColumnStore.containsKey(key)) {
            log.error("当前缓存中存在key！！！");
            throw new RuntimeException("存在要添加的缓存key");
        }
        this.statisticsMetaColumnStore.put(key, list);
    }
}
