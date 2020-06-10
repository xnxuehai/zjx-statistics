package com.zjx.statistics.interceptor;

/**
 * TODO 异常处理，目前没想好怎么设计
 *
 * @author Aaron
 * @date 2020/6/10 3:10 下午
 */
@FunctionalInterface
public interface StatisticsOperationInvoker {

    Object invoke() throws ThrowableWrapper;


    @SuppressWarnings("serial")
    class ThrowableWrapper extends RuntimeException {

        private final Throwable original;

        public ThrowableWrapper(Throwable original) {
            super(original.getMessage(), original);
            this.original = original;
        }

        public Throwable getOriginal() {
            return this.original;
        }
    }
}
