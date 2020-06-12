package com.zjx.statistics.rpc.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 统计信息数据传输类
 *
 * @author Aaron
 * @date 2020/6/11 5:19 下午
 */
public class CounterDTO implements Serializable {
    /**
     * key 目前用 userId
     */
    private Object key;
    /**
     * 统计的模块
     */
    private String module;
    /**
     * 统计自己 值为 0 或者 1
     */
    private Integer countSelf;
    /**
     * 统计字段集合
     */
    private List<BaseCount> tableFieldCount;
    /**
     * 状态字段集合
     */
    private List<BaseCount> tableStatusCount;

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getCountSelf() {
        return countSelf;
    }

    public void setCountSelf(Integer countSelf) {
        this.countSelf = countSelf;
    }

    public List<BaseCount> getTableFieldCount() {
        return tableFieldCount;
    }

    public void setTableFieldCount(List<BaseCount> tableFieldCount) {
        this.tableFieldCount = tableFieldCount;
    }

    public List<BaseCount> getTableStatusCount() {
        return tableStatusCount;
    }

    public void setTableStatusCount(List<BaseCount> tableStatusCount) {
        this.tableStatusCount = tableStatusCount;
    }

    @Override
    public String toString() {
        return "CounterDTO{" +
                "key=" + key +
                ", module='" + module + '\'' +
                ", countSelf=" + countSelf +
                ", tableFieldCount=" + tableFieldCount +
                ", tableStatusCount=" + tableStatusCount +
                '}';
    }
}
