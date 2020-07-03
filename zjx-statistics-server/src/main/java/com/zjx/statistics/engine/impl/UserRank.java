package com.zjx.statistics.engine.impl;

import com.zjx.statistics.dto.StatisticsFieldHashRuleDTO;
import com.zjx.statistics.engine.Algorithm;
import com.zjx.statistics.engine.ComputeContext;
import com.zjx.statistics.engine.ComputeContextData;
import com.zjx.statistics.transport.HashField;
import com.zjx.statistics.transport.TransDTO;
import com.zjx.statistics.util.CarryUtil;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 计算学生排行榜规则
 *
 * @author Aaron
 * @date 2020/7/3 18:01
 */
public class UserRank implements Algorithm {

    @Override
    public void operate() {
        ComputeContextData computeContextData = ComputeContext.getInstance().getComputeContextData();
        // 排序的分数
        Double score = computeContextData.getScore();
        // 传入的数据对象
        TransDTO transDTO = computeContextData.getTransDTO();
        List<HashField> hashFields = transDTO.getHashFields();
        List<StatisticsFieldHashRuleDTO> statisticsFieldHashRuleDTOS = computeContextData.getStatisticsFieldHashRuleDTOS();

        // 按照从小到大排序
        List<StatisticsFieldHashRuleDTO> sortList = statisticsFieldHashRuleDTOS.stream()
                .sorted(Comparator.comparing(StatisticsFieldHashRuleDTO::getSort))
                .collect(Collectors.toList());

        String experienceKey = sortList.get(0).getHashField();
        String medalKey = sortList.get(1).getHashField();

        Integer experience = null;
        Integer medal = null;

        for (HashField hashField : hashFields) {
            if (experienceKey.equals(hashField.getHashFieldName())) {
                experience = Integer.valueOf(hashField.getHashFieldValue().toString());
            }
            if (medalKey.equals(hashField.getHashFieldName())) {
                medal = Integer.valueOf(hashField.getHashFieldValue().toString());
            }
        }


        double sc;
        if (score == null) {
            // 第一次添加
            sc = CarryUtil.scoreParser(experience, medal, 0L);
        } else {
            sc = CarryUtil.scoreParser(experience, medal, Long.valueOf(new BigDecimal(score).toString()));
        }
        computeContextData.setScore(sc);
    }
}
