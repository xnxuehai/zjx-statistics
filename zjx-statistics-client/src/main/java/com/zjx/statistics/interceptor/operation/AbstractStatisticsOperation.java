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

    private Boolean countSelf;

    private Set<String> paramField;

    private Set<String> tableField;

    private Set<String> openStatus;

    private Set<String> tableStatus;

    private final String toString;


    protected AbstractStatisticsOperation(Builder b) {
        this.name = b.name;
        this.key = b.key;
        this.module = b.module;
        this.countSelf = b.countSelf;
        this.paramField = b.paramField;
        this.tableField = b.tableField;
        this.openStatus = b.openStatus;
        this.tableStatus = b.tableStatus;
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

    public Boolean getCountSelf() {
        return countSelf;
    }

    public Set<String> getParamField() {
        return paramField;
    }

    public Set<String> getTableField() {
        return tableField;
    }

    public Set<String> getOpenStatus() {
        return openStatus;
    }

    public Set<String> getTableStatus() {
        return tableStatus;
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

        private Boolean countSelf = false;

        private Set<String> paramField = Collections.emptySet();

        private Set<String> tableField = Collections.emptySet();

        private Set<String> openStatus = Collections.emptySet();

        private Set<String> tableStatus = Collections.emptySet();

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

        public void setCountSelf(Boolean countSelf) {
            this.countSelf = countSelf;
        }

        public void setParamField(String paramField) {
            this.paramField = Collections.singleton(paramField);
        }

        public void setParamField(String... paramFields) {
            this.paramField = new LinkedHashSet<>(paramFields.length);
            for (String field : paramFields) {
                this.paramField.add(field);
            }
        }

        public void setTableField(String tableField) {
            this.tableField = Collections.singleton(tableField);
        }

        public void setTableField(String... tableFields) {
            this.tableField = new LinkedHashSet<>(tableFields.length);
            for (String field : tableFields) {
                this.tableField.add(field);
            }
        }

        public void setOpenStatus(String openStatus) {
            this.openStatus = Collections.singleton(openStatus);
        }

        public void setOpenStatus(String... openStatus) {
            this.openStatus = new LinkedHashSet<>(openStatus.length);
            for (String field : openStatus) {
                this.openStatus.add(field);
            }
        }

        public void setTableStatus(String tableStatus) {
            this.tableStatus = Collections.singleton(tableStatus);
        }

        public void setTableStatus(String... tableStatus) {
            this.tableStatus = new LinkedHashSet<>(tableStatus.length);
            for (String field : tableStatus) {
                this.tableStatus.add(field);
            }
        }

        protected StringBuilder getOperationDescription() {
            StringBuilder result = new StringBuilder(getClass().getSimpleName());
            result.append("[").append(this.name);
            result.append("] key=").append(this.key);
            result.append(" | module='").append(this.module);
            result.append("' | countSelf='").append(this.countSelf);
            result.append("' | paramField='").append(this.paramField);
            result.append("' | tableField='").append(this.tableField);
            result.append("' | openStatus='").append(this.openStatus);
            result.append("' | tableStatus='").append(this.openStatus).append("'");
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
