package com.zjx.statistics.interceptor;

import com.zjx.statistics.interceptor.operation.AbstractStatisticsOperation;
import com.zjx.statistics.rpc.dto.CounterDTO;
import com.zjx.statistics.rpc.parser.DataParser;
import com.zjx.statistics.rpc.parser.DataToRpcParser;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 最主要的抽象类，在这个类中进行了，前置通知 和 后置通知的 逻辑。
 * 自定义的通知（拦截器需要继承这个抽象类）
 *
 * @author Aaron
 * @date 2020/6/10 10:54 上午
 */
public abstract class StatisticsAspectSupport implements BeanFactoryAware, InitializingBean, SmartInitializingSingleton {

    @Nullable
    private StatisticsOperationSource statisticsOperationSource;
    @Nullable
    private BeanFactory beanFactory;

    /**
     * 构建线程池
     */
    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(16, 16, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue(), new MyThreadFactory("Aaron-Thread"), new ThreadPoolExecutor.AbortPolicy());


    /**
     * 构建线程池工厂
     */
    static class MyThreadFactory implements ThreadFactory {
        private ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private String namePrefix;

        MyThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + "-" + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }


    public void setCacheOperationSource(@Nullable StatisticsOperationSource statisticsOperationSource) {
        this.statisticsOperationSource = statisticsOperationSource;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.state(getStatisticsOperationSource() != null, "The 'statisticsOperationSource' property is required: " +
                "If there are no ZjxStatistics methods, then don't use a statistics aspect.");
    }

    @Override
    public void afterSingletonsInstantiated() {
//        System.out.println("************ 执行 afterSingletonsInstantiated 方法，目前留空 *************");
    }

    @Nullable
    public StatisticsOperationSource getStatisticsOperationSource() {
        return this.statisticsOperationSource;
    }

    @Nullable
    protected Object execute(StatisticsOperationInvoker invoker, Object target, Method method, Object[] args) {
        // Check whether aspect is enabled (to cope with cases where the AJ is pulled in automatically)
        // if (this.initialized) { 没懂啥意思，以后思考。
        if (true) {
            Class<?> targetClass = getTargetClass(target);
            StatisticsOperationSource statisticsOperationSource = getStatisticsOperationSource();
            if (statisticsOperationSource != null) {
                Collection<AbstractStatisticsOperation> operations = statisticsOperationSource.getStatisticsOperations(method, targetClass);
                if (!CollectionUtils.isEmpty(operations)) {
                    return execute(invoker, method, operations, args);
                }
            }
        }

        return invoker.invoke();
    }

    @Nullable
    private Object execute(final StatisticsOperationInvoker invoker, Method method, Collection<AbstractStatisticsOperation> operations, Object[] args) {

        // TODO 判断 module 是否合法
        // TODO 主要的业务逻辑，此处需要根据 模块 和 统计的参数 进行处理！
        // TODO 根据业务情况是同步处理还是异步处理！
        // TODO 发生异常处理措施！


        Object returnValue = invokeOperation(invoker);

        poolExecutor.execute(() -> {
            // 解析数据
            DataParser parser = new DataToRpcParser();
            // TODO 解析数据和发送数据异步处理
            List<CounterDTO> counterDTO = parser.parser(method, operations, args);
            // TODO 发送数据到服务端
            System.out.println("向服务端发送信息:" + counterDTO);
        });

        return returnValue;
    }

    /**
     * 调用被代理对象的真实方法
     *
     * @param invoker
     * @return
     */
    protected Object invokeOperation(StatisticsOperationInvoker invoker) {
        return invoker.invoke();
    }

    private Class<?> getTargetClass(Object target) {
        return AopProxyUtils.ultimateTargetClass(target);
    }
}
