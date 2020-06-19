package com.zjx.core.cache;

import com.zjx.core.constant.Constant;
import com.zjx.core.util.DateUtil;

/**
 * 缓存 key 管理
 *
 * @author Aaron
 * @date 2020/6/18 18:15
 */
public class CacheKey {
    /**
     * 获取 用户id set 缓存 key
     *
     * @return
     */
    public static String getUserIdCacheKey() {
        return new StringBuilder()
                .append(Constant.CACHE_COUNT_STR)
                .append(Constant.DELIMITER_COLON)
                .append(DateUtil.getCurrentLocalDateTime(Constant.DATE_FORMAT_STR))
                .toString();
    }

    /**
     * 获取 统计数据 缓存 key
     *
     * @param member     用户id
     * @param moduleName 统计模块名称
     * @return
     */
    public static String getCountKey(Integer member, String moduleName) {
        return new StringBuilder()
                .append(member)
                .append(Constant.DELIMITER_COLON)
                .append(moduleName)
                .append(Constant.DELIMITER_COLON)
                .append(DateUtil.getCurrentLocalDateTime(Constant.DATE_FORMAT_STR))
                .toString();
    }
}
