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

    private final String module;

    private final Set<String> fields;

    private final String toString;


    protected AbstractStatisticsOperation(Builder b) {
        this.name = b.name;
        this.module = b.module;
        this.fields = b.fields;
        this.toString = b.getOperationDescription().toString();
    }


    public String getName() {
        return this.name;
    }

    public String getModule() {
        return module;
    }

    public Set<String> getFields() {
        return fields;
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


    public abstract static class Builder {

        private String name = "";

        private String module = "";

        private Set<String> fields = Collections.emptySet();

        public void setName(String name) {
            Assert.hasText(name, "Name must not be empty");
            this.name = name;
        }

        public void setModule(String module) {
            Assert.hasText(module, "Module must not be empty");
            this.module = module;
        }

        public void setField(String field) {
            Assert.hasText(field, "Field name must not be empty");
            this.fields = Collections.singleton(field);
        }

        public void setField(String... fields) {
            this.fields = new LinkedHashSet<>(fields.length);
            for (String field : fields) {
                Assert.hasText(field, "Field name must be non-empty if specified");
                this.fields.add(field);
            }
        }

        protected StringBuilder getOperationDescription() {
            StringBuilder result = new StringBuilder(getClass().getSimpleName());
            result.append("[").append(this.module);
            result.append("] fields=").append(this.fields).append("'");
            return result;
        }

        public abstract AbstractStatisticsOperation build();
    }
}
