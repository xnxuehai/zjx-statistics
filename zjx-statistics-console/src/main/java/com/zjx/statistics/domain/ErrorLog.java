package com.zjx.statistics.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Aaron
 * @date 2020/7/1 9:27
 */
@Data
public class ErrorLog {
    /**
     * 唯一标识
     */
    private Integer id;
    /**
     * 缓存key
     */
    private String moduleName;
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
