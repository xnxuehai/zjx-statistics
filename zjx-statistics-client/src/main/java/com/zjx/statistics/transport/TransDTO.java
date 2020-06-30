package com.zjx.statistics.transport;

import java.util.List;
import java.util.Set;

/**
 * @author Aaron
 * @date 2020/6/30 9:44
 */
public class TransDTO {
    /**
     * 模块名称
     */
    private String module;
    /**
     * 唯一标识
     */
    private Object key;
    /**
     * hash field
     */
    private List<HashField> hashFields;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public List<HashField> getHashFields() {
        return hashFields;
    }

    public void setHashFields(List<HashField> hashFields) {
        this.hashFields = hashFields;
    }

    @Override
    public String toString() {
        return "TransDTO{" +
                "module='" + module + '\'' +
                ", key=" + key +
                ", hashFields=" + hashFields +
                '}';
    }
}
