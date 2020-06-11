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
public class DataToRpcParser implements DataParser {

    @Override
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

    private List<TableStatusCount> parserTableStatusCountToList(Method method, Set<String> openStatus, Set<String> tableStatus, Object[] args) {
        return null;
    }

    private List<TableFieldCount> parserToTableFieldCountToList(Method method, Set<String> paramField, Set<String> tableField, Object[] args) {
        return null;
    }

    public void aa(Method method, Collection<AbstractStatisticsOperation> operations, Object[] args) {

//        operations.forEach(operation -> {
//
//            if ("video".equals(operation.getModule())) {
//                // 需要统计的 key
//                Set<String> fields = operation.getFields();
//
//                Map<String, Object> kv = new ConcurrentHashMap<>(4);
//
//                // 获取方法中参数的列表，并处理成 k,v 形式
//                Parameter[] parameters = method.getParameters();
//                for (int i = 0; i < parameters.length; i++) {
//                    System.out.println("参数名称：" + parameters[i].getName() + " --- 参数类型：" + parameters[i].getType().getName());
//                    if (String.class.isAssignableFrom(parameters[i].getType())) {
//                        // 获取参数名称
//                        String name = parameters[i].getName();
//                        if (fields.contains(name)) {
//                            kv.put(name, args[i]);
//                        }
//                    } else if (int.class.isAssignableFrom(parameters[i].getType())) {
//                        // 获取参数名称
//                        String name = parameters[i].getName();
//                        if (fields.contains(name)) {
//                            kv.put(name, args[i]);
//                        }
//                    } else if (Integer.class.isAssignableFrom(parameters[i].getType())) {
//                        // 获取参数名称
//                        String name = parameters[i].getName();
//                        if (fields.contains(name)) {
//                            kv.put(name, args[i]);
//                        }
//                    } else {
//                        // 自定义引用类型
//                        Field[] declaredFields = parameters[i].getType().getDeclaredFields();
//
//                        Set<String> fieldsTemp = new HashSet<>(fields);
//
//                        String[] fieldsName = new String[declaredFields.length];
//                        for (int i1 = 0; i1 < declaredFields.length; i1++) {
//                            fieldsName[i1] = declaredFields[i1].getName();
//                        }
//
//                        fieldsTemp.retainAll(Arrays.asList(fieldsName));
//
//                        System.out.println("fieldsTemp:" + fieldsTemp);
//
//                        // 获取参数类型对应的数据
//                        Object value = args[i];
//                        try {
//                            for (String field : fieldsTemp) {
//                                Field fieldName = parameters[i].getType().getDeclaredField(field);
//                                // 强吻
//                                fieldName.setAccessible(true);
//                                // 获取值
//                                Object fieldValue = fieldName.get(value);
//                                // 存储到容器中
//                                kv.put(field, fieldValue);
//                            }
//                        } catch (NoSuchFieldException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                // 处理完一组注解发送到服务端
//                System.out.println("发送到服务端的数据为：" + kv);
//
//            }
//        });
    }

}
