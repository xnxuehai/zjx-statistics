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
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
