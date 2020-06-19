package com.zjx.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Aaron
 * @date 2020/6/18 13:48
 */
@Data
public class StatisticsMeta {
    private Integer id;
    private String moduleName;
    private String moduleDesc;
    private Date createTime;
    private Date updateTime;
}
