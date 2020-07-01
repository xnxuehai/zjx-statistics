package com.zjx.statistics.business.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Aaron
 * @date 2020/7/1 15:14
 */
@Data
public class StatisticsOfDay {
    /**
     * 唯一标识
     */
    private Integer id;
    /**
     * 模块id
     */
    private Integer moduleId;
    /**
     * 缓存 field
     */
    private String cacheField;
    /**
     * 缓存值
     */
    private String cacheFieldValue;
    /**
     * 统计日期
     */
    private String countDate;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
