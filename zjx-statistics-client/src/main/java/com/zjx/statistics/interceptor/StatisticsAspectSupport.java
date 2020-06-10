package com.zjx.statistics.interceptor;

import com.zjx.statistics.interceptor.operation.AbstractStatisticsOperation;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

		// TODO 主要的业务逻辑，此处需要根据 模块 和 统计的参数 进行处理！
		// TODO 根据业务情况是同步处理还是异步处理！
		// TODO 发生异常处理措施！


		Object returnValue = invokeOperation(invoker);

		operations.forEach(operation -> {
			if ("video".equals(operation.getModule())) {
				// 需要统计的 key
				Set<String> fields = operation.getFields();

				Map<String, Object> kv = new ConcurrentHashMap<>(4);

				// 获取方法中参数的列表，并处理成 k,v 形式
				Parameter[] parameters = method.getParameters();
				for (int i = 0; i < parameters.length; i++) {
					System.out.println("参数名称：" + parameters[i].getName() + " --- 参数类型：" + parameters[i].getType().getName());
					if (String.class.isAssignableFrom(parameters[i].getType())) {
						// 获取参数名称
						String name = parameters[i].getName();
						if (fields.contains(name)) {
							kv.put(name, args[i]);
						}
					} else if (int.class.isAssignableFrom(parameters[i].getType())) {
						// 获取参数名称
						String name = parameters[i].getName();
						if (fields.contains(name)) {
							kv.put(name, args[i]);
						}
					} else if (Integer.class.isAssignableFrom(parameters[i].getType())) {
						// 获取参数名称
						String name = parameters[i].getName();
						if (fields.contains(name)) {
							kv.put(name, args[i]);
						}
					} else {
						// 自定义引用类型
						Field[] declaredFields = parameters[i].getType().getDeclaredFields();

						Set<String> fieldsTemp = new HashSet<>(fields);

						String[] fieldsName = new String[declaredFields.length];
						for (int i1 = 0; i1 < declaredFields.length; i1++) {
							fieldsName[i1] = declaredFields[i1].getName();
						}

						fieldsTemp.retainAll(Arrays.asList(fieldsName));

						System.out.println("fieldsTemp:" + fieldsTemp);

						// 获取参数类型对应的数据
						Object value = args[i];
						try {
							for (String field : fieldsTemp) {
								Field fieldName = parameters[i].getType().getDeclaredField(field);
								// 强吻
								fieldName.setAccessible(true);
								// 获取值
								Object fieldValue = fieldName.get(value);
								// 存储到容器中
								kv.put(field, fieldValue);
							}
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}

				// 处理完一组注解发送到服务端
				System.out.println("发送到服务端的数据为：" + kv);

			}
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
