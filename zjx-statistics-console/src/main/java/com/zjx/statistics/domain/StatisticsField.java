package com.zjx.statistics.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Aaron
 * @date 2020/6/28 15:50
 */
@Data
public class StatisticsField {
    /**
     * 唯一标识
     */
    private Integer id;
    /**
     * 分类id
     */
    private Integer categoryId;
    /**
     * 统计属性
     */
    private String cacheField;
    /**
     * 统计属性描述
     */
    private String description;
    /**
     * 过期时间 单位 秒
     */
    private Integer expired;
    /**
     * 数据类型 1 string 2 set 3 hash 4 zSet
     */
    private Integer dataType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
