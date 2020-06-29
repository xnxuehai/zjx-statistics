package com.zjx.statistics.rpc.parser;


import com.zjx.statistics.interceptor.operation.AbstractStatisticsOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Aaron
 * @date 2020/6/11 6:04 下午
 */
public class DataToRpcParser {

    Logger logger = LoggerFactory.getLogger(getClass());

//    public List<CounterDTO> parser(Method method, Collection<AbstractStatisticsOperation> operations, Object[] args) {
//        List<CounterDTO> counterDTOList = new ArrayList<>();
//        // 循环每一个 operations 获取注解信息
//        for (AbstractStatisticsOperation operation : operations) {
//            CounterDTO counterDTO = parser(method, operation, args);
//            counterDTOList.add(counterDTO);
//        }
//        return counterDTOList;
//    }


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
            logger.error("Statistics Client Reflection Object NoSuchFieldException", e);
            // TODO 调用服务端接口进行异常日志持久化
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            logger.error("Statistics Client Reflection Object IllegalAccessException", e);
            // TODO 调用服务端接口进行异常日志持久化
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
