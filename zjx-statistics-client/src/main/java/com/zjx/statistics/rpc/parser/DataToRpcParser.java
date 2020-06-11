package com.zjx.statistics.rpc.parser;

import com.zjx.statistics.interceptor.operation.AbstractStatisticsOperation;
import com.zjx.statistics.rpc.dto.CounterDTO;
import com.zjx.statistics.rpc.dto.TableFieldCount;
import com.zjx.statistics.rpc.dto.TableStatusCount;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Aaron
 * @date 2020/6/11 6:04 下午
 */
public class DataToRpcParser {

	public List<CounterDTO> parser(Method method, Collection<AbstractStatisticsOperation> operations, Object[] args) {
		List<CounterDTO> counterDTOList = new ArrayList<>();
		// 循环每一个 operations 获取注解信息
		for (AbstractStatisticsOperation operation : operations) {
			CounterDTO counterDTO = parser(method, operation, args);
			counterDTOList.add(counterDTO);
		}
		return counterDTOList;
	}

	private CounterDTO parser(Method method, AbstractStatisticsOperation operation, Object[] args) {

		CounterDTO counterDTO = new CounterDTO();
		String key = operation.getKey();
		String module = operation.getModule();
		Boolean countSelf = operation.getCountSelf();
		Set<String> paramField = operation.getParamField();
		Set<String> tableField = operation.getTableField();
		Set<String> openStatus = operation.getOpenStatus();
		Set<String> tableStatus = operation.getTableStatus();

		// 解析 paramField、tableField
		List<TableFieldCount> tableFieldCount = parserToTableFieldCountToList(method, paramField, tableField, args);

		// 解析 openStatus、openStatus
		List<TableStatusCount> tableStatusCount = parserTableStatusCountToList(method, openStatus, tableStatus, args);

		counterDTO.setKey(key);
		counterDTO.setModule(module);
		counterDTO.setCountSelf(countSelf == true ? 1 : 0);
		counterDTO.setTableFieldCount(tableFieldCount);
		counterDTO.setTableStatusCount(tableStatusCount);

		return counterDTO;
	}


	private List<TableFieldCount> parserToTableFieldCountToList(Method method, Set<String> paramField, Set<String> tableField, Object[] args) {
		List<TableFieldCount> res = new ArrayList<>(paramField.size());
		if (paramField.size() == 0) {
			return res;
		}

		if (paramField.size() != tableField.size()) {
			System.out.println("注解 paramField 与 tableField 配置有误");
			return res;
		}

		Parameter[] parameters = method.getParameters();

		List<String> paramFieldList = new ArrayList<>(paramField);
		List<String> tableFieldList = new ArrayList<>(tableField);

		TableFieldCount tableFieldCount = null;

		for (int i = 0; i < parameters.length; i++) {
			tableFieldCount = new TableFieldCount();
			Object value = parser(parameters[i].getName(), parameters[i], paramField, args[i]);
			tableFieldCount.setTableFieldName(tableFieldList.get(paramFieldList.indexOf(parameters[i].getName())));
			tableFieldCount.setFieldCount(value);
			res.add(tableFieldCount);
		}

		return res;
	}

	private Object parser(String paramFieldName, Parameter parameter, Set<String> paramField, Object value) {
		if (isSystemType(parameter.getType())) {
			return getObjectValue(paramFieldName, paramField, value);
		} else {
			return getObjectValue(parameter, paramField, value);
		}
	}

	private List<TableStatusCount> parserTableStatusCountToList(Method method, Set<String> openStatus, Set<String> tableStatus, Object[] args) {
		if (openStatus.size() != tableStatus.size()) {
			System.out.println("注解 openStatus 与 tableStatus 配置有误");
			return null;
		}

		return null;
	}

	/**
	 * 获取系统类型数据
	 *
	 * @param paramFieldName
	 * @param paramField
	 * @param value
	 * @return
	 */
	private Object getObjectValue(String paramFieldName, Set<String> paramField, Object value) {
		if (paramField.contains(paramFieldName)) {
			return value;
		}
		return null;
	}

	/**
	 * 获取自定义类型数据
	 *
	 * @param parameter
	 * @param paramField
	 * @param value
	 * @return
	 */
	private Object getObjectValue(Parameter parameter, Set<String> paramField, Object value) {
		Object resValue = null;
		Field[] declaredFields = parameter.getType().getDeclaredFields();

		Set<String> fieldsTemp = new HashSet<>(paramField);

		String[] fieldsName = new String[declaredFields.length];

		for (int i = 0; i < declaredFields.length; i++) {
			fieldsName[i] = declaredFields[i].getName();
		}

		// 取二者的交集
		fieldsTemp.retainAll(Arrays.asList(fieldsName));

		// 获取参数类型对应的数据
		try {
			for (String field : fieldsTemp) {
				Field fieldName = parameter.getType().getDeclaredField(field);
				// 强吻
				fieldName.setAccessible(true);
				// 存储到容器中
				resValue = fieldName.get(value);
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return resValue;
	}

	/**
	 * 非自定义类型判断器
	 *
	 * @param cls
	 * @return
	 */
	private boolean isSystemType(Class<?> cls) {
		if (String.class.isAssignableFrom(cls)) {
			return true;
		} else if (Integer.class.isAssignableFrom(cls)) {
			return true;
		} else if (int.class.isAssignableFrom(cls)) {
			return true;
		} else if (Long.class.isAssignableFrom(cls)) {
			return true;
		} else if (Double.class.isAssignableFrom(cls)) {
			return true;
		} else if (double.class.isAssignableFrom(cls)) {
			return true;
		} else if (Boolean.class.isAssignableFrom(cls)) {
			return true;
		} else if (boolean.class.isAssignableFrom(cls)) {
			return true;
		} else if (Float.class.isAssignableFrom(cls)) {
			return true;
		} else if (Byte.class.isAssignableFrom(cls)) {
			return true;
		} else if (byte.class.isAssignableFrom(cls)) {
			return true;
		} else if (Character.class.isAssignableFrom(cls)) {
			return true;
		} else if (char.class.isAssignableFrom(cls)) {
			return true;
		} else if (Short.class.isAssignableFrom(cls)) {
			return true;
		} else if (short.class.isAssignableFrom(cls)) {
			return true;
		}
		return false;
	}

}
