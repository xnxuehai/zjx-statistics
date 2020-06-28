package com.zjx.statistics.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Aaron
 * @date 2020/6/28 15:43
 */
@Data
public class StatisticsModule {
    /**
     * 唯一标识
     */
    private Integer id;
    /**
     * 统计模块名称
     */
    private String name;
    /**
     * 描述信息
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
