package com.zjx.statistics.interceptor.operation;

import com.zjx.statistics.annotation.ZjxStatistics;

/**
 * 注解 {@link ZjxStatistics} 元信息存储类
 *
 * @author Aaron
 * @date 2020/6/10 2:03 下午
 */
public class ZjxStatisticsOperation extends AbstractStatisticsOperation {

    public ZjxStatisticsOperation(Builder b) {
        super(b);
    }

    public static class Builder extends AbstractStatisticsOperation.Builder {

        @Override
        protected StringBuilder getOperationDescription() {
            StringBuilder sb = super.getOperationDescription();
            return sb;
        }

        @Override
        public AbstractStatisticsOperation build() {
            return new AbstractStatisticsOperation(this);
        }
    }
}
