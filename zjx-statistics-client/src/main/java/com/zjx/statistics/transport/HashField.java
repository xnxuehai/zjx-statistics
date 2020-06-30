package com.zjx.statistics.transport;

/**
 * @author Aaron
 * @date 2020/6/30 9:50
 */
public class HashField {
    /**
     * 统计字段名称
     */
    private String hashFieldName;
    /**
     * 需要统计的值
     */
    private Object hashFieldValue;

    public String getHashFieldName() {
        return hashFieldName;
    }

    public void setHashFieldName(String hashFieldName) {
        this.hashFieldName = hashFieldName;
    }

    public Object getHashFieldValue() {
        return hashFieldValue;
    }

    public void setHashFieldValue(Object hashFieldValue) {
        this.hashFieldValue = hashFieldValue;
    }

    @Override
    public String toString() {
        return "HashField{" +
                "hashFieldName='" + hashFieldName + '\'' +
                ", hashFieldValue=" + hashFieldValue +
                '}';
    }
}
