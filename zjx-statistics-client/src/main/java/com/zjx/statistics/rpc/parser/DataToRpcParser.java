package com.zjx.statistics.rpc.parser;

import com.zjx.statistics.interceptor.operation.AbstractStatisticsOperation;
import com.zjx.statistics.rpc.dto.CounterDTO;
import com.zjx.statistics.rpc.dto.TableFieldCount;
import com.zjx.statistics.rpc.dto.TableStatusCount;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

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

        counterDTO.setKey(parserKey(key, method, args));
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

        List<String> paramFieldList = new ArrayList<>(paramField);
        List<String> tableFieldList = new ArrayList<>(tableField);

        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (isSystemType(parameters[i].getType())) {
                TableFieldCount tableFieldCount = new TableFieldCount();
                if (paramField.contains(parameters[i].getName())) {
                    tableFieldCount.setTableFieldName(tableFieldList.get(paramFieldList.indexOf(parameters[i].getName())));
                    tableFieldCount.setFieldCount(args[i]);
                }
            } else {
                Set<String> retainElement = getRetainElement(paramField, parameters[i]);
                for (String field : retainElement) {
                    TableFieldCount tableFieldCount = new TableFieldCount();
                    Object value = getObjectValueByReflection(parameters[i].getType(), field, args[i]);
                    tableFieldCount.setTableFieldName(tableFieldList.get(paramFieldList.indexOf(field)));
                    tableFieldCount.setFieldCount(value);
                    res.add(tableFieldCount);
                }
            }
        }

        return res;
    }

    private Object parserKey(String key, Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (isSystemType(parameters[i].getType())) {
                if (key.equals(parameters[i].getName())) {
                    return args[i];
                }
            } else {
                Set<String> set = new HashSet<>();
                set.add(key);
                Set<String> retainElement = getRetainElement(set, parameters[i]);
                for (String field : retainElement) {
                    TableFieldCount tableFieldCount = new TableFieldCount();
                    Object value = getObjectValueByReflection(parameters[i].getType(), field, args[i]);
                    if (value != null) {
                        return value;
                    }
                }
            }
        }
        return null;
    }


    private List<TableStatusCount> parserTableStatusCountToList(Method method, Set<String> openStatus, Set<String> tableStatus, Object[] args) {
        List<TableStatusCount> res = new ArrayList<>(openStatus.size());
        if (openStatus.size() == 0) {
            return res;
        }

        if (openStatus.size() != openStatus.size()) {
            System.out.println("注解 paramField 与 tableField 配置有误");
            return res;
        }

        List<String> paramFieldList = new ArrayList<>(openStatus);
        List<String> tableFieldList = new ArrayList<>(tableStatus);

        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (isSystemType(parameters[i].getType())) {
                TableStatusCount tableStatusCount = new TableStatusCount();
                if (openStatus.contains(parameters[i].getName())) {
                    tableStatusCount.setTableStatusName(tableFieldList.get(paramFieldList.indexOf(parameters[i].getName())));
                    tableStatusCount.setStatusValue(args[i]);
                }
            } else {
                Set<String> retainElement = getRetainElement(openStatus, parameters[i]);
                for (String field : retainElement) {
                    TableStatusCount tableStatusCount = new TableStatusCount();
                    Object value = getObjectValueByReflection(parameters[i].getType(), field, args[i]);
                    tableStatusCount.setTableStatusName(tableFieldList.get(paramFieldList.indexOf(field)));
                    tableStatusCount.setStatusValue(value);
                    res.add(tableStatusCount);
                }
            }
        }

        return res;
    }

    /**
     * 返回 都包含的属性名称集合
     *
     * @param paramField paramField
     * @param parameter  parameter
     * @return
     */
    private Set<String> getRetainElement(Set<String> paramField, Parameter parameter) {
        // 创建一个新的集合
        Set<String> fieldRetain = new HashSet<>(paramField);
        // 获取 Parameter 中所有的属性
        Field[] declaredFields = parameter.getType().getDeclaredFields();
        String[] fieldsName = new String[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            fieldsName[i] = declaredFields[i].getName();
        }
        // 取交集
        fieldRetain.retainAll(Arrays.asList(fieldsName));

        return fieldRetain;
    }

    /**
     * 通过反射获取对象中属性的值
     *
     * @param cls       cls
     * @param fieldName fieldName
     * @param obj       obj
     * @return
     */
    private Object getObjectValueByReflection(Class<?> cls, String fieldName, Object obj) {
        Object resValue = null;
        try {
            Field declaredField = cls.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            resValue = declaredField.get(obj);
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
