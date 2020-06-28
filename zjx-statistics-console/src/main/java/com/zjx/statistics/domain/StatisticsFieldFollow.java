package com.zjx.statistics.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Aaron
 * @date 2020/6/28 15:52
 */
@Data
public class StatisticsFieldFollow {
    /**
     * 唯一标识
     */
    private Integer id;
    /**
     * 属性id
     */
    private Integer fieldId;
    /**
     * hash 属性
     */
    private String hashField;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
