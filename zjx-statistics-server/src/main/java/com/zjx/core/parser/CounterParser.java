package com.zjx.core.parser;

import com.zjx.core.constant.Constant;
import com.zjx.statistics.dto.CounterDTO;
import com.zjx.statistics.dto.TableFieldCount;
import com.zjx.statistics.dto.TableStatusCount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析数据
 *
 * @author Aaron
 * @date 2020/6/15 19:03
 */
public class CounterParser {


    /**
     * 解析数据
     *
     * @param counterDTO counterDTO
     * @return
     */
    public static Map<String, Object> getParserData(CounterDTO counterDTO) {
        Map<String, Object> cacheData = new HashMap<>(counterDTO.getTableFieldCount().size());
        // 处理 countSelf
        if (counterDTO.getCountSelf() != null && !Constant.ZERO.equals(counterDTO.getCountSelf())) {
            cacheData.put(Constant.COUNT_SELF, 1);
        }
        // 获取数据库统计字段和值
        List<TableFieldCount> tableFieldCount = counterDTO.getTableFieldCount();
        // 获取数据库状态字段和值
        List<TableStatusCount> tableStatusCount = counterDTO.getTableStatusCount();

        List<String> statusFieldKey = getStatusFieldKey(tableStatusCount);

        if (statusFieldKey.size() == 0) {
            // 正常统计属性处理
            for (TableFieldCount fieldCount : tableFieldCount) {
                cacheData.put(fieldCount.getTableFieldName(), fieldCount.getFieldCount());
            }
        } else {
            // 笛卡尔积处理 + 正常统计属性处理
            for (String key : statusFieldKey) {
                // 处理状态 + 统计属性
                for (TableFieldCount fieldCount : tableFieldCount) {
                    cacheData.put(key + Constant.SPLIT + fieldCount.getTableFieldName(), fieldCount.getFieldCount());
                    //  正常统计属性处理
                    cacheData.put(fieldCount.getTableFieldName(), fieldCount.getFieldCount());
                }
                // 处理状态 + countSelf
                if (counterDTO.getCountSelf() != null && !Constant.ZERO.equals(counterDTO.getCountSelf())) {
                    cacheData.put(key + Constant.SPLIT + Constant.COUNT_SELF, 1);
                }
            }
        }
        return cacheData;
    }

    private static List<String> getStatusFieldKey(List<TableStatusCount> statusFieldList) {
        List<String> statusKeys = new ArrayList<>();
        for (TableStatusCount tableStatusCount : statusFieldList) {
            String tableStatusName = tableStatusCount.getTableStatusName();
            Object statusValue = tableStatusCount.getStatusValue();
            if (statusValue != null && !"".equals(statusValue) && !"null".equals(statusValue)) {
                statusKeys.add(tableStatusName + Constant.SPLIT + statusValue);
            }
        }

        return statusKeys;
    }

}
