package com.zjx.statistics.core.cache;

import com.zjx.statistics.domain.StatisticsMeta;
import com.zjx.statistics.domain.StatisticsMetaColumn;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 本地缓存
 *
 * @author Aaron
 * @date 2020/6/18 17:30
 */
@Slf4j
public class CacheStore {
    /**
     * 统计模块本地缓存
     */
    private List<StatisticsMeta> statisticsMetaStore = new CopyOnWriteArrayList<>();
    /**
     * 统计字段本地缓存
     */
    private Map<Integer, List<StatisticsMetaColumn>> statisticsMetaColumnStore = new ConcurrentHashMap<>(7);
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
     * 添加 {@link StatisticsMeta} 本地缓存
     *
     * @param statisticsMeta statisticsMeta
     * @return
     */
    public boolean addStatisticsMeta(StatisticsMeta statisticsMeta) {
        if (statisticsMeta == null) {
            log.error("添加化统计模块 [ StatisticsMeta ] 本地缓存时异常!原因为: statisticsMeta is null");
            return false;
        }
        return this.statisticsMetaStore.add(statisticsMeta);
    }

    /**
     * 获取缓存
     *
     * @return
     */
    public List<StatisticsMeta> getStatisticsMetaList() {
        return this.statisticsMetaStore;
    }

    /**
     * 删除 {@link StatisticsMeta} 本地缓存
     *
     * @param statisticsMeta statisticsMeta
     * @return
     */
    public boolean removeStatisticsMeta(StatisticsMeta statisticsMeta) {
        if (statisticsMeta == null) {
            log.error("删除统计模块 [ StatisticsMeta ] 本地缓存时异常! 原因为: statisticsMeta is null");
            return false;
        }
        return this.statisticsMetaStore.remove(statisticsMeta);
    }

    /**
     * 添加 统计字段 本地缓存
     *
     * @param key                       key
     * @param statisticsMetaColumnsList statisticsMetaColumnsList
     * @return
     */
    public boolean addStatisticsMetaColumn(Integer key, List<StatisticsMetaColumn> statisticsMetaColumnsList) {
        if (key == null || "".equals(key) || statisticsMetaColumnsList.size() == 0) {
            log.error("添加统计字段 [ key 和 StatisticsMetaColumn ] 本地缓存时异常!原因为: key is null 或者 '' 或者 statisticsMetaColumnsList size is 0");
            return false;
        }
        return this.statisticsMetaColumnStore.put(key, statisticsMetaColumnsList) == null ? false : true;
    }

    /**
     * 获取统计字段缓存
     *
     * @param key key
     * @return
     */
    public List<StatisticsMetaColumn> getStatisticsMetaColumn(Integer key) {
        if (key == null || "".equals(key)) {
            log.error("获取统计字段 [ key ] 本地缓存时异常!原因为: key is null 或者 ''");
            return null;
        }
        return this.statisticsMetaColumnStore.get(key);
    }

    /**
     * 删除 统计字段 本地缓存
     *
     * @param key key
     * @return
     */
    public boolean removeStatisticsMetaColumn(Integer key) {
        if (key == null || "".equals(key)) {
            log.error("删除统计字段 [ key ] 本地缓存时异常!原因为: key is null 或者 ''");
            return false;
        }
        return this.statisticsMetaColumnStore.remove(key) == null ? false : true;
    }
}
