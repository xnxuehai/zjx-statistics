package com.zjx.statistics.interceptor.operation;

import com.zjx.statistics.interceptor.BasicOperation;
import org.springframework.cache.interceptor.CacheOperation;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 统计操作基类（用来封装通用的 属性 和 行为）
 *
 * @author Aaron
 * @date 2020/6/10 11:26 上午
 */
public class AbstractStatisticsOperation implements BasicOperation {

    private final String name;

    private String key;

    private final String module;

    private Set<String> hashField;

    private final String toString;


    protected AbstractStatisticsOperation(Builder b) {
        this.name = b.name;
        this.key = b.key;
        this.module = b.module;
        this.hashField = b.hashField;
        this.toString = b.getOperationDescription().toString();
    }


    public String getName() {
        return this.name;
    }

    public String getModule() {
        return module;
    }

    public String getKey() {
        return key;
    }

    public Set<String> getHashField() {
        return hashField;
    }

    public String getToString() {
        return toString;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        return (other instanceof CacheOperation && toString().equals(other.toString()));
    }


    @Override
    public int hashCode() {
        return toString().hashCode();
    }


    @Override
    public final String toString() {
        return this.toString;
    }

    /**
     * 使用构建器模式构建
     */
    public abstract static class Builder {

        private String name = "";

        private String key = "";

        private String module = "";

        private Set<String> hashField = Collections.emptySet();

        public void setName(String name) {
            Assert.hasText(name, "Name must not be empty");
            this.name = name;
        }

        public void setKey(String key) {
            Assert.hasText(name, "Key must not be empty");
            this.key = key;
        }

        public void setModule(String module) {
            Assert.hasText(module, "Module must not be empty");
            this.module = module;
        }


        public void setHashField(String hashField) {
            this.hashField = Collections.singleton(hashField);
        }

        public void setHashField(String... hashFields) {
            this.hashField = new LinkedHashSet<>(hashFields.length);
            for (String field : hashFields) {
                this.hashField.add(field);
            }
        }


        protected StringBuilder getOperationDescription() {
            StringBuilder result = new StringBuilder(getClass().getSimpleName());
            result.append("[").append(this.name);
            result.append("] key=").append(this.key);
            result.append(" | module='").append(this.module);
            result.append("' | hashField='").append(this.hashField).append("'");
            return result;
        }

        /**
         * 子类实现
         *
         * @return
         */
        public abstract AbstractStatisticsOperation build();
    }
}
