package com.zjx.statistics.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Aaron
 * @date 2020/6/28 15:47
 */
@Data
public class StatisticsCategory {
    /**
     * 唯一标识
     */
    private Integer id;
    /**
     * 统计模块id
     */
    private Integer moduleId;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 场景标识 1 元信息 2 运营
     */
    private Integer scene;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
