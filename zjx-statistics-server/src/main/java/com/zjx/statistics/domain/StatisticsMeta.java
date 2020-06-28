package com.zjx.statistics.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Aaron
 * @date 2020/6/18 13:48
 */
@Data
public class StatisticsMeta {
    /**
     * id
     */
    private Integer id;
    /**
     * 统计模块名称
     */
    private String moduleName;
    /**
     * 描述
     */
    private String moduleDesc;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
