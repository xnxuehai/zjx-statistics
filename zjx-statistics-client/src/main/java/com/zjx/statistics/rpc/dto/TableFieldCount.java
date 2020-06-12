package com.zjx.statistics.rpc.dto;

import java.io.Serializable;

/**
 * 统计字段集合
 *
 * @author Aaron
 * @date 2020/6/11 5:50 下午
 */
public class TableFieldCount extends BaseCount implements Serializable {
    /**
     * 数据库中的字段
     */
    private String tableFieldName;
    /**
     * 需要统计的值
     */
    private Object fieldCount;

    public String getTableFieldName() {
        return tableFieldName;
    }

    public void setTableFieldName(String tableFieldName) {
        this.tableFieldName = tableFieldName;
    }

    public Object getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(Object fieldCount) {
        this.fieldCount = fieldCount;
    }

    @Override
    public String toString() {
        return "tableFieldCount{" +
                "tableFieldName='" + tableFieldName + '\'' +
                ", fieldCount=" + fieldCount +
                '}';
    }
}
