package com.zjx.statistics.service.impl;

import com.zjx.statistics.domain.StatisticsField;
import com.zjx.statistics.domain.StatisticsFieldFollow;
import com.zjx.statistics.domain.StatisticsRule;
import com.zjx.statistics.mapper.StatisticsFieldFollowMapper;
import com.zjx.statistics.mapper.StatisticsFieldMapper;
import com.zjx.statistics.mapper.StatisticsRuleMapper;
import com.zjx.statistics.service.IStatisticsFieldService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Aaron
 * @date 2020/6/29 10:12
 */
@Service
public class StatisticsFieldServiceImpl implements IStatisticsFieldService {
    @Resource
    private StatisticsFieldMapper statisticsFieldMapper;
    @Resource
    private StatisticsFieldFollowMapper statisticsFieldFollowMapper;
    @Resource
    private StatisticsRuleMapper statisticsRuleMapper;


    @Override
    public List<StatisticsField> findAllField() {
        return statisticsFieldMapper.selectAll();
    }

    @Override
    public List<StatisticsFieldFollow> findByFieldId(Integer fieldId) {
        return statisticsFieldFollowMapper.findByFieldId(fieldId);
    }

    @Override
    public List<StatisticsRule> findAllRule() {
        return statisticsRuleMapper.findAll();
    }

}
