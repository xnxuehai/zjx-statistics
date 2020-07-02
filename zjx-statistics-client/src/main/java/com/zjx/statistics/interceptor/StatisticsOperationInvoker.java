package com.zjx.statistics.interceptor;

/**
 * TODO 异常处理，目前没想好怎么设计
 *
 * @author Aaron
 * @date 2020/6/10 3:10 下午
 */
@FunctionalInterface
public interface StatisticsOperationInvoker {
    /**
     * 调用 目标方法
     *
     * @return
     * @throws ThrowableWrapperException
     */
    Object invoke() throws ThrowableWrapperException;


    @SuppressWarnings("serial")
    class ThrowableWrapperException extends RuntimeException {

        private final Throwable original;

        public ThrowableWrapperException(Throwable original) {
            super(original.getMessage(), original);
            this.original = original;
        }

        public Throwable getOriginal() {
            return this.original;
        }
    }
}
