package com.zjx.statistics.dto;

import java.io.Serializable;

/**
 * 状态字段集合
 *
 * @author Aaron
 * @date 2020/6/11 5:50 下午
 */
public class TableStatusCount extends BaseCount implements Serializable {
    /**
     * 数据库中的字段
     */
    private String tableStatusName;
    /**
     * 需要统计的值
     */
    private Object statusValue;

    public String getTableStatusName() {
        return tableStatusName;
    }

    public void setTableStatusName(String tableStatusName) {
        this.tableStatusName = tableStatusName;
    }

    public Object getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(Object statusValue) {
        this.statusValue = statusValue;
    }

    @Override
    public String toString() {
        return "TableStatusCount{" +
                "tableStatusName='" + tableStatusName + '\'' +
                ", statusValue=" + statusValue +
                '}';
    }
}
