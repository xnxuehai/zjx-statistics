package com.zjx.statistics.interceptor;

import com.alibaba.fastjson.JSON;
import com.zjx.statistics.dto.CounterDTO;
import com.zjx.statistics.interceptor.operation.AbstractStatisticsOperation;
import com.zjx.statistics.rpc.parser.DataToRpcParser;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

/**
 * 最主要的抽象类，在这个类中进行了，前置通知 和 后置通知的 逻辑。
 * 自定义的通知（拦截器需要继承这个抽象类）
 *
 * @author Aaron
 * @date 2020/6/10 10:54 上午
 */

public abstract class StatisticsAspectSupport implements InitializingBean, SmartInitializingSingleton {
    private static final Logger log = LoggerFactory.getLogger(StatisticsAspectSupport.class);

    @Nullable
    private StatisticsOperationSource statisticsOperationSource;

    @Resource(name = "statisticsThreadPoolExecutor")
    private Executor executor;

    @Resource(name = "statisticsProducer")
    private DefaultMQProducer producer;


    public void setCacheOperationSource(@Nullable StatisticsOperationSource statisticsOperationSource) {
        this.statisticsOperationSource = statisticsOperationSource;
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
        // if (this.initialized) { TODO 以后思考,需不需要实现。
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

        Object returnValue = null;
        try {
            returnValue = invoker.invoke();
            executor.execute(() -> {
                // 解析数据
                DataToRpcParser parser = new DataToRpcParser();
                // TODO 解析数据, 目前先这个抽象，后期迭代。
                List<CounterDTO> counterDTO = parser.parser(method, operations, args);
                // 发送数据到服务端
                log.info("[{}] send statistics server data : {}", Thread.currentThread().getName(), counterDTO);
                for (CounterDTO dto : counterDTO) {
                    SendResult sendResult = null;
                    try {
                        Message msg = new Message("syn_topic_test", "TagA", JSON.toJSONString(dto).getBytes(RemotingHelper.DEFAULT_CHARSET));
                        // 发送消息到一个Broker
                        sendResult = producer.send(msg);
                    } catch (MQClientException e) {
                        e.printStackTrace();
                    } catch (RemotingException e) {
                        e.printStackTrace();
                    } catch (MQBrokerException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    // 通过sendResult返回消息是否成功送达
                    log.info("sendResult:{}", sendResult);
                }
            });
        } catch (StatisticsOperationInvoker.ThrowableWrapper throwableWrapper) {
            log.error("[{}] 统计探针执行被代理对象真实方法出现异常: {}", Thread.currentThread().getName(), throwableWrapper.getMessage());
            throwableWrapper.printStackTrace();
            // TODO 此处抛出异常是为了防止 事务回滚的问题，这个问题待测试。
            throw throwableWrapper;
        }

        return returnValue;
    }

    private Class<?> getTargetClass(Object target) {
        return AopProxyUtils.ultimateTargetClass(target);
    }
}
