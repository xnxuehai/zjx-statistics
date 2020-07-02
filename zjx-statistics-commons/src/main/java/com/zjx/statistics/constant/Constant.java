package com.zjx.statistics.constant;

/**
 * 公共常量
 *
 * @author Aaron
 * @date 2020/6/18 14:12
 */
public class Constant {
    /**
     * 分隔符
     */
    public static final String DELIMITER_COLON = ":";
    /**
     * 学生排名统计缓存
     */
    public static final String USER_RANK_CACHE_KEY = "USER_RANK_CACHE_KEY";
    /**
     * 过期时间
     */
    public static final long EXPIRE = 48 * 3600;
    /**
     * 日期格式
     */
    public static final String DATE_FORMAT_STR = "yyyy-MM-dd";
    /**
     * 排行榜缓存标识位
     */
    public static final String CACHE_FLAG_STR = "flag";

    public static final String CACHE_COUNT_STR = "cache:count:user";

    /**
     * 无效标识
     */
    public static final String INVALID_FLAG = "-1";

    /**
     * Redis string 类型
     */
    public static final int STRING = 1;
    /**
     * Redis set 类型
     */
    public static final int SET = 2;
    /**
     * Redis hash 结构
     */
    public static final int HASH = 3;
    /**
     * Redis zSet 结构
     */
    public static final int Z_SET = 4;

}


