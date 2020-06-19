package com.zjx.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Aaron
 * @date 2020/6/18 16:14
 */
@Data
public class StatisticsMetaColumn {

    private Integer id;
    private Integer statisticsMetaId;
    private String statisticsColumn;
    private String statisticsStatusColumn;
    private String statisticsStatusColumnValue;
    private String persistenceColumn;
    private Date createTime;
    private Date updateTime;
}
